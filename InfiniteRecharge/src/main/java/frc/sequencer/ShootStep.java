package frc.sequencer;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;

public class ShootStep extends SequenceTransition implements SequenceStepIf {
    

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

    @Override
    public void transStart() {
        // Do nothing
    }

    @Override
    public boolean transUpdate() {
        // Do nothing
        return isTransComplete();
    }

    @Override
    public boolean isTransComplete() {
        return Shooter.getInstance().getShooterAcceptableSpeed(0.0);
    }

}
