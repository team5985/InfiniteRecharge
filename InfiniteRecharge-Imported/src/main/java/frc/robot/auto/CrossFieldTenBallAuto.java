package frc.robot.auto;

import frc.robot.AutoController.AutoSelection;
import frc.robot.subsystems.Drive;

public class CrossFieldTenBallAuto extends AutoMode {
    private String name = "Cross Field Ten Ball Auto";
    private AutoSelection autoType = AutoSelection.CROSS_FIELD_TEN_BALL_AUTO;
    
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
            Drive.getInstance().actionSensorDrive(0.4, 0.0, 2.3);

            System.out.println("ENCODER:" + Drive.getInstance().getAvgEncoderDistance());
            if (Drive.getInstance().getAvgEncoderDistance() >= 2.2) {
                return true;
            }
            break;

            case 1:
            Drive.getInstance().actionSensorDrive(0.4, 0.0, 0.0);

            System.out.println("ENCODER:" + Drive.getInstance().getAvgEncoderDistance());
            if (Drive.getInstance().getAvgEncoderDistance() <= 0.1) {
                return true;
            }
            break;

            case 2:
            Drive.getInstance().actionGyroTurn(-90, 0);

            System.out.println("GYRO:" + Drive.getInstance().getYaw());
            if (Drive.getInstance().getYaw() <= -85) {
                return true;
            }
            break;

            case 3:
            Drive.getInstance().actionSensorDrive(0.4, -90.0, 3.0);

            System.out.println("ENCODER:" + Drive.getInstance().getAvgEncoderDistance());
            if (Drive.getInstance().getAvgEncoderDistance() >= 2.9) {
                return true;
            }
            break;

//turn you iece of shit

            default:
            return false;
        }

        return false;
    }

}