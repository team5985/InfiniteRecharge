package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;


import frc.robot.RobotMap;
import frc.robot.Constants;



public class Intake extends Subsystem {

    public static Intake m_instance;

    IntakeState currentState;
    IntakeState desiredState;

    public enum IntakeState {
        EXTENDED,
        RETRACTED,
        INTAKING,
        UNINTAKING,
    }

    
    
    public static Intake getInstance() {
        if (m_instance == null) {
            m_instance = new Intake();
        }
        return m_instance;
    }
    
    private Intake() {
        currentState = IntakeState.RETRACTED;
    }

    public void update() {
        switch(currentState) {
            
            case RETRACTED:
                RobotMap.getIntakeActuationSystem().set(ControlMode.MotionMagic, 0);
                currentState = desiredState;
                break;

            case EXTENDED:
                RobotMap.getIntakeActuationSystem().set(ControlMode.MotionMagic, (Constants.kIntakeExtensionRevolutions * Constants.kIntakeEncoderPPR));
                currentState = desiredState;
                break;

            case INTAKING:
                RobotMap.getIntakeSystem().set(Constants.kIntakeIntakingSpeed);
                currentState = desiredState;
                break;

            case UNINTAKING:
                RobotMap.getIntakeSystem().set(Constants.kIntakeUnintakingSpeed);
                currentState = desiredState;
            break;
            

        }
    }



    public double getPosition() {
        return RobotMap.getIntakeActuationSystem().getSelectedSensorPosition();
      
    }

    public boolean zeroPosition() {
        RobotMap.getIntakeActuationSystem().setSelectedSensorPosition(0);
        return false;

    }

    public void setDesiredState(IntakeState state) {
        desiredState = state;
    }

    public IntakeState getCurrentState() {
        return currentState;
    }
   
    public boolean checkSafeRetraction() {
        if(true == true) { //FIXME
            return true;
        } else {
            return false;
        }
    }
}
