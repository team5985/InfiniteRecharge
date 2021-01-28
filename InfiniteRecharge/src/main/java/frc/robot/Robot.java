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
import com.revrobotics.SparkMax;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.subsystems.ControlPanel.ControlPanelState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.robot.TeleopController;

import frc.util.ColourSensor;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {
  AutoController autoController;
  TeleopController teleopController;
  ColourSensor colourSensor;
  ControlPanel m_controlPanel;
  Compressor comp;
  //Solenoid solenoid;
  static Timer _timer = new Timer();
  Joystick js = new Joystick(Constants.kJoystickPort);


  public void robotInit() {
 
    /* autoController.setDefaultOption("WIN", kDefaultAuto);
    autoController.addOption("Loose :(", kCustomAuto);
    SmartDashboard.putData("Auto choices", autoController);
    CameraServer.getInstance().startAutomaticCapture(); */
  

    ControlPanel controlPanel = ControlPanel.getInstance();
    ColourSensor colourSensor = ColourSensor.getInstance();
    
    
    CameraServer.getInstance().startAutomaticCapture(0);
    _timer.reset();
    comp = new Compressor(Constants.kPcmCanID);
    autoController = new AutoController();
    teleopController = new TeleopController();
    colourSensor = ColourSensor.getInstance();
    LiveWindow.disableAllTelemetry();
    //solenoid = new Solenoid(Constants.kPcmCanID, 7);
    m_controlPanel = new ControlPanel();

    // Subsystems are classes that contain only the logic (a controller) for controlling each subsystem
    //RobotWrangler.setSystem(RobotMap.getRobotWranglerSystem()); // The Robot gives each Subsystem its physical devices that it will control

    SmartDashboard.putNumber("Shooter P Gain", Constants.kShooterP);
    SmartDashboard.putNumber("Shooter I Gain", Constants.kShooterI);
    SmartDashboard.putNumber("Shooter D Gain", Constants.kShooterD);
    SmartDashboard.putNumber("Shooter I Zone", Constants.kShooterIz);
    SmartDashboard.putNumber("Shooter Feed Forward", Constants.kShooterFF);



    SmartDashboard.putNumber("Servo", 0.0);
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
    teleopController.callStateMachine();  // Also runs drivetrain
    Shooter.getInstance().update();
    Indexer.getInstance().update();
    Intake.getInstance().update();
    ControlPanel.getInstance().update();  //FIXME
    
    //The control panel can be setup more easily, and you don't need to call ColourSensor
    ColourSensor.getInstance().update(); //FIXME
    Bar.getInstance().update();
    // Climber.getInstance().update();

    //RobotMap.getIntakeServo().set(SmartDashboard.getNumber("Servo", 0.0));

  }


  @Override
  public void testPeriodic() {
  }
}

  /*  colourSensor.update();
   
    solenoid.set(true); */
