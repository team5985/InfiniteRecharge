/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {
  AutoController autoController;
  TeleopController teleopController;
  public static Timer _timer;

  @Override
  public void robotInit() {
    CameraServer.getInstance().startAutomaticCapture(0);
    _timer.reset();
    
    autoController = new AutoController();
    teleopController = new TeleopController();

    // Subsystems are classes that contain only the logic (a controller) for controlling each subsystem
    RobotWrangler.setSystem(RobotMap.getRobotWranglerSystem(), RobotMap.getRobotWranglerLimits()); // The Robot gives each Subsystem its physical devices that it will control
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
