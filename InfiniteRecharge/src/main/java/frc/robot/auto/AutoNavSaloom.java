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
            if(Drive.getInstance().actionSensorDrive(0.5, 0, 2)) {
                System.out.println("Step done");
                return true;
            } else {
                return false;
            }
            case 1:
            if(Drive.getInstance().actionGyroTurn(-90, 1)) {
                return true;
            } else {
                return false;
            }
            case 2:
            if(Drive.getInstance().actionSensorDrive(0.5, -90, 4)) {
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
            if(Drive.getInstance().actionSensorDrive(0.5, 0, 8.572)) {
                return true;
    
            } else {
                return false;
            }
            case 5:
            if(Drive.getInstance().actionGyroTurn(90, 1)) {
                return true;
            } else {
                return false;
            }
            case 6:
            if(Drive.getInstance().actionSensorDrive(0.25, 90, 10.7)) {
                return true;
            } else {
                return false;
            }
            case 7:
            if(Drive.getInstance().actionGyroTurn(0, 1)) {
                return true;
            } else {
                return false;
            }
            case 8:
            if(Drive.getInstance().actionSensorDrive(0.5, 0, 11.7)) {
                return true;
            } else {
                return false;
            }
        }
        return result;
    }

}