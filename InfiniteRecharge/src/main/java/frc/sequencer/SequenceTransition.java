package frc.sequencer;

import java.util.LinkedList;
import java.util.List;

public abstract class SequenceTransition implements SequenceTransitionIf
{
    /** 
     * Returns the steps to continue on with once this transition has completed.
     * 
     * @return The next step(s).
     */
    public SequenceStepIf[] nextSteps()
    {
        return myNextSteps.toArray(new SequenceStepIf[myNextSteps.size()]);
    }

    /**
     * Returns the transitions for the next sequence steps.
     * @return
     */
    public SequenceTransitionIf[] nextTransitions()
    {
        return myNextTrans.toArray(new SequenceTransitionIf[myNextTrans.size()]);
    }

    private List<SequenceStepIf> myNextSteps = new LinkedList<SequenceStepIf>();
    private List<SequenceTransitionIf> myNextTrans =
        new LinkedList<SequenceTransitionIf>();
    private boolean myNextTransType = false;

    public void setNextSteps(SequenceStepIf ... aNextSteps)
    {
        myNextSteps = new LinkedList<SequenceStepIf>();
        if (aNextSteps != null)
        {
            for (SequenceStepIf step : aNextSteps)
            {
                myNextSteps.add(step);
            }
        }
    }

    public void addNextStep(SequenceStepIf aNextStep)
    {
        myNextSteps.add(aNextStep);
    }

    public void setNextTrans(SequenceTransitionIf ... aNextTrans)
    {
        myNextTrans = new LinkedList<SequenceTransitionIf>();
        if (aNextTrans != null)
        {
            for (SequenceTransitionIf trans : aNextTrans)
            {
                myNextTrans.add(trans);
            }
        }
    }

    public void addNextTrans(SequenceTransitionIf aNextTrans)
    {
        myNextTrans.add(aNextTrans);
    }
    
    public boolean nextTransType()
    {
        return myNextTransType;
    }
}