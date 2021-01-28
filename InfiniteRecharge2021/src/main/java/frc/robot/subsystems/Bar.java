package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotMap;

public class Bar extends Subsystem {
    public static Bar m_instance;

    public static Bar getInstance() {
        if (m_instance == null) {
            m_instance = new Bar();
        }

        return m_instance;
    }

    private BarState currentState;
    private BarState desiredState;

    private Bar() {
        currentState = BarState.IDLE;
        desiredState = BarState.IDLE;
    }

    public enum BarState {
        IDLE,
        FORWARD,
        BACKWARD,
    }



    @Override
    public void update() {
        switch(desiredState) {
            case IDLE:
            RobotMap.getBarMotor().stopMotor();
            currentState = desiredState;
            break;

            case FORWARD:
            RobotMap.getBarMotor().set(Constants.kBarForward);
            currentState = desiredState;
            break;

            case BACKWARD:
            RobotMap.getBarMotor().set(Constants.kBarBackward);
            currentState = desiredState;
            break;
        }
    }

    @Override
    public double getPosition() {
        return 0;
    }

    @Override
    public boolean zeroPosition() {
        return false;
    }

    public void setDesiredState(BarState state) {
        desiredState = state;
    }

    public BarState getCurrentState() {
        return currentState;
    }
}
