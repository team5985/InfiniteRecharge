package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Constants;
import frc.robot.RobotMap;

public class Bar extends Subsystem {

    private Bar() {
        currentState = BarStates.IDLE;
        desiredState = BarStates.IDLE;
    }

    

public enum BarStates {
    LEFT,
    RIGHT,
    IDLE,
}

    private static Bar m_instance;
    private BarStates currentState;
    private BarStates desiredState;

    public static Bar getInstance() {
        if(m_instance == null) {
            m_instance = new Bar();
        }
        return m_instance;
    }


    public void update() {
        switch(currentState) {
            case LEFT:
                moveLeft();
                currentState = desiredState;
            break;
            case RIGHT:
                moveRight();
                currentState = desiredState;
            default:
                stopBar();
                currentState = desiredState; 
        }
    }

    public double getPosition() {
        return 0;
    }

    public boolean zeroPosition() {
        return true;
    }
    public BarStates getCurrentState() {
        return currentState;
    }

    public void setDesiredState(BarStates state) {
        desiredState = state;
    }

    void moveLeft() {
        RobotMap.getBarTraverser().set(ControlMode.PercentOutput, Constants.kBarLeftSpeed);
    }

    void moveRight() {
        RobotMap.getBarTraverser().set(ControlMode.PercentOutput, Constants.kBarRightSpeed);
    }

    void stopBar() {
        RobotMap.getBarTraverser().set(ControlMode.PercentOutput, Constants.kBarIdleSpeed);
    }
}