
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.util.LimitSwitchAdapter;
import frc.util.LimitSwitchGroup;

public class Climber extends Subsystem {
    public static Climber m_instance;

    public static Climber getInstance() {
        if (m_instance == null) {
            m_instance = new Climber();
        }

        return m_instance;
    }

    // private static SensoredSystem m_elevator;
    private static Solenoid m_buddyLock;
    private static LimitSwitchAdapter m_upperLimit;
    private static LimitSwitchAdapter m_lowerLimit;

    private ClimberState currentState;
    private ClimberState desiredState;
    private BuddyState buddyState;
    private IdleState idleState;
    
    ProfiledPIDController winchPidController;
    Constraints winchPidConstraints;

    private Climber() {
        currentState = ClimberState.STOWED;
        desiredState = ClimberState.STOWED;
        buddyState = BuddyState.NO_BUDDY;
        idleState = IdleState.IDLE;

        winchPidConstraints = new Constraints(Constants.kWinchMaxVelocity, Constants.kWinchMaxAccel);
        winchPidController = new ProfiledPIDController(Constants.kWinchKp, 0.0, 0.0, winchPidConstraints);
        winchPidController.reset(0.0);
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

    /**
     * Responsible for setitng the neutral modes of the motors.
     */

    public enum IdleState {
        UP, //Winch A = Coast, Winch B = Brake
        DOWN, //Winch A = Brake, Winch B = Coasr
        IDLE,  // Winch A = Brake, Winch B = Brake
        SAFE,
    }

    @Override
    public void update() {
        System.out.println("Climber is " + RobotMap.getClimberA().getSelectedSensorPosition());
        //setIdleMode();
        switch(desiredState) {
            case STOWED:
                idleState = IdleState.IDLE;
                if(RobotMap.getClimberLimit().get()) {
                    RobotMap.getClimberA().stopMotor();
                    RobotMap.getClimberB().stopMotor();
                    //RobotMap.getClimberB().set(ControlMode.MotionMagic, Constants.kClimbHighPos);
                } else {
                    RobotMap.getClimberA().stopMotor();
                    RobotMap.getClimberB().stopMotor();
                }
                currentState = desiredState;
            break;
            case LIFTING:
            //System.out.println("UP!!!!!"); 

            idleState = IdleState.UP;

                if(RobotMap.getClimberLimit().get()) {
                    //RobotMap.getClimberA().set(ControlMode.MotionMagic, Constants.kClimbLowPos);
                    //RobotMap.getClimberB().set(ControlMode.MotionMagic, Constants.kClimbLowPos);
                    RobotMap.getClimberB().set(ControlMode.MotionMagic, -Constants.kElevatorHighPos);
                    RobotMap.getClimberA().stopMotor();
                } else {
                    RobotMap.getClimberA().stopMotor();
                    RobotMap.getClimberB().stopMotor();
                }
                currentState = desiredState;

            break;
            case CLIMBING:
            idleState = IdleState.DOWN;

                if(/*RobotMap.getClimberLimit().get()*/ true) {
                    RobotMap.getClimberA().set(ControlMode.PercentOutput, -Constants.kElevatorClimbSpeed);
                    RobotMap.getClimberB().stopMotor();

                    //RobotMap.getClimberB().set(ControlMode.MotionMagic, Constants.kClimbHighPos);
                } else {
                    RobotMap.getClimberA().stopMotor();
                    RobotMap.getClimberB().stopMotor();
                }
                currentState = desiredState;

            break;
            default:
                idleState = IdleState.IDLE;

                RobotMap.getClimberA().stopMotor();
                RobotMap.getClimberB().stopMotor();

                  currentState = desiredState;

            break;
        }

        switch(idleState) {
            case UP:
                RobotMap.getClimberA().setNeutralMode(NeutralMode.Coast);
                RobotMap.getClimberB().setNeutralMode(NeutralMode.Brake);
            break;
            case DOWN:
                RobotMap.getClimberA().setNeutralMode(NeutralMode.Brake);
                RobotMap.getClimberB().setNeutralMode(NeutralMode.Coast);
            break;
            case SAFE:
                RobotMap.getClimberA().setNeutralMode(NeutralMode.Coast);
                RobotMap.getClimberB().setNeutralMode(NeutralMode.Coast);
            break;
            default:
                RobotMap.getClimberA().setNeutralMode(NeutralMode.Brake);
                RobotMap.getClimberB().setNeutralMode(NeutralMode.Brake);
            break;
        }
    }



    /**
     * @return Position in rotations, where calibration position is 0 and becomes positive as the lift goes up
     */
    @Override
    public double getPosition() {
        return 0;
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
        return 0;
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
        RobotMap.getClimberA().setSelectedSensorPosition(0);
        RobotMap.getClimberB().setSelectedSensorPosition(0);
        return RobotMap.getClimberLimit().get();

    }
    
    private void winchMoveTo(double rotations) {
    }

    /**
     * Move the winch manually, with protection for completely disallowing reversing and travelling past the lower limit switch.
     * @param speed from -1.0 to 1.0
     */
    public void winchMove(double speed) {
        if (m_lowerLimit.get() && speed < 0) {
            speed = 0;
        }
    }

    public boolean getTarget() {
        return true;//RobotMap.getClimberA().isMotionProfileFinished();
    }

    private double winchCountsToRotations(int counts) {
        return counts / Constants.kWinchEncoderCountsPerRev;
    }

    public void resetSensors() {
    }
/*
    private void setIdleMode() {
        if(currentState == ClimberState.IDLE) {
            idleState = IdleState.IDLE;
        } else if(Math.abs(RobotMap.getClimberA().getMotorOutputPercent()) >= 0.05) {
            idleState = IdleState.UP;
        } else if(Math.abs(RobotMap.getClimberB().getMotorOutputPercent()) >= 0.1) {
            idleState = IdleState.DOWN;
        }
    } */
}

    
