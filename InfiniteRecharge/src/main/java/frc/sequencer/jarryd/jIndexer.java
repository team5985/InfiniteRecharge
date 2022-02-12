package frc.sequencer.jarryd;

import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.sequencer.SequenceStepIf;

public class jIndexer implements SequenceStepIf{

    @Override
    public void stepStart() {
        Indexer.getInstance().setDesiredState(IndexerState.INDEXING);
    }

    @Override
    public void stepEnd() {
        Indexer.getInstance().setDesiredState(IndexerState.IDLE);
    }

    @Override
    public void stepUpdate() {       
    }

    @Override
    public String stepName() {
        return "Indexer";
    }
    
}
