package frc.robot.auto;

import frc.robot.Constants;
import frc.robot.AutoController.AutoSelection;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Vision;


public class LeftTrenchCrossFieldAuto extends AutoMode {
    private String name = "Left Trench Cross Field Auto";
    private AutoSelection autoType = AutoSelection.LEFT_TRENCH_CROSS_FIELD_AUTO;
    
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
            Drive.getInstance().actionSensorDrive(0.69, 0.0, 0.0);
            
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
            Drive.getInstance().actionSensorDrive(0.69, -90.0, 4.9);

            System.out.println("ENCODER:" + Drive.getInstance().getAvgEncoderDistance());
            if (Drive.getInstance().getAvgEncoderDistance() >= 4.8) {
                return true;
            }
            break;

            case 4:
            Drive.getInstance().actionGyroTurn(0, 0);

            System.out.println("GYRO:" + Drive.getInstance().getYaw());
            if (Drive.getInstance().getYaw() >= -5) {
                return true;
            }
            break;

            case 5:
            double tx = Vision.getInstance().getAngleToTarget();
            double visionSteering = tx * Constants.kVisionTurnKp;
            Drive.getInstance().arcadeDrive(1.0, visionSteering, 0.0);
            // TODO shoot the balls.
            break;

            default:
            return false;
        }

        return false;
    }

}