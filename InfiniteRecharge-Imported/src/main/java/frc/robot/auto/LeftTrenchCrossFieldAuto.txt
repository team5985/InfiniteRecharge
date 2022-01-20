package frc.robot.auto;

import frc.robot.Constants;
import frc.robot.AutoController.AutoSelection;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Shooter.ShooterState;


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
            Drive.getInstance().actionSensorDrive(0.4, 0.0, 2.5);
            Intake.getInstance().setDesiredState(IntakeState.INTAKING);

            System.out.println("ENCODER:" + Drive.getInstance().getAvgEncoderDistance());
            if (Drive.getInstance().getAvgEncoderDistance() >= 2.4) {
                return true;
            }
            break;

            case 1:
            Drive.getInstance().actionSensorDrive(0.69, 0.0, 0.0);
            Intake.getInstance().setDesiredState(IntakeState.INTAKING);
            
            System.out.println("ENCODER:" + Drive.getInstance().getAvgEncoderDistance());
            if (Drive.getInstance().getAvgEncoderDistance() <= 0.1) {
                return true;
            }
            break;

            case 2:
            Intake.getInstance().setDesiredState(IntakeState.INTAKING);
            Drive.getInstance().actionGyroTurn(-90, 0);

            System.out.println("GYRO:" + Drive.getInstance().getYaw());
            if (Drive.getInstance().getYaw() <= -85) {
                return true;
            }
            break;

            case 3:
            Intake.getInstance().setDesiredState(IntakeState.INTAKING_RETRACTED);
            Drive.getInstance().actionSensorDrive(0.69, -90.0, 4.9);

            if (Drive.getInstance().getAvgEncoderDistance() >= 4.85) {
                return true;
            }
            break;

            case 4:
            Intake.getInstance().setDesiredState(IntakeState.IDLE);
            return Drive.getInstance().actionGyroTurn(0, 0);

            case 5:
            Intake.getInstance().setDesiredState(IntakeState.IDLE);

            double tx = Vision.getInstance().getAngleToTarget();
            double visionSteering = tx * Constants.kVisionTurnKp;
            Drive.getInstance().arcadeDrive(1.0, visionSteering, 0.0);
            
            // Shooting logic
            Shooter.getInstance().setShooterSpeed(2100.0);
            
            if(Shooter.getInstance().getShooterAcceptableSpeed(Shooter.getInstance().getShooterTargetSpeed())) {
                     
                //READY - AIM - FIRE!
                Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
                Indexer.getInstance().setDesiredState(IndexerState.INDEXING);
               
            } else {
                //Let shooter keep spinning up
                Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
                Indexer.getInstance().setDesiredState(IndexerState.IDLE);
            }
            break;

            default:
            return false;
        }

        return false;
    }

}