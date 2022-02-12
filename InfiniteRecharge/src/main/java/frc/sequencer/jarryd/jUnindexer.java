package frc.sequencer.jarryd;

import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.sequencer.SequenceStepIf;

public class jUnindexer implements SequenceStepIf{

    @Override
    public void stepStart() {
        Indexer.getInstance().setDesiredState(IndexerState.UNINDEXING);   
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
        return "Unindexer";
    }
    
}
