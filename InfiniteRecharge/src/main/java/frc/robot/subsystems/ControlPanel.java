package frc.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.util.ColourSensor;

public class ControlPanel extends Subsystem {

    private ControlPanelState currentState;
    private ControlPanelState desiredState;
    private boolean rotationalControlIsTriggered = false;
    private boolean positionalControlIsTriggered = false;
    public void update() {
        ColourSensor.getInstance().runPeriodic();
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
            if(rotationalControlIsTriggered == true)
            {
                int controlPanelRotations = (int) ColourSensor.getControlPanelRotations();
                if(controlPanelRotations  < Constants.kRotationalControlPhase1)    
                {
                    //spin  at a normal speed <60 rpm

                }
                if((controlPanelRotations  < Constants.kRotationalControlPhase2) && (controlPanelRotations >= Constants.kRotationalControlPhase1))    
                {
                    //spin  at a half speed
                    
                }
                if((controlPanelRotations >= Constants.kRotationalControlPhase1) && (controlPanelRotations < Constants.kRotationalControlComplete)){
                    //spin at quarter speed
                }
            }


          
            currentState = desiredState;
            break;
            case POSITION_CONTROL:
            if (positionalControlIsTriggered = true){
                int FMSColour = ColourSensor.getInstance().getFmsColour();
                int currentColour = ColourSensor.getInstance().getColour();
                if(FMSColour != currentColour){
                    //Spin slowly
                }

            }


            currentState = desiredState;
            break;
            case MANUAL_CLOCKWISE:
            // Spin manual not clockwise
            
            
            currentState = desiredState;
            break;
            case MANUAL_ANTICLOCKWISE:
            // Spin manual clockwise

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