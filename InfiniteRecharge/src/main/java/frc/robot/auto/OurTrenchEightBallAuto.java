package frc.robot.auto;

import frc.robot.AutoController.AutoSelection;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Vision;
import frc.robot.Constants;

public class OurTrenchEightBallAuto extends AutoMode {
    private String name = "Our Trench Eight Ball Auto";
    private AutoSelection autoType = AutoSelection.OUR_TRENCH_EIGHT_BALL_AUTO;
    
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
            double tx = Vision.getInstance().getAngleToTarget();
            double visionSteering = tx * Constants.kVisionTurnKp;
            Drive.getInstance().arcadeDrive(1.0, visionSteering, 0.0);
            // TODO shoot the balls.
            break;
            
            case 1:
            Drive.getInstance().actionSensorDrive(0.4, 0.0, 6.0);

            System.out.println("ENCODER:" + Drive.getInstance().getAvgEncoderDistance());
            if (Drive.getInstance().getAvgEncoderDistance() >= 5.9) {
                return true;
            }
            break;
            /*
            case 2:
            Drive.getInstance().actionSensorDrive(0.4, 0.0, 0.0);

            System.out.println("ENCODER:" + Drive.getInstance().getAvgEncoderDistance());
            if (Drive.getInstance().getAvgEncoderDistance() <= 0.1) {
                return true;
            }
            break;*/

            default:
            return false;
        }

        return false;
    }

}