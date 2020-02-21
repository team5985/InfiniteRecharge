package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;
import frc.robot.Constants;
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

    public static void setSystem(SensoredSystem winch, Solenoid buddyLock, LimitSwitchGroup limitGroup) {
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
    }

    public enum BuddyState {
        BUDDY,  // Robot has a friend
        NO_BUDDY,  // Robot does not have a friend
    }

    @Override
    public void update() {
        switch(desiredState) {  //TODO: set currentElevatorState when actions are completed
            case STOWED:
            m_winchMaster.stopMotor();
            currentState = ClimberState.STOWED;
            buddyState = BuddyState.NO_BUDDY;
            break;

            case PREPARING:
            this.elevatorMoveTo(Constants.kElevatorMaxHeight);
            if (this.getPosition())
            buddyState = BuddyState.NO_BUDDY;
            break;

            case PREPARED:
            this.elevatorMoveTo(0.0);
            break;

            case CLIMB_LO:
            this.winchMoveTo(Constants.kWinchLoClimbHeight);
            break;

            case CLIMB_HI:
            this.winchMoveTo(Constants.kWinchHiClimbHeight);
            break;

            case ELEVATOR_MANUAL:
            break;

            case WINCH_MANUAL:
            break;

            default:
            m_elevator.stopMotor();
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
     * @return Position in rotations, where the bottom is 0 and becomes positive as the lift goes up
     */
    @Override
    @Deprecated
    public double getPosition() {
        return winchCountsToRotations(m_winchMaster.getCounts());
    }

    /**
     * @return Position in metres, where the bottom is 0 and becomes positive as the lift goes up
     */
    public double getElevatorPosition() {
        return elevatorCountsToMetres(m_winchMaster.getCounts());
    }

    /**
     * @return Position in metres, where the top is 0 and becomes positive as the winch operates.
     */
    public double getWinchPosition() {
        return winchCountsToMetres(m_winchMaster.getCounts());
    }

    public ClimberState getCurrentElevatorState() {
        return currentState;
    }

    /**
     * @param desiredElevatorState the desiredElevatorState to set
     */
    public void setDesiredElevatorState(ClimberState desiredElevatorState) {
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
            elevatorMove(-0.1);
            return false;
        } else {
            elevatorMove(0.0);
            return true;
        }
    }
    
    private void elevatorMoveTo(double position) {
        double speed = (elevatorCountsToMetres(m_elevator.getCounts()) - position) * Constants.kElevatorKp;  // Simple proportional control
        if (speed < 0) {
            speed *= Constants.kElevatorDownwardsGain;
        }
        m_elevator.set(speed);
    }

    /**
     * Move the elevator manually, with protections from the limit switches
     * @param speed from -1.0 to 1.0
     */
    private void elevatorMove(double speed) {
        if (m_upperLimit.get() && speed > 0) {
            speed = 0;
        } else if (m_lowerLimit.get() && speed < 0) {
            speed = 0;
        }
        m_elevator.set(speed);
    }

    private void winchMoveTo(double position) {
        double speed = winchPidController.calculate(winchCountsToMetres(m_winchMaster.getCounts()), position);
        winchMove(speed);
    }

    /**
     * Move the winch manually, with protection for completely disallowing reversing and travelling past the lower limit switch.
     * @param speed from -1.0 to 1.0
     */
    private void winchMove(double speed) {
        if (m_lowerLimit.get() && speed > 0) {
            speed = 0;
        } else if (speed < 0) {
            speed = 0;
        }
        m_winchMaster.set(speed);
    }

    private int elevatorMetresToCounts(double position) {
        return (int) Math.round(Constants.kElevatorCountsPerMetre * position);
    }

    private double elevatorCountsToMetres(int counts) {
        return counts / Constants.kElevatorCountsPerMetre;
    }

    private int winchMetresToCounts(double position) {
        return (int) Math.round(Constants.kWinchCountsPerMetre * position);
    }

    private double winchCountsToMetres(int counts) {
        return counts / Constants.kWinchCountsPerMetre;
    }
}
