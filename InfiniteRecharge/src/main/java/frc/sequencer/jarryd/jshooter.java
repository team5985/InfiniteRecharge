package frc.sequencer.jarryd;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.sequencer.SequenceStepIf;
import frc.sequencer.SequenceTransition;

public class jshooter extends SequenceTransition implements SequenceStepIf {

    @Override
    public void stepStart() {
        Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stepEnd() {
        Shooter.getInstance().setDesiredState(ShooterState.IDLE);
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stepUpdate() {
        
        Shooter.getInstance().update();
        Shooter.getInstance().getShooterRPM();
        // System.out.println("shoot rpm" + Shooter.getInstance().getShooterRPM());
        // TODO Auto-generated method stub
        
    }

    @Override
    public String stepName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void transStart() {
        Shooter.getInstance().getShooterRPM();
        Shooter.getInstance().getShooterTargetSpeed(); 
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean transUpdate() {
        return isTransComplete();
    }
    
    @Override
    public boolean isTransComplete() {
        return Shooter.getInstance().getShooterAcceptableSpeed(0.0);
    } 
}
