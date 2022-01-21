package frc.sequencer;

public abstract class SequenceStep implements SequenceStepIf
{
    /**
     * Called when the step is started.
     * This can be used to save the starting state for steps where a delta value
     * is used (eg, a step to move 2 metres will need to remember where we started
     * from in order to calculate how far we have moved).
     */
    public abstract void stepStart();

    /**
     * Called when the step is ended.
     * This can be used to stop the motion of this step (eg, turn motors off, etc)
     */
    public abstract void stepEnd();

    /**
     * Called every scan of the sequencer. This allows the step class to monitor
     * values over time.
     */
    public abstract void stepUpdate();
}