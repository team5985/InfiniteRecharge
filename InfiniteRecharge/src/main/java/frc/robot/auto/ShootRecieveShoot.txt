package frc.robot.auto;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.AutoController.AutoSelection;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.Indexer.IndexerState;

public class ShootRecieveShoot extends AutoMode {
    private String name = "ShootRecieveShoot";
    private AutoSelection autoType = AutoSelection.SHOOT_RECIEVE_SHOOT;
    private boolean result = false;
    
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
            Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
            if(DriverStation.getInstance().getMatchTime() < 12) {
                Indexer.getInstance().setDesiredState(IndexerState.INDEXING);
            }
            if(DriverStation.getInstance().getMatchTime() < 9) {
                result = true;
            }
            break;
            case 1:

            Indexer.getInstance().setDesiredState(IndexerState.IDLE);
            Shooter.getInstance().setDesiredState(ShooterState.IDLE);
            if(DriverStation.getInstance().getMatchTime() < 5) {
                result = true;
            }
            else {
                result = false;
            }
            break;
            case 2:
            result = false;
            Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
            if(DriverStation.getInstance().getMatchTime() < 3) {
                Indexer.getInstance().setDesiredState(IndexerState.INDEXING);
            }
            default:
            result = false;
        }
        return result;
    }

}