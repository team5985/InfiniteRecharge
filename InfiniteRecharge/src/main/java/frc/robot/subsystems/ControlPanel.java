package frc.robot.subsystems;

public class ControlPanel extends Subsystem {

    private ControlPanelState currentState;
    private ControlPanelState desiredState;
    public void update() {
        //state machine
        switch(currentState) {
            case EXTENDED:

            currentState = desiredState;
            break;
            case RETRACTED:

            currentState = desiredState;
            break;
            case ROTATION_CONTROL:

            currentState = desiredState;
            break;
            case POSITION_CONTROL:

            currentState = desiredState;
            break;
            case MANUAL_CLOCKWISE:

            currentState = desiredState;
            break;
            case MANUAL_ANTICLOCKWISE:

            currentState = desiredState;
            break;
            default:

            currentState = desiredState;
            break;

        }
    }

    public double getPosition() {
        return 0.0; //Position
    }
    public boolean zeroPosition() {
        return false;
    }

    public enum ControlPanelState {
        EXTENDED,
        RETRACTED,
        ROTATION_CONTROL,
        POSITION_CONTROL,
        MANUAL_CLOCKWISE,
        MANUAL_ANTICLOCKWISE,
    }
    
}