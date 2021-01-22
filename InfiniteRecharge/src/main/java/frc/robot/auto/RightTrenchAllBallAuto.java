package frc.robot.auto;

import frc.robot.AutoController.AutoSelection;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Vision;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Constants;

public class RightTrenchAllBallAuto extends AutoMode {
    private String name = "Right Trench All Ball Auto";
    private AutoSelection autoType = AutoSelection.RIGHT_TRENCH_ALL_BALL_AUTO;
    
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
            System.out.println("visionSteering: " + visionSteering);
            // TODO shoot the balls and detect when all have been shot.
            if (DriverStation.getInstance().getMatchTime() < 13) {
                return true;
            }
            break;
            
            case 1:
            Vision.getInstance().disableVision();
            Drive.getInstance().actionSensorDrive(1.0, 0.0, 6.0);

            System.out.println("ENCODER:" + Drive.getInstance().getAvgEncoderDistance());
            if (Drive.getInstance().getAvgEncoderDistance() >= 5.9) {
                return true;
            }
            break;
            
            case 2:
            Drive.getInstance().actionSensorDrive(1.0, 0.0, 0.0);

            System.out.println("ENCODER:" + Drive.getInstance().getAvgEncoderDistance());
            if (Drive.getInstance().getAvgEncoderDistance() <= 0.1) {
                return true;
            }
            break;

            case 3:
            tx = Vision.getInstance().getAngleToTarget();
            visionSteering = tx * Constants.kVisionTurnKp;
            Drive.getInstance().arcadeDrive(1.0, visionSteering, 0.0);
            System.out.println("visionSteering: " + visionSteering);
            // TODO shoot the balls.
            break;

            default:
            return false;
        }

        return false;
    }

}