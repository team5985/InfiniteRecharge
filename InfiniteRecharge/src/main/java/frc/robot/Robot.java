/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.util.Color;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Timer;
import frc.util.ColourSensor;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends TimedRobot {
  AutoController autoController;
  TeleopController teleopController;
  ColourSensor colourSensor;
  Compressor comp;
  Solenoid solenoid;
  static Timer _timer = new Timer();
  


  public void robotInit() {
 
    /* autoController.setDefaultOption("WIN", kDefaultAuto);
    autoController.addOption("Loose :(", kCustomAuto);
    SmartDashboard.putData("Auto choices", autoController);
    CameraServer.getInstance().startAutomaticCapture(); */
    /*
    Drive drivetrain = Drive.getInstance();
    drivetrain.setSystem(RobotMap.getLeftDrive(), RobotMap.getRightDrive()); */
    CameraServer.getInstance().startAutomaticCapture(0);
    _timer.reset();
    comp = new Compressor(Constants.kPcmCanID);
    autoController = new AutoController();
    teleopController = new TeleopController();
    colourSensor = ColourSensor.getInstance();
    solenoid = new Solenoid(Constants.kPcmCanID, 7);
    // Subsystems are classes that contain only the logic (a controller) for controlling each subsystem
    //RobotWrangler.setSystem(RobotMap.getRobotWranglerSystem()); // The Robot gives each Subsystem its physical devices that it will control
/*
   SmartDashboard .putNumber("Shooter P Gain", Constants.kShooterP);
        SmartDashboard.putNumber("Shooter I Gain", Constants.kShooterI);
        SmartDashboard.putNumber("Shooter D Gain", Constants.kShooterD);
        SmartDashboard.putNumber("Shooter I Zone", Constants.kShooterIz);
        SmartDashboard.putNumber("Shooter Feed Forward", Constants.kShooterFF);
    RobotWrangler.setSystem(RobotMap.getRobotWranglerSystem(), RobotMap.getRobotWranglerLimits()); // The Robot gives each Subsystem its physical devices that it will control
*/
    


    //Climber.setSystem(RobotMap.getElevatorSystem(), RobotMap.getWinchSystem(), RobotMap.getClimberSolenoid(), RobotMap.getClimberLimits());
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
  public void teleopInit() {
    comp.start();

  }


  @Override
  public void teleopPeriodic() {
    
    colourSensor.update();
    SmartDashboard.putString("Current Colour", ColourSensor.getInstance().getColourString());
    SmartDashboard.putNumber("Rotations", ColourSensor.getInstance().getControlPanelRotations());
    SmartDashboard.putNumber("Total Colour Changes", ColourSensor.getInstance().getColourChange());
    SmartDashboard.putBoolean("Is it going clockwise ", ColourSensor.getInstance().getControlPanelDirection());
    SmartDashboard.putNumber("R", ColourSensor.getInstance().ColourSensorR);
    SmartDashboard.putNumber("G", ColourSensor.getInstance().ColourSensorG);
    SmartDashboard.putNumber("B", ColourSensor.getInstance().ColourSensorB);
    solenoid.set(true);

  }

  @Override
  public void testPeriodic() {
  }
}

