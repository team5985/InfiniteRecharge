package frc.robot.auto;

import frc.robot.AutoController.AutoSelection;
import frc.robot.subsystems.Drive;

public class LeaveLine extends AutoMode {
    private String name = "Leave Line";
    private AutoSelection autoType = AutoSelection.LEAVELINE;
    
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
            Drive.getInstance().arcadeDrive(1.0, 0.0, 0.4);

            System.out.println("ENCODER:" + Drive.getInstance().getAvgEncoderDistance());
            if (Drive.getInstance().getAvgEncoderDistance() >= 5.0) {
                return true;
            }
            break;

            case 1:
            Drive.getInstance().arcadeDrive(0.0, 0.0, 0.0);
            break;

            default:
            return false;
        }

        return false;
    }

}