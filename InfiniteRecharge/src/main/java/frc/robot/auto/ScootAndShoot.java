package frc.robot.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.AutoController.AutoSelection;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.Shooter.ShooterState;

public class ScootAndShoot extends AutoMode {
    private String name = "Move off Line then shoot";
    private AutoSelection autoType = AutoSelection.SCOOT_AND_SHOOT;
    
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
            Vision.getInstance().disableVision();
            
            Shooter.getInstance().setDesiredState(ShooterState.IDLE);
            Indexer.getInstance().setDesiredState(IndexerState.IDLE);
            Drive.getInstance().arcadeDrive(-1.0, 0.0, 0.4);

            if (Drive.getInstance().getAvgEncoderDistance() <= -2) {
                return true;
            }
            break;
            
            case 1:
            double tx = Vision.getInstance().getAngleToTarget();
            double visionSteering = tx * Constants.kVisionTurnKp;
            Drive.getInstance().arcadeDrive(1.0, visionSteering, 0.0);

            //check if shooter ia at an acceptable speed
            if(Shooter.getInstance().getShooterAcceptableSpeed(Shooter.getInstance().getShooterTargetSpeed())) {
                Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
                Indexer.getInstance().setDesiredState(IndexerState.INDEXING);
            } else {
                Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
                Indexer.getInstance().setDesiredState(IndexerState.IDLE);
            }

            if (DriverStation.getInstance().getMatchTime() < 10) {
                return true;
            }
            break;
            

            case 2:
            Drive.getInstance().arcadeDrive(0.0, 0.0, 0.0);
            Shooter.getInstance().setDesiredState(ShooterState.IDLE);
            Indexer.getInstance().setDesiredState(IndexerState.IDLE);
            Vision.getInstance().disableVision();

            break;

            default:
            return false;
        }

        return false;
    }

}