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

public class AutoNavBounce extends AutoMode {
    private String name = "AUTO_NAV_BOUNCE";
    private AutoSelection autoType = AutoSelection.AUTO_NAV_BOUNCE;
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
            if(Drive.getInstance().actionSensorDrive(0.5, 0, 1.8)) {
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
            if(Drive.getInstance().actionSensorDrive(0.6, 90, 0.9)) {
                return true;
            } else {
                return false;
            }
            case 3:
            if(Drive.getInstance().actionGyroTurn(60, 1)) {
                return true; 
            } else {
                return false;
            }
            case 4:
            if(Drive.getInstance().actionSensorDrive(0.4, 65 , 3.5)) {
                return true;
            } else {
                return false;
            }
            case 5:
            if(Drive.getInstance().actionGyroTurn(5, 1)) {
                return true;
            } else {

                return false;
            }
            case 6:
            if(Drive.getInstance().actionSensorDrive(0.5, 0,  4.4)) {
                return true; 
            } else {
                return false;
            }
            case 7:
            if(Drive.getInstance().actionGyroTurn(-90, 1)) {
                return true;
            } else {

                return false;
            }
            case 8:
            if(Drive.getInstance().actionSensorDrive(0.8, -90, 6.9)) {
                return true; 
            } else {
                return false;
            }
            case 9:
            if(Drive.getInstance().actionSensorDrive(0.5, -90, 4.5)) {
                return true;
            } else {
                return false;
            }
            case 10:
            if(Drive.getInstance().actionGyroTurn(0, 1)) {
                return true;
            } else {
                return false;
            }
            case 11:
            if(Drive.getInstance().actionSensorDrive(0.5, 0, 7)) {
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
            if(Drive.getInstance().actionSensorDrive(0.8, -90, 9.5)) {
                return true;
            } else {
                return false;
            }   
            case 14:
            if(Drive.getInstance().actionSensorDrive(0.5, -90, 8.5)) {
                return true;
            } else {
                return false;
            }
            case 15:
            if(Drive.getInstance().actionGyroTurn(0, 1)) {
                return true;
            } else {
                return false;
            }
            case 16:
            if(Drive.getInstance().actionSensorDrive(0.5, 0, 13)) {
                return true;
            } else {
                return false;
            }
        }
                return result;
    }
}