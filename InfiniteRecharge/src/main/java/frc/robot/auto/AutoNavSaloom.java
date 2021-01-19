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

public class AutoNavSaloom extends AutoMode {
    private String name = "AUTO_NAV_SALOOM";
    private AutoSelection autoType = AutoSelection.AUTO_NAV_SALOOM;
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
            if(Drive.getInstance().actionSensorDrive(1, 0, 2.286)) {
                return true;
            } else {
                return false;
            }
            case 1:
            if(Drive.getInstance().actionGyroTurn(90, 1)) {
                return true;
            } else {
                return false;
            }
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