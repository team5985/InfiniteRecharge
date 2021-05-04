package frc.robot.auto;

import frc.robot.AutoController.AutoSelection;
import frc.robot.subsystems.Drive;

public class DistTest extends AutoMode {
    private String name = "Distance Test";
    private AutoSelection autoType = AutoSelection.DISTTEST;
    
    @Override
    public boolean getExit() {
        return false;
    }

    @Override
    public void init() {
        Drive.getInstance().zeroPosition();
    }

    @Override
    public boolean runStep(int step) {
        switch(step) {
            case 0:
            if(Drive.getInstance().actionSensorDrive(0.5, 0, 1)) {
                return true;
            }
            break;

            case 1:
            Drive.getInstance().actionSensorDrive(0.5, 0.0, 0.0);
            return false;

    }
    return false;


    }

}