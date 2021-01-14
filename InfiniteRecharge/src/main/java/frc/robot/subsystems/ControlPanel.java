package frc.robot.subsystems;

import java.util.Random;

import com.revrobotics.ColorMatch;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.util.ColourSensor;

public class ControlPanel extends Subsystem
{
    /**
     * The state we are currently in
     */
    private ControlPanelState currentState;

    private static ControlPanel m_instance;

    /**
     * The state we are going to be in.
     */
    private ControlPanelState desiredState;


    private int randomFMS;
    private int NumberOfEarlyScans;
    private int SecondarySpinCheck;

    public ControlPanel(){
        desiredState = ControlPanelState.RETRACTED;
        randomFMS = 0;
        NumberOfEarlyScans = 0;
        SecondarySpinCheck = 0;
    }

    public static ControlPanel getInstance() {
        if (m_instance == null) {
            m_instance = new ControlPanel();
        }
        return m_instance;
    }

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
        } else {
            newState = false;
        }
        
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
                setSpinnerSpeed(Constants.kRotationalControlSpeed);
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
//Test code 

    
            //Actual Code
                       
            if(newState == true){
                NumberOfEarlyScans = 0;
                SecondarySpinCheck = 0;

            }     
            if (ColourSensor.getInstance().getFmsColour() == Constants.kControlPanelColourInvalid)
            {
                setDesiredState(ControlPanelState.RETRACTED);
            }
            else
            {
                NumberOfEarlyScans++;
                int FMSColour = ColourSensor.getInstance().getFmsColour();
                int currentColour = ColourSensor.getInstance().getColour();
                int TargetColour = (FMSColour +2) % 4;
                if(currentColour != TargetColour && NumberOfEarlyScans >= 20)
                {
                    setSpinnerSpeed(Constants.kControlPanelPoisitionControlSpeed);
                }
                else if(currentColour != TargetColour || NumberOfEarlyScans < 20){
            
                }
            else
            {   
                setSpinnerSpeed(0.2);
                if(SecondarySpinCheck >= 5){
                setSpinnerSpeed(0);
                setDesiredState(ControlPanelState.RETRACTED);
                }
                SecondarySpinCheck++;    
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
            /*  
            if(newState == true){
                randomFMS = (int)(Math.random() * 4);
                System.out.println("The current colour is: " + ColourSensor.getInstance().getColourString(randomFMS));
                NumberOfEarlyScans = 0;
                SecondarySpinCheck = 0;
              
            }
            NumberOfEarlyScans++;
            int FMSColour = randomFMS;
            int currentColour = ColourSensor.getInstance().getColour();
            int TargetColour = (FMSColour +2) % 4;
            if(currentColour != TargetColour && NumberOfEarlyScans >= 50)
            {
                setSpinnerSpeed(Constants.kControlPanelPoisitionControlSpeed);
            }
            else if(currentColour != TargetColour || NumberOfEarlyScans < 50){
            
            }
            else
            {   
                setSpinnerSpeed(0.15);
                if(SecondarySpinCheck >= 5){
                setSpinnerSpeed(0);
                setDesiredState(ControlPanelState.RETRACTED);
                }
                SecondarySpinCheck++;

            }
*/
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
	Solenoid controlPanelSolenoid = new Solenoid(Constants.kPcmCanID, Constants.kControlPanelSolenoidAChannel);

    /**
     * Extends our spinner
     */
    private void extendSpinner()
    {
        controlPanelSolenoid.set(Constants.kControlPanelExtendedState);;
    }

    /**
    * Retracts spinner
    */
    private void retractSpinner()
    {
         controlPanelSolenoid.set(Constants.kControlPanelRetractedState);;

    }

    /**
     * Sets our spinner motor speed.
     */
    private void setSpinnerSpeed(double speed)
    {
        RobotMap.getControlPanelSystem().set(speed);
    
    }
    
}
