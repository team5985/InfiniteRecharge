package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;
import frc.robot.Constants;
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
    
    // ProfiledPIDController winchPidController;
    // Constraints winchPidConstraints;

    private Climber() {
        currentState = ClimberState.STOWED;
        desiredState = ClimberState.STOWED;
        buddyState = BuddyState.NO_BUDDY;

        // winchPidConstraints = new Constraints(Constants.kWinchMaxVelocity, Constants.kWinchMaxAccel);
        // winchPidController = new ProfiledPIDController(Constants.kWinchKp, 0.0, 0.0, winchPidConstraints);
        // winchPidController.reset(0.0);
    }

    public void setSystem(SensoredSystem winch, Solenoid buddyLock, LimitSwitchGroup limitGroup) {
        m_winchMaster = winch;
        m_buddyLock = buddyLock;
        m_upperLimit = limitGroup.getInstance(0);
        m_lowerLimit = limitGroup.getInstance(1);
    }

    public enum ClimberState {
        STOWED,
        PREPARING,  // Elevator is up and hooks are not supported by bar
        PREPARED,  // Elevator goes down but hooks are supported by bar
        CLIMBING,  // 
        CLIMB_LO,
        CLIMB_HI,
        ELEVATOR_MANUAL,
        WINCH_MANUAL,
        ZEROING,
    }

    public enum BuddyState {
        BUDDY,  // Robot has a friend
        NO_BUDDY,  // Robot does not have a friend
    }

    @Override
    public void update() {
        switch(desiredState) {
            case STOWED:
            m_winchMaster.stopMotor();
            currentState = ClimberState.STOWED;
            buddyState = BuddyState.NO_BUDDY;
            break;

            case ZEROING:
            if (this.zeroPosition()) {
                desiredState = ClimberState.STOWED;
                currentState = ClimberState.STOWED;
            } else {
                currentState = ClimberState.ZEROING;
            }
            break;

            case PREPARING:
            // this.winchMoveTo(Constants.kElevatorTopHeightRotations);
            if(Math.abs(getPosition() - Constants.kElevatorTopHeightRotations) <= Constants.kElevatorHeightTolerance) {
                currentState = ClimberState.PREPARED;
            } else {
                currentState = ClimberState.PREPARING;
            }
            buddyState = BuddyState.NO_BUDDY;
            break;

            case PREPARED:
            // this.winchMoveTo(Constants.kElevatorTopHeightRotations);
            if(Math.abs(getPosition() - Constants.kElevatorTopHeightRotations) <= Constants.kElevatorHeightTolerance) {
                currentState = ClimberState.PREPARED;
            }
            buddyState = BuddyState.NO_BUDDY;
            break;

            case CLIMB_LO:
            // this.winchMoveTo(Constants.kWinchLoClimbRotations);
            currentState = ClimberState.CLIMB_LO;
            break;

            case CLIMB_HI:
            // this.winchMoveTo(Constants.kWinchHiClimbRotations);
            currentState = ClimberState.CLIMB_HI;
            break;

            case ELEVATOR_MANUAL:
            currentState = ClimberState.ELEVATOR_MANUAL;
            break;

            case WINCH_MANUAL:
            currentState = ClimberState.WINCH_MANUAL;
            break;

            default:
            m_winchMaster.stopMotor();
        }
        
        switch(buddyState) {
            case BUDDY:
            m_buddyLock.set(true);  // As this solenoid must be normally extended, "Forward" in this case means releasing the lock
            break;

            case NO_BUDDY:
            // be sad :(
            m_buddyLock.set(false);  // As this solenoid must be normally extended, "Off" or "Reverse" in this case means locked
            break;

            default:
            buddyState = BuddyState.NO_BUDDY;
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
            // winchMove(-0.2);
            return false;
        } else {
            // winchMove(0.0);
            m_winchMaster.setCounts(0);
            return true;
        }
    }

    private void extendElevator() {
        RobotMap.getWinchA().set(ControlMode.MotionMagic, winchCountsToRotations(Constants.kWinchElevatorUp));
    }

    private void retractElevator() {
        RobotMap.getWinchA().set(ControlMode.MotionMagic, winchCountsToRotations(Constants.kWinchElevatorDown));
    }

    private void climbElevator() {
        RobotMap.getWinchA().set(ControlMode.MotionMagic, winchCountsToRotations(Constants.kWinchElevatorClimb));
    }

    private double winchCountsToRotations(int counts) {
        return counts / Constants.kWinchEncoderCountsPerRev;
    }
}
