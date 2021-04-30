package frc.sequencer;

import edu.wpi.first.wpilibj.Timer;

public class TimedStep extends SequenceTransition implements SequenceStepIf
{
    double myEndTime;
    double myDelay;

    /**
     * Called when the step that this transition relates to is started.
     * This can be used to save the starting state for transitions where a delta value
     * is used (eg, a step to move 2 metres will need to remember where we started
     * from in order to calculate how far we have moved).
     */
    public void transStart()
    {
        myEndTime = Timer.getFPGATimestamp() + myDelay;
    }

    /**
     * Called every scan of the sequencer. This allows the transition class to monitor
     * values over time.
     * This method must calculate and return whether the step has completed.
     * @return <code>true</code> if the step has completed.
     */
    public boolean transUpdate()
    {
        return isTransComplete();
    }

    /**
     * Returns whether the step has completed.
     * 
     * @return <code>true</code> if the step has completed.
     */
    public boolean isTransComplete()
    {
        if (Timer.getFPGATimestamp() >= myEndTime)
        {
            return true;
        }
        return false;
    }


    /**
     * Called when the step is started.
     * This can be used to save the starting state for steps where a delta value
     * is used (eg, a step to move 2 metres will need to remember where we started
     * from in order to calculate how far we have moved).
     */
    public void stepStart()
    {
    }

    /**
     * Called when the step is ended.
     * This can be used to stop the motion of this step (eg, turn motors off, etc)
     */
    public void stepEnd()
    {
        // Nothing required here.
    }

    /**
     * Called every scan of the sequencer. This allows the step class to monitor
     * values over time.
     */
    public void stepUpdate()
    {
        // Nothing required here.
    }

    public void setDelay(double aDelay)
    {
        myDelay = aDelay;
    }

    public double getDelay()
    {
        return myDelay;
    }

    public String stepName()
    {
        return "TimedStep";
    }
}