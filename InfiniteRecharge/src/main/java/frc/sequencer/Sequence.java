package frc.sequencer;

import java.util.LinkedList;
import java.util.List;

public class Sequence {
    public Sequence(String aName, int aStartPos, List<SequenceStepIf> aInitialSteps, List<SequenceTransitionIf> aInitialTransitions)
    {
        myInitialSteps = new LinkedList<SequenceStepIf>();
        myInitialSteps.addAll(aInitialSteps);
        myInitialTransitions = new LinkedList<SequenceTransitionIf>();
        myInitialTransitions.addAll(aInitialTransitions);
        myName = aName;
        myStartPos = aStartPos;
    }

    public Sequence(String aName, int aStartPos)
    {
        myInitialSteps = new LinkedList<SequenceStepIf>();
        myInitialTransitions = new LinkedList<SequenceTransitionIf>();
        myName = aName;
        myStartPos = aStartPos;
    }

    public void setInitialTransitions(SequenceTransitionIf ... anInitialTransitions)
    {
        myInitialTransitions.clear();
        for (SequenceTransitionIf trans : anInitialTransitions)
        {
            myInitialTransitions.add(trans);
        }
    }

    public void setInitialSteps(SequenceStepIf ... anInitialSteps)
    {
        myInitialSteps.clear();
        for (SequenceStepIf step : anInitialSteps)
        {
            myInitialSteps.add(step);
        }
    }

    public String getName()
    {
        return myName;
    }

    public int getStartPos()
    {
        return myStartPos;
    }

    public SequenceStepIf[] getInitialSteps()
    {
        return myInitialSteps.toArray(new SequenceStepIf[myInitialSteps.size()]);
    }

    public SequenceTransitionIf[] getInitialTransitions()
    {
        return myInitialTransitions.toArray(new SequenceTransitionIf[myInitialTransitions.size()]);
    }

    private final String myName;
    private final int myStartPos;
    private List<SequenceStepIf> myInitialSteps;
    private List<SequenceTransitionIf> myInitialTransitions;
}
