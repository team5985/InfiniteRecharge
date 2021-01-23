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
            if(Drive.getInstance().zeroPosition() && Drive.getInstance().resetEncoders()) {
                return true;
            } else {
                return true;
            }
            case 1:
            if(Drive.getInstance().actionSensorDrive(1, 0, 2.15)) {
                return true;
            } else {
                return false;
            }
            case 2:
            if(Drive.getInstance().actionGyroTurn(-90, 1)) {
                return true;
            } else {
                return false;
            }
            case 3:
            if(Drive.getInstance().actionSensorDrive(1, -90, 4)) {
                return true;
            } else {
                return false;
            }

            case 4:
            if(Drive.getInstance().actionGyroTurn(0, 1)) {
                return true;
            } else {
                return false;
            }
            case 5: 
            if(Drive.getInstance().actionSensorDrive(1, 0, 8.3)) {
                return true;
    
            } else {
                return false;
            }
            case 6:
            if(Drive.getInstance().actionGyroTurn(90, 1)) {
                return true;
            } else {
                return false;
            }
            case 7:
            if(Drive.getInstance().actionSensorDrive(1, 90, 10.25)) {
                return true;
            } else {
                return false;
            }
            case 8:
            if(Drive.getInstance().actionGyroTurn(0, 1)) {
                return true;
            } else {
                return false;
            }
            case 9:
            if(Drive.getInstance().actionSensorDrive(1, 0, 11.75)) {
                return true;
            } else {
                return false;
            }
            case 10:
            if(Drive.getInstance().actionGyroTurn(-90, 1)) {
                Drive.getInstance().zeroPosition();
                return true;
            } else {
                return false;
            }
            case 11:
            if(Drive.getInstance().actionSensorDrive(1, 0, 13.5)) {
                return true;
            } else {
                return false;
            }
            case 12:
            if(Drive.getInstance().actionGyroTurn(-90, 1)) {
                return true;
            } else {
                return false;
            }
            case 13:
            if(true) {
                return true;
            } else {
                return false;
            }
            case 14:
            if(Drive.getInstance().actionSensorDrive(1, -90, 14.8)) {
                Drive.getInstance().resetImu();
                return true;
            } else {
                return false;
            }
            case 15:
            if(true) {
                return true;
            } else {
                return true;
            }
            case 16:
            if(Drive.getInstance().actionGyroTurn(-90, 1)) {
                return true;
            } else {
                return false;
            }
            case 17:
            if(Drive.getInstance().actionSensorDrive(1, -90, 16.3)) {
                return true;
            } else {
                return false;
            }
            case 18:
            if(Drive.getInstance().actionGyroTurn(0, 1)) {
                return true;
            } else {
                return false;
            }
            case 19:
            if(Drive.getInstance().actionSensorDrive(1, 0, 21)) {
                return true;
            } else {
                return false;
            }
            case 20:
            if(Drive.getInstance().actionGyroTurn(90, 1)) {
                return true;
            } else {
                return false;
            }
            case 21:
            if(Drive.getInstance().actionSensorDrive(1, 90, 22.5)) {
                return true;
            } else {
                return false;
            }
            case 22:
            if(Drive.getInstance().actionGyroTurn(0, 1)) {
                return true;
            } else {
                return false;
            }
            case 23:
            if(Drive.getInstance().actionSensorDrive(1, 0, 24.5)) {
                return true;
            } else {
                return false;
            }
        }
        return result;
    }

}