package frc.sequencer.jarryd;

import frc.robot.subsystems.Drive;
import frc.sequencer.SequenceStepIf;

public class autoStop implements SequenceStepIf{

    @Override
    public void stepStart() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stepEnd() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stepUpdate() {
        Drive.getInstance().arcadeDrive(0, 0, 0);
        // TODO Auto-generated method stub
        
    }

    @Override
    public String stepName() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
