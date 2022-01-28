package frc.sequencer;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;

public class ShootStep implements SequenceStepIf {
    

    public void stepStart()
    {
        Shooter.getInstance().setDesiredState(Shooter.ShooterState.SHOOTING);
    }

    public void stepEnd()
    {
        Shooter.getInstance().setDesiredState(Shooter.ShooterState.IDLE);
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
