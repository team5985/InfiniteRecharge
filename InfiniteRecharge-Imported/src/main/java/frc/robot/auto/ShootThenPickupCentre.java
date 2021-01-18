package frc.robot.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.AutoController.AutoSelection;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Shooter.ShooterState;

public class ShootThenPickupCentre extends AutoMode {
    private String name = "Shoot preload then pickup from centre";
    private AutoSelection autoType = AutoSelection.SHOOT_THEN_PICKUP_CENTRE;
    
    @Override
    public boolean getExit() {
        return false;
    }

    @Override
    public void init() {
        
    }

    @Override
    public boolean runStep(int step) {
        switch(step) {
            case 0:
            double tx = Vision.getInstance().getAngleToTarget();
            double visionSteering = tx * Constants.kVisionTurnKp;
            Drive.getInstance().arcadeDrive(1.0, visionSteering, 0.0);
            
            Intake.getInstance().setDesiredState(IntakeState.RETRACTED);
            
            if(Shooter.getInstance().getShooterAcceptableSpeed(Shooter.getInstance().getShooterTargetSpeed())) {
                Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
                Indexer.getInstance().setDesiredState(IndexerState.INDEXING);
            } else {
                Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
                Indexer.getInstance().setDesiredState(IndexerState.IDLE);
            }

            // TODO Detect When Each Ball Is Shot

            if (DriverStation.getInstance().getMatchTime() < 10) {
                return true;
            }

            break;
           
            case 1:
            return Drive.getInstance().actionGyroTurn(0.0, 1);
            
            case 2:
            Vision.getInstance().disableVision();
            Shooter.getInstance().setDesiredState(ShooterState.IDLE);
            Indexer.getInstance().setDesiredState(IndexerState.IDLE);
            Intake.getInstance().setDesiredState(IntakeState.INTAKING);

            Drive.getInstance().actionSensorDrive(0.2, 0.0, 1.55);
            
            if(Drive.getInstance().getAvgEncoderDistance()>=1.55)
            {
              return true;
            }

            break;

            case 3:
            Shooter.getInstance().setDesiredState(ShooterState.IDLE);
            Indexer.getInstance().setDesiredState(IndexerState.IDLE);
            Intake.getInstance().setDesiredState(IntakeState.INTAKING);
            
            Drive.getInstance().actionSensorDrive(0.2, 0.0, -1.55);            

            if(Drive.getInstance().getAvgEncoderDistance()<=0.25)
            {
                return true;
            }

            break;

            case 4:
            return Drive.getInstance().actionGyroTurn(14.0, 1);

            case 5:
            Intake.getInstance().setDesiredState(IntakeState.EXTENDED);
            
            tx = Vision.getInstance().getAngleToTarget();
            visionSteering = tx * Constants.kVisionTurnKp;
            Drive.getInstance().arcadeDrive(1.0, visionSteering, 0.0);
            
            if(Shooter.getInstance().getShooterAcceptableSpeed(Shooter.getInstance().getShooterTargetSpeed())) {
                Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
                Indexer.getInstance().setDesiredState(IndexerState.INDEXING);
            } else {
                Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
                Indexer.getInstance().setDesiredState(IndexerState.IDLE);
            }

            default:
            return false;
        }

        return false;
    }

}