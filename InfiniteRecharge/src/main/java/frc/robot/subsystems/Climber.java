package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.util.LimitSwitchAdapter;
import frc.util.LimitSwitchGroup;
import frc.util.SensoredSystem;

public class Climber extends Subsystem {
    public static Climber m_instance;

    public static Climber getInstance() {
        if (m_instance == null) {
            m_instance = new Climber();
        }

        return m_instance;
    }

    // private static SensoredSystem m_elevator;
    private static SensoredSystem m_winchMaster;
    private static Solenoid m_buddyLock;
    private static LimitSwitchAdapter m_upperLimit;
    private static LimitSwitchAdapter m_lowerLimit;

    private ClimberState currentState;
    private ClimberState desiredState;
    private BuddyState buddyState;
    
    ProfiledPIDController winchPidController;
    Constraints winchPidConstraints;

    private Climber() {
        currentState = ClimberState.STOWED;
        desiredState = ClimberState.STOWED;
        buddyState = BuddyState.NO_BUDDY;

        winchPidConstraints = new Constraints(Constants.kWinchMaxVelocity, Constants.kWinchMaxAccel);
        winchPidController = new ProfiledPIDController(Constants.kWinchKp, 0.0, 0.0, winchPidConstraints);
        winchPidController.reset(0.0);
    }

    public void setSystem(SensoredSystem winch, Solenoid buddyLock, LimitSwitchGroup limitGroup) {
        m_winchMaster = winch;
        m_buddyLock = buddyLock;
        m_upperLimit = limitGroup.getInstance(0);
        m_lowerLimit = limitGroup.getInstance(1);
    }

    public enum ClimberState {
        STOWED,
        CLIMBING,  // Elevator is up and hooks are not supported by bar
        LIFTING,  // Elevator goes down but hooks are supported by bar
        IDLE,
    }
    public enum BuddyState {
        BUDDY,  // Robot has a friend
        NO_BUDDY,  // Robot does not have a friend
    }

    @Override
    public void update() {
        switch(desiredState) {
            case STOWED:
                RobotMap.getClimberA().set(ControlMode.MotionMagic, 0);
                RobotMap.getClimberB().set(ControlMode.MotionMagic, 0);
                currentState = desiredState;
            break;
            case LIFTING:
                RobotMap.getClimberA().set(ControlMode.MotionMagic, Constants.kClimbLowPos);
                RobotMap.getClimberB().set(ControlMode.MotionMagic, Constants.kClimbLowPos);
                if(RobotMap.getClimberA().isMotionProfileFinished()) {
                    
                }
                currentState = desiredState;

            break;
            case CLIMBING:
                RobotMap.getClimberA().set(ControlMode.MotionMagic, Constants.kClimbHighPos);
                RobotMap.getClimberB().set(ControlMode.MotionMagic, Constants.kClimbHighPos);
                currentState = desiredState;

            break;
            default:
                  
                  currentState = desiredState;

            break;
        }
    }

    /**
     * @return Position in rotations, where calibration position is 0 and becomes positive as the lift goes up
     */
    @Override
    public double getPosition() {
        return winchCountsToRotations(m_winchMaster.getCounts());
    }

    // /**
    //  * @return Position in metres, where the bottom is 0 and becomes positive as the lift goes up
    //  */
    // public double getElevatorPosition() {
    //     return elevatorCountsToRotations(m_winchMaster.getCounts());
    // }

    /**
     * @return Position in rotations, where calibration position is 0 and becomes positive as the lift goes up
     */
    public double getWinchPosition() {
        return winchCountsToRotations(m_winchMaster.getCounts());
    }

    public ClimberState getCurrentState() {
        return currentState;
    }

    /**
     * @param desiredElevatorState the desiredElevatorState to set
     */
    public void setDesiredState(ClimberState desiredElevatorState) {
        this.desiredState = desiredElevatorState;
    }

    /**
     * @param buddyState the buddyState to set
     */
    public void setBuddyState(BuddyState buddyState) {
        this.buddyState = buddyState;
    }
    public BuddyState getBuddyState() {
        return buddyState;
    }

    @Override
	public boolean zeroPosition() {
		if (!m_lowerLimit.get()) {
            winchMove(-0.2);
            return false;
        } else {
            winchMove(0.0);
            m_winchMaster.setCounts(0);
            return true;
        }
    }
    
    private void winchMoveTo(double rotations) {
        double speed = winchPidController.calculate(winchCountsToRotations(m_winchMaster.getCounts()), rotations);
        winchMove(speed);
    }

    /**
     * Move the winch manually, with protection for completely disallowing reversing and travelling past the lower limit switch.
     * @param speed from -1.0 to 1.0
     */
    public void winchMove(double speed) {
        if (m_lowerLimit.get() && speed < 0) {
            speed = 0;
        }
        m_winchMaster.set(speed);
    }

    public boolean getTarget() {
        return RobotMap.getClimberA().isMotionProfileFinished();
    }

    private double winchCountsToRotations(int counts) {
        return counts / Constants.kWinchEncoderCountsPerRev;
    }
}
