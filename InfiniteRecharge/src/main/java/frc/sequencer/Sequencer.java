package frc.sequencer;

import java.util.LinkedList;
import java.util.List;

public class Sequencer
{
    private List<SequenceStepIf> myCurrentSteps = new LinkedList<SequenceStepIf>();
    private List<SequenceTransitionIf> myCurrentTransitions =
        new LinkedList<SequenceTransitionIf>();
    private String myStepName = "-";
    private boolean myIsRunning = false;
    private List<SequenceStepIf> myInitialSteps = new LinkedList<SequenceStepIf>();
    private List<SequenceTransitionIf> myInitialTransitions =
        new LinkedList<SequenceTransitionIf>();
    private int myStepCounter = 0;

    /**
     * the next step ends when <code>true</code> ALL of the
     * transitions have finished, or <code>false</code> when only ONE of the
     * transitions have finished.
     * {@link SequenceTransitionIf#nextTransType()}
     */
    private boolean myCurrentTransType;

    /**
     * Needs to be run every robot scan to execute the sequence.
     * 
     * @return true if the sequence has finished.
     */
    public boolean update()
    {
        if (myIsRunning == false)
        {
            return true;
        }
        boolean sequenceFinished = false;
        if (myCurrentTransitions.size() == 0)
        {
            myIsRunning = false;
            return true;
        }
        for (SequenceTransitionIf trans : myCurrentTransitions)
        {
            trans.transUpdate();
        }
        boolean stepComplete = checkComplete();
        if (stepComplete == true)
        {
            myStepCounter++;
            //create new step & transition lists
            List<SequenceStepIf> previousSteps = myCurrentSteps;
            List<SequenceTransitionIf> previousTransitions = myCurrentTransitions;
            List<SequenceStepIf> newSteps = new LinkedList<SequenceStepIf>();
            List<SequenceTransitionIf> newTransitions = new LinkedList<SequenceTransitionIf>();
            boolean newTransitionType = false;

            if (myCurrentTransType == true)
            {
                // Iterate through all transitions to get their new transisitons.
                for (SequenceTransitionIf trans : previousTransitions)
                {
                    SequenceStepIf[] newTSteps = trans.nextSteps();
                    for (SequenceStepIf tstep : newTSteps)
                    {
                        newSteps.add(tstep);
                    }
                    SequenceTransitionIf[] newTTrans = trans.nextTransitions();
                    for (SequenceTransitionIf ttrans : newTTrans)
                    {
                        newTransitions.add(ttrans);
                    }
                    if (trans.nextTransType() == true)
                    {
                        newTransitionType = true;
                    }
                }
            }
            else
            {
                // Get the new transitions from only the first transition that completed.
                for (SequenceTransitionIf trans : previousTransitions)
                {
                    if (trans.isTransComplete() == true)
                    {
                        SequenceStepIf[] newTSteps = trans.nextSteps();
                        for (SequenceStepIf tstep : newTSteps)
                        {
                            newSteps.add(tstep);
                        }
                        SequenceTransitionIf[] newTTrans = trans.nextTransitions();
                        for (SequenceTransitionIf ttrans : newTTrans)
                        {
                            newTransitions.add(ttrans);
                        }
                        newTransitionType = trans.nextTransType();
                        break;
                    }
                }
            }
            //call endStep for all steps ending (exclude ones in the new list)
            for (SequenceStepIf step : previousSteps)
            {
                if (newSteps.contains(step) == false)
                {
                    step.stepEnd();
                }
            }
            //replace current step lists with new ones.
            myCurrentSteps = newSteps;
            myCurrentTransitions = newTransitions;
            myCurrentTransType = newTransitionType;
            //call startStep for all new steps and transitions.
            for (SequenceStepIf step : myCurrentSteps)
            {
                if (previousSteps.contains(step) == false)
                {
                    step.stepStart();
                }
            }
            updateStepName();
            for (SequenceTransitionIf trans : myCurrentTransitions)
            {
                trans.transStart();
            }
            //if no new transitions then sequence is finished.
            if (myCurrentTransitions.size() == 0)
            {
                sequenceFinished = false;
            }
        }
        if (sequenceFinished == false)
        {
            for (SequenceStepIf step : myCurrentSteps)
            {
                step.stepUpdate();
            }
        }
        if (sequenceFinished == true)
        {
            myIsRunning = false;
        }
        return sequenceFinished;
    }

    private boolean checkComplete()
    {
        if (myCurrentTransType == true)
        {
            for (SequenceTransitionIf trans : myCurrentTransitions)
            {
                if (trans.isTransComplete() == false)
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            for (SequenceTransitionIf trans : myCurrentTransitions)
            {
                if (trans.isTransComplete() == true)
                {
                    return true;
                }
            }
            return false;
        }
    }

    public String getStepName()
    {
        return myStepName;
    }

    public void setInitialSteps(SequenceStepIf ... aSteps)
    {
        myInitialSteps.clear();
        if (aSteps != null)
        {
            for (SequenceStepIf step : aSteps)
            {
                myInitialSteps.add(step);
            }
        }
    }

    public void setInitialTransitions(SequenceTransitionIf ... aTrans)
    {
        myInitialTransitions.clear();
        if (aTrans != null)
        {
            for (SequenceTransitionIf trans : aTrans)
            {
                myInitialTransitions.add(trans);
            }
        }
    }

    public void sequenceStart()
    {
        if (myIsRunning == false)
        {
            myIsRunning = true;
            myCurrentSteps = myInitialSteps;
            myCurrentTransitions = myInitialTransitions;
            for (SequenceStepIf step : myCurrentSteps)
            {
                step.stepStart();
            }
            updateStepName();
            for (SequenceTransitionIf trans : myCurrentTransitions)
            {
                trans.transStart();
            }
        }
    }

    public void sequenceStop()
    {
        if (myIsRunning == true)
        {
            myIsRunning = false;
            for (SequenceStepIf step : myCurrentSteps)
            {
                step.stepEnd();
            }
        }
    }

    private void updateStepName()
    {
        if (myIsRunning == false)
        {
            myStepName = myStepCounter + " - SQR STOPPED";
        }
        else if (myCurrentSteps.size() == 0)
        {
            myStepName = myStepCounter + " - NO STEP";
        }
        else
        {
            StringBuilder stepNames = new StringBuilder(myStepCounter + " - ");
            for (SequenceStepIf step : myCurrentSteps)
            {
                stepNames.append(step.stepName());
                stepNames.append(' ');
            }
            stepNames.deleteCharAt(stepNames.length()-1);
            myStepName = stepNames.toString();
        }
   
    }
}