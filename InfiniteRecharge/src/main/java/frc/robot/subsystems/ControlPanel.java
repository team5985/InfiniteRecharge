package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.util.ColourSensor;

public class ControlPanel extends Subsystem {

    private ControlPanelState currentState;
    private ControlPanelState desiredState;
    public void update() {
        //state machine
        switch(currentState) {
            case EXTENDED:

            RobotMap.getControlPanelSolenoid().setForward();; //FIXME

            currentState = desiredState;
            break;
            case RETRACTED:

            RobotMap.getControlPanelSolenoid().setReverse(); //FIXME
            currentState = desiredState;
            break;
            case ROTATION_CONTROL:

                if(ColourSensor.getControlPanelRotations() > Constants.kControlPanelTargetRotations - Constants.kControlPanelHysteresis && 
                ColourSensor.getControlPanelRotations() < Constants.kControlPanelTargetRotations + Constants.kControlPanelHysteresis) {
                    ColourSensor.resetColourChange();
                } else {
                    RobotMap.getControlPanelSystem().set(Constants.kControlPanelRotationControlSpeed);
                }

            currentState = desiredState;
            break;
            case POSITION_CONTROL:

            if(ColourSensor.getPositionControlColourChanges == ColourSensor.getFmsColour()) {
                ColourSensor.resetColourChange();
            } else {
                RobotMap.getControlPanelSystem().set(Constants.kControlPanelPoisitionControlSpeed);
            }
 
            currentState = desiredState;
            break;
            case MANUAL_CLOCKWISE:

            RobotMap.getControlPanelSystem().set(Constants.kControlPanelManualSpeed); //TODO direction check

            currentState = desiredState;
            break;
            case MANUAL_ANTICLOCKWISE:
            RobotMap.getControlPanelSystem().set(Constants.kControlPanelManualSpeed * -1); //TODO direction check

            currentState = desiredState;
            break;
            default:

            currentState = desiredState;
            break;

        }
    }
    /**
     * @return 1 if extended, 0 if retracted
     */
    public double getPosition() {
        if(currentState == ControlPanelState.EXTENDED) {
            return 1.0;
        }else {
            return 0.0; 
        }
        
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