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

public class AutoNavBarrel extends AutoMode {
    private String name = "AUTO_NAV_BARREL";
    private AutoSelection autoType = AutoSelection.AUTO_NAV_BARREL;
    private boolean result = false;
    
    @Override
    public boolean getExit() {
        return false;
    }

    @Override
    public void init() {
        Drive.getInstance().zeroEncoders();
    }

    @Override
    public boolean runStep(int step) {
        switch(step) {
            case 0:
            if(Drive.getInstance().actionSensorDrive(0.5, 0, 4.1)) {
                return true;
            } else {
                return false;
            }
            case 1:
            if(Drive.getInstance().actionGyroTurn(110, 1)) {
                return true;
            } else {
                return false;
            }
            case 2:
            if(Drive.getInstance().actionSensorDrive(0.5, 110, 5.4)) {
                return true;
            } else {
                return false;
            }
            case 3:
            if(Drive.getInstance().actionGyroTurn(0, 1)) {
                return true;
            } else {
                return false; 
            }
            case 4:
            if(Drive.getInstance().actionSensorDrive(0.5, 0, 4.2)) {
                return true;
            } else {
                return false;
            }
        }
        return result;

    }
}