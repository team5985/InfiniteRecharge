package frc.sequencer;

import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Indexer.IndexerState;

public class IndexInStep implements SequenceStepIf {
    

    public void stepStart()
    {
        Indexer.getInstance().setDesiredState(Indexer.IndexerState.INTAKING);
    }

    public void stepEnd()
    {
        Indexer.getInstance().setDesiredState(Indexer.IndexerState.IDLE);
    }

    public void stepUpdate()
    {
        // Do nothing.
    }

    public String stepName()
    {
        return "ShootStep";
    }

}
