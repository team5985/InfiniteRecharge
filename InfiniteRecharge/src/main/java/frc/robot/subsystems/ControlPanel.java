package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.util.ColourSensor;

public class ControlPanel extends Subsystem {

    private ControlPanelState currentState;
    private ControlPanelState desiredState;

    public void update() 
    {
        ColourSensor.getInstance().runPeriodic();
        //state machine
        switch(currentState) {
            case EXTENDED:
            RobotMap.getControlPanelSolenoid().setForward();; //FIXME
            
            /*
            If button is pressed - trigger rotational control
                Desired state = Rotationalcontrol
            If button is pressed - trigger positional control
                Desired state = postionalcontrol
            If button is pressed - trigger manual clockwise control
                Desired state = Manual clockwise
            If button is pressed - trigger manual anticlockwise control
                Desired state = Manual anticlockwise
            If button is pressed - retract   (same button to extend?)
            
                DesiredState = retracted

             */
            desiredState = ControlPanelState.EXTENDED;
            currentState = desiredState;
            break;

            case RETRACTED:
            RobotMap.getControlPanelSolenoid().setReverse(); //FIXME
            /*If button is pressed - extended   (same button to retract?)
            
            DesiredState = extended
            */
            desiredState = ControlPanelState.RETRACTED;
            currentState = desiredState;
            break;

            case ROTATION_CONTROL:
            double controlPanelRotations = ColourSensor.getInstance().getControlPanelRotations();
            
            if(controlPanelRotations < Constants.kRotationalControlTargetRotations)
            {
                double rotationSpeed = Math.min(Math.sqrt(Math.max(controlPanelRotations, 0)),1);
                //Spin at rotationSpeed
            }
            else
            {
                //Stop spinning
                desiredState = ControlPanelState.RETRACTED;
            }   
            currentState = desiredState;
            break;

            case POSITION_CONTROL:
            if (ColourSensor.getInstance().getFmsColour() == ColourSensor.CPANEL_COLOUR_INVALID)
            {
                desiredState = ControlPanelState.EXTENDED;
            }
            else
            {
                int FMSColour = ColourSensor.getInstance().getFmsColour();
                int currentColour = ColourSensor.getInstance().getColour();
                if(FMSColour != currentColour)
                {
                    //Spin slowly
                }
                else
                {
                    // stop spinning
                    desiredState = ControlPanelState.EXTENDED;
                }
            }
            currentState = desiredState;
            break;
            case MANUAL_CLOCKWISE:
            // Spin manual not clockwise
            desiredState = ControlPanelState.EXTENDED;
            
            currentState = desiredState;
            break;


            case MANUAL_ANTICLOCKWISE:
            // Spin manual clockwise
            desiredState = ControlPanelState.EXTENDED;

            currentState = desiredState;
            break;



            default:
            desiredState = ControlPanelState.RETRACTED;

            currentState = desiredState;
            break;
        }
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
    

    
    
}