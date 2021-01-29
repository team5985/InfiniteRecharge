package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.RobotMap;
import frc.util.SensoredSystem;
import frc.robot.Constants;

public class Intake extends Subsystem {

    public static Intake m_instance;

    IntakeState currentState;
    IntakeState desiredState;

    DigitalInput indexerFlap = new DigitalInput(0);

    private static Solenoid m_intakeActuator;
    private static SpeedController m_intakeRoller;

    
    public enum IntakeState {
        INTAKING,
        UNINTAKING,
        IDLE,
    }

    private Intake() {
        currentState = IntakeState.IDLE;
        desiredState = IntakeState.IDLE;
        
    }

    public static Intake getInstance() {
        if (m_instance == null) {
            m_instance = new Intake();
        }
        return m_instance;
    }

    public void setSystem(SpeedController intakeRoller, Solenoid intakeActuator) {
        m_intakeActuator = intakeActuator;
        m_intakeRoller = intakeRoller;
    }
    
    public void update() {
        switch(desiredState) {
            

            case INTAKING:
            m_intakeActuator.set(true);    
            m_intakeRoller.set(Constants.kIntakeIntakingSpeed);
            currentState = desiredState;
            break;

            case UNINTAKING:
            m_intakeActuator.set(true);
            m_intakeRoller.set(Constants.kIntakeUnintakingSpeed);
            currentState = desiredState;
            break;
            
            default:
            m_intakeRoller.set(0);
            m_intakeActuator.set(false);
            

        }
    }

    public double getPosition() {
        if(currentState == IntakeState.INTAKING) {
            return 1;
        }else {
            return 0;
        }
      
    }

    public boolean zeroPosition() {
        desiredState = IntakeState.IDLE;
        return true;

    }

    public void setDesiredState(IntakeState state) {
        desiredState = state;
    }

    public IntakeState getCurrentState() {
        return currentState;
    }
   
    public boolean checkSafeRetraction() {
        /*if(!(indexerFlap.get())) { //FIXME
            return true;
        } else {
            return false;
        } */
        return true;
    }

}
