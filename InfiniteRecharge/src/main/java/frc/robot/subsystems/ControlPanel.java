package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.util.ColourSensor;

public class ControlPanel extends Subsystem {

    private ControlPanelState currentState;
    private ControlPanelState desiredState;
    
    public void update() 
    {
        boolean newState = false;
        if (currentState != desiredState)
        {
            newState = true;
            currentState = desiredState;
        }

        ColourSensor.getInstance().runPeriodic();
        //state machine
        switch(currentState) {
            case EXTENDED:
            setSpinnerSpeed(0);

            extendSpinner();
            break;

            case RETRACTED:
            setSpinnerSpeed(0);
            retractSpinner();
            break;

            case ROTATION_CONTROL:
            extendSpinner();
            
            double controlPanelRotations = ColourSensor.getInstance().getControlPanelRotations();
            if(newState = true){
                ColourSensor.getInstance().resetColourChanges();
                newState = false;
            }
            if(controlPanelRotations < Constants.kRotationalControlTargetRotations)
            {
                double rotationSpeed = Math.min(Math.sqrt(Math.max(controlPanelRotations, 0)),1);
                setSpinnerSpeed(rotationSpeed);
            }
            else
            {
                setSpinnerSpeed(0);
                setDesiredState(ControlPanelState.RETRACTED);
            }   
            break;

            case POSITION_CONTROL:
            extendSpinner();
            if (ColourSensor.getInstance().getFmsColour() == ColourSensor.CPANEL_COLOUR_INVALID)
            {
                setDesiredState(ControlPanelState.RETRACTED);
            }
            else
            {
                int FMSColour = ColourSensor.getInstance().getFmsColour();
                int currentColour = ColourSensor.getInstance().getColour();
                if(FMSColour != currentColour)
                {
                    setSpinnerSpeed(Constants.kControlPanelPoisitionControlSpeed);
                }
                else
                {
                    setSpinnerSpeed(0);
                   
                    setDesiredState(ControlPanelState.RETRACTED);
                }
            }
            break;

            case MANUAL_CLOCKWISE:
            extendSpinner();
            setSpinnerSpeed(Constants.kControlPanelManualSpeed);
            break;


            case MANUAL_ANTICLOCKWISE:
            extendSpinner();
            setSpinnerSpeed(-Constants.kControlPanelManualSpeed);
            break;



            default:
            desiredState = ControlPanelState.RETRACTED;
            break;
        }
    }

    public void setDesiredState(ControlPanelState newState)
    {
        desiredState = newState;
    }

    public ControlPanelState getCurrentState()
    {
        return currentState;
    }

    /**
     * @return 1 if extended, 0 if retracted
     */
    public double getPosition()
    {
        if(currentState == ControlPanelState.EXTENDED)
        {
            return 1.0;
        }
        else
        {
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

    private void extendSpinner()
    {
        RobotMap.getControlPanelSolenoid().setForward();
    }

    private void retractSpinner()
    {
        RobotMap.getControlPanelSolenoid().setReverse();
    }
    
    private void setSpinnerSpeed(double speed)
    {
        RobotMap.getControlPanelSystem().set(speed);
    }
    
}