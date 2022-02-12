package frc.sequencer.jarryd;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.IntakeState;
import frc.sequencer.SequenceStepIf;

public class jintake implements SequenceStepIf{

    @Override
    public void stepStart() {
        Intake.getInstance().setDesiredState(IntakeState.INTAKING);        
    }

    @Override
    public void stepEnd() {
        Intake.getInstance().setDesiredState(IntakeState.IDLE);        
    }

    @Override
    public void stepUpdate() {        
    }

    @Override
    public String stepName() {
        return "Intake";
    }
    
}
