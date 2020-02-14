/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {
  AutoController autoController;
  TeleopController teleopController;

  @Override
  public void robotInit() {
    CameraServer.getInstance().startAutomaticCapture(0);
    autoController = new AutoController();
    teleopController = new TeleopController();

    // Subsystems are classes that contain only the logic (a controller) for controlling each subsystem
    //RobotWrangler.setSystem(RobotMap.getRobotWranglerSystem()); // The Robot gives each Subsystem its physical devices that it will control

    SmartDashboard.putNumber("Shooter P Gain", Constants.kShooterP);
        SmartDashboard.putNumber("Shooter I Gain", Constants.kShooterI);
        SmartDashboard.putNumber("Shooter D Gain", Constants.kShooterD);
        SmartDashboard.putNumber("Shooter I Zone", Constants.kShooterIz);
        SmartDashboard.putNumber("Shooter Feed Forward", Constants.kShooterFF);
    RobotWrangler.setSystem(RobotMap.getRobotWranglerSystem(), RobotMap.getRobotWranglerLimits()); // The Robot gives each Subsystem its physical devices that it will control

    Climber.setSystem(RobotMap.getElevatorSystem(), RobotMap.getWinchSystem(), RobotMap.getClimberSolenoid(), RobotMap.getClimberLimits());
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testPeriodic() {
  }
}
