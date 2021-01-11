package frc.robot.subsystems;

import com.revrobotics.ColorMatch;

import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.util.ColourSensor;

public class ControlPanel extends Subsystem
{
    /**
     * The state we are currently in
     */
    private ControlPanelState currentState;

    /**
     * The state we are going to be in.
     */
    private ControlPanelState desiredState;

    /**
     * Run every 20ms, executes all functionality
     */
    public void update() 
    {
        //This boolean is declared as true every time we enter a new state, and can be used
        // to differentiate between the first loop of the state from the others.
        boolean newState = false;
        if (currentState != desiredState)
        {
            newState = true;
            currentState = desiredState;
        }
        //This allows us to get updated data from our colour sensor every 20ms
        ColourSensor.getInstance().update();
        //state machine
        switch(currentState) {
            //What to do if we are EXTENDING our arm, or we are EXTENDED
            case EXTENDED:
            setSpinnerSpeed(0);
            extendSpinner();
            break;

            //What to do if we are RETRACTING our arm, or we are RETRACTED
            case RETRACTED:
            setSpinnerSpeed(0);
            retractSpinner();
            break;

            // What to do during ROTATIONAL CONTROL. This is the end of stage 2
            // in the game where we need to rotate the wheel between 3 and 5
            // revolutions.
            case ROTATION_CONTROL:
            extendSpinner();
            if(newState == true){
                ColourSensor.getInstance().resetColourChanges();
                newState = false;
            }
            double controlPanelRotations = ColourSensor.getInstance().getControlPanelRotations();
            if(controlPanelRotations < Constants.kRotationalControlTargetRotations)
            {   
                //Defines a square root deceleration
                double rotationSpeed = Math.min(Math.sqrt(Math.max(controlPanelRotations, 0)),1)*Constants.kControlPanelManualSpeed;
                setSpinnerSpeed(rotationSpeed);
            }
            else
            {
                setSpinnerSpeed(0);
                setDesiredState(ControlPanelState.RETRACTED);
            }   
            break;

            // What to do during POSITIONAL CONTROL. This is where we need to turn
            // the wheel to a particular colour.
            case POSITION_CONTROL:
            extendSpinner();
            if (ColourSensor.getInstance().getFmsColour() == Constants.kControlPanelColourInvalid)
            {
                setDesiredState(ControlPanelState.RETRACTED);
            }
            else
            {
                int FMSColour = ColourSensor.getInstance().getFmsColour();
                int currentColour = ColourSensor.getInstance().getColour();
                int TargetColour = (FMSColour +2) % 4;
                if(currentColour != TargetColour)
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

            //What to do when we are spinning MANUAL CLOCKWISE
            case MANUAL_CLOCKWISE:
            extendSpinner();
            setSpinnerSpeed(Constants.kControlPanelManualSpeed);
            break;

            //What to do when we are spinning MANUAL ANTICLOCKWISE
            case MANUAL_ANTICLOCKWISE:
            extendSpinner();
            setSpinnerSpeed(-Constants.kControlPanelManualSpeed);
            break;

            default:
            desiredState = ControlPanelState.RETRACTED;
            break;
        }
    }
    /**
     * This allows another class to change our control state.
     * 
     * @param newState The state to change to
     */
    public void setDesiredState(ControlPanelState newState)
    {
        desiredState = newState;
    }

    /**
     * Returns our current state
     * 
     * @return The current control state.
     */
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

    /**
     * Zeros our position. This is a required part of Subsystem and is not
     * relevant here. Always returns false.
     * @return false
     */
    public boolean zeroPosition()
    {
        return false;
    }

    /**
     * An enum to list all of our states.
     */
    public enum ControlPanelState
    {
        EXTENDED,
        RETRACTED,
        ROTATION_CONTROL,
        POSITION_CONTROL,
        MANUAL_CLOCKWISE,
        MANUAL_ANTICLOCKWISE,
    }

    /**
     * Extends our spinner
     */
    private void extendSpinner()
    {
        RobotMap.getControlPanelSolenoid().set(Constants.kControlPanelExtendedState);;
    }

    /**
    * Retracts spinner
    */
    private void retractSpinner()
    {
         RobotMap.getControlPanelSolenoid().set(Constants.kControlPanelRetractedState);;
    }

    /**
     * Sets our spinner motor speed.
     */
    private void setSpinnerSpeed(double speed)
    {
        RobotMap.getControlPanelSystem().set(speed);
    }
    
}
