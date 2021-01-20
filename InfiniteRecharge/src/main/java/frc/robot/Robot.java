/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.TeleopController;
import frc.robot.auto.AutoMode;
public class Robot extends TimedRobot {
  AutoController autoController;
  TeleopController teleopController;
  static Timer _timer = new Timer();
  

  @Override
  public void robotInit() {
    /* autoController.setDefaultOption("WIN", kDefaultAuto);
    autoController.addOption("Loose :(", kCustomAuto);
    SmartDashboard.putData("Auto choices", autoController);
    CameraServer.getInstance().startAutomaticCapture(); */
    
    Drive drivetrain = Drive.getInstance();
    drivetrain.setSystem(RobotMap.getLeftDrive(), RobotMap.getRightDrive(), RobotMap.getLeftDriveEncoder(), RobotMap.getRightDriveEncoder());

    Climber climber = Climber.getInstance();
    climber.setSystem(RobotMap.getWinchSystem(), RobotMap.getClimberSolenoid(), RobotMap.getClimberLimits());
   
    Intake intake = Intake.getInstance();
    intake.setSystem(RobotMap.getIntakeActuationSystem(), RobotMap.getIntakeSystem(), RobotMap.getIntakeServo());
    
    CameraServer.getInstance().startAutomaticCapture(0);
    _timer.reset();

    Vision.getInstance();
    
    autoController = AutoController.getInstance();
    teleopController = TeleopController.getInstance();

    // Subsystems are classes that contain only the logic (a controller) for controlling each subsystem
    //RobotWrangler.setSystem(RobotMap.getRobotWranglerSystem()); // The Robot gives each Subsystem its physical devices that it will control

    // SmartDashboard.putNumber("Shooter P Gain", Constants.kShooterP);
    // SmartDashboard.putNumber("Shooter I Gain", Constants.kShooterI);
    // SmartDashboard.putNumber("Shooter D Gain", Constants.kShooterD);
    // SmartDashboard.putNumber("Shooter I Zone", Constants.kShooterIz);
    // SmartDashboard.putNumber("Shooter Feed Forward", Constants.kShooterFF);


    // SmartDashboard.putNumber("Servo", 0.0);
  }

  @Override
  public void robotPeriodic() {
    if (isEnabled() && !Drive.getInstance().getBrakes()) { // set to brake when enabled if not already set to brake
      Drive.getInstance().setBrakes(true);
    }
  }

  @Override
  public void autonomousInit() {
    autoController.initialiseAuto();
  }

  @Override
  public void autonomousPeriodic() {
    autoController.runAuto();
    Shooter.getInstance().update();
    Indexer.getInstance().update();
    Intake.getInstance().update();
    
  }
  @Override
  public void teleopPeriodic() {
    teleopController.callStateMachine();  // Also runs drivetrain
    Shooter.getInstance().update();
    Indexer.getInstance().update();
    Intake.getInstance().update();
    // Climber.getInstance().update();

    //RobotMap.getIntakeServo().set(SmartDashboard.getNumber("Servo", 0.0));
  }

  @Override
  public void disabledInit() {
    Drive.getInstance().setBrakes(false); 
    Vision.getInstance().disableVision();
  }

  @Override
  public void testPeriodic() {
  }
}
