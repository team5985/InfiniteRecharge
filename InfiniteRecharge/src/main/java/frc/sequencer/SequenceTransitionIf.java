package frc.sequencer;

public interface SequenceTransitionIf
{
    /**
     * Called when the step that this transition relates to is started.
     * This can be used to save the starting state for transitions where a delta value
     * is used (eg, a step to move 2 metres will need to remember where we started
     * from in order to calculate how far we have moved).
     */
    public void transStart();

    /**
     * Called every scan of the sequencer. This allows the transition class to monitor
     * values over time.
     * This method must calculate and return whether the step has completed.
     * @return <code>true</code> if the step has completed.
     */
    public boolean transUpdate();

    /**
     * Returns whether the step has completed.
     * 
     * @return <code>true</code> if the step has completed.
     */
    public boolean isTransComplete();

    /** 
     * Returns the steps to continue on with once this transition has completed.
     * 
     * @return The next step(s).
     */
    public SequenceStepIf[] nextSteps();

    /**
     * Returns the transitions for the next sequence steps.
     * @return
     */
    public SequenceTransitionIf[] nextTransitions();

    /**
     * Returns whether the next step ends when <code>true</code> ALL of the
     * transitions have finished, or <code>false</code> when only ONE of the
     * transitions have finished.
     * 
     * If <code>true</code> then the steps from all of the transitions will be
     * executed. If <code>false</code> then only the steps from the first completed
     * transition will be executed.
     * @return The transition type.
     */
    public boolean nextTransType();
}