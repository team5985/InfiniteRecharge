package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.RobotMap;
import frc.util.SensoredSystem;
import frc.robot.Constants;

public class Intake extends Subsystem {

    public static Intake m_instance;

    IntakeState currentState;
    IntakeState desiredState;

    DigitalInput indexerFlap = new DigitalInput(0);

    private static WPI_TalonSRX m_intakeActuator;
    private static SpeedController m_intakeRoller;
    private static Servo m_intakeServo;
    
    public enum IntakeState {
        EXTENDED,
        RETRACTED,
        INTAKING,
        UNINTAKING,
    }

    private Intake() {
        currentState = IntakeState.RETRACTED;
        desiredState = IntakeState.RETRACTED;
        DigitalInput intakeFlap;
        
    }

    public static Intake getInstance() {
        if (m_instance == null) {
            m_instance = new Intake();
        }
        return m_instance;
    }

    public void setSystem(WPI_TalonSRX intakeActuator, SpeedController intakeRoller, Servo servo) {
        m_intakeActuator = intakeActuator;
        m_intakeRoller = intakeRoller;
        m_intakeServo = servo;
    }
    
    public void update() {
        switch(desiredState) {
            
            case RETRACTED:
            m_intakeActuator.set(ControlMode.MotionMagic, 0);
            m_intakeRoller.set(0.0);
            currentState = desiredState;
            break;

            case EXTENDED:
            m_intakeActuator.set(ControlMode.MotionMagic, (Constants.kIntakeExtensionRevolutions * Constants.kIntakeEncoderPPR));
            m_intakeRoller.set(0.0);
            RobotMap.getIntakeServo().set(Constants.kIntakeServoExtendedPos);
            currentState = desiredState;
            break;

            case INTAKING:
            m_intakeActuator.set(ControlMode.MotionMagic, (Constants.kIntakeExtensionRevolutions * Constants.kIntakeEncoderPPR));    
            m_intakeRoller.set(Constants.kIntakeIntakingSpeed);
            currentState = desiredState;
            break;

            case UNINTAKING:
            m_intakeActuator.set(ControlMode.MotionMagic, (Constants.kIntakeExtensionRevolutions * Constants.kIntakeEncoderPPR));
            m_intakeRoller.set(Constants.kIntakeUnintakingSpeed);
            currentState = desiredState;
            break;
            

        }
    }

    public double getPosition() {
        return m_intakeActuator.getSelectedSensorPosition() / Constants.kIntakeEncoderPPR;
      
    }

    public boolean zeroPosition() {
        m_intakeActuator.setSelectedSensorPosition(0);
        return false;

    }

    public void setDesiredState(IntakeState state) {
        desiredState = state;
    }

    public IntakeState getCurrentState() {
        return currentState;
    }
   
    public boolean checkSafeRetraction() {
        if(!(indexerFlap.get())) { //FIXME
            return true;
        } else {
            return false;
        }
    }
    public void retractFlap() {
        RobotMap.getIntakeServo().set(Constants.kIntakeServoRetractedPos);
    }

	public void resetSensors() {
        m_intakeActuator.setSelectedSensorPosition(0);
	}
}
