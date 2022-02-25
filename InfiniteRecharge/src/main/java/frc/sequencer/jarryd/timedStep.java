package frc.sequencer.jarryd;

import edu.wpi.first.wpilibj.Timer;
import frc.sequencer.SequenceTransition;

public class timedStep extends SequenceTransition{

    @Override
    public void transStart() {
        myEndTime = Timer.getFPGATimestamp() + myDelay;
    }

    @Override
    public boolean transUpdate() {
        return isTransComplete();
    }

    @Override
    public boolean isTransComplete() {
        if (Timer.getFPGATimestamp() >= myEndTime)
        {
            return true;
        }
        return false;
    }
    
    private double myDelay = 0;
    public void setDelay (double aDelay)
    {
        myDelay = aDelay;
    }
    private double myEndTime = 0;
}

