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
// import edu.wpi.first.wpilibj.command.button.JoystickButton;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Drive.gameMode;
import frc.robot.subsystems.LED.DesiredColour;
import frc.robot.subsystems.LED.LEDState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.TeleopController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {
  AutoController autoController;
  TeleopController teleopController;
  Compressor comp;
  ErrorHandeling m_errors;
  //Solenoid solenoid;
  static Timer _timer = new Timer();
  Joystick js = new Joystick(Constants.kJoystickPort);


  public void robotInit() {

    LED.getInstance().setColour(DesiredColour.RED);


 
    /* autoController.setDefaultOption("WIN", kDefaultAuto);
    autoController.addOption("Loose :(", kCustomAuto);
    SmartDashboard.putData("Auto choices", autoController); */

    Drive drivetrain = Drive.getInstance();
    //drivetrain.setSystem(RobotMap.getLeftDrive(), RobotMap.getRightDrive(), RobotMap.getLeftDriveEncoder(), RobotMap.getRightDriveEncoder());

    Climber climber = Climber.getInstance();
    //climber.setSystem(RobotMap.getIndexerSystem(), RobotMap.getClimberSolenoid(), RobotMap.getClimberLimits());

    Intake intake = Intake.getInstance();

         
    //CameraServer.startAutomaticCapture(0);
    _timer.reset();

    Vision.getInstance();
    
    comp = new Compressor(Constants.kPcmCanID, PneumaticsModuleType.CTREPCM);
    autoController = AutoController.getInstance();
    teleopController = TeleopController.getInstance();
    LiveWindow.disableAllTelemetry();
    //solenoid = new Solenoid(Constants.kPcmCanID, 7);
    LiveWindow.disableAllTelemetry();

    // Subsystems are classes that contain only the logic (a controller) for controlling each subsystem
    //RobotWrangler.setSystem(RobotMap.getRobotWranglerSystem()); // The Robot gives each Subsystem its physical devices that it will control

    // SmartDashboard.putNumber("Shooter P Gain", Constants.kShooterP);
    // SmartDashboard.putNumber("Shooter I Gain", Constants.kShooterI);
    // SmartDashboard.putNumber("Shooter D Gain", Constants.kShooterD);
    // SmartDashboard.putNumber("Shooter I Zone", Constants.kShooterIz);
    // SmartDashboard.putNumber("Shooter Feed Forward", Constants.kShooterFF);
  }

  public void robotPeriodic() {
    //LED.getInstance().update();
    //m_errors.GetInstance().checkCan();

    if (isEnabled() && !Drive.getInstance().getBrakes()) { // set to brake when enabled if not already set to brake
      Drive.getInstance().setBrakes(true);
    }

    SmartDashboard.putNumber("Gyro", Drive.getInstance().getYaw());
    SmartDashboard.putNumber("AvgEncDistance", Drive.getInstance().getAvgEncoderDistance());
  }

  @Override
  public void autonomousInit() {
    Drive.getInstance().setGameMode(Drive.gameMode.AUTO);
    autoController.initialiseAuto();
  }

  @Override
  public void autonomousPeriodic() {
    Drive.getInstance().updateUltrasonics();
    autoController.runAuto();
    Shooter.getInstance().update();
    Indexer.getInstance().update();
    Intake.getInstance().update();
    
  }

 @Override
  public void teleopInit() {
    Drive.getInstance().setGameMode(Drive.gameMode.TELEOP);
    RobotMap.getIndexer().setSelectedSensorPosition(9);
        comp.enableDigital();
    
    Climber.getInstance().zeroPosition();

  }


  @Override
  public void teleopPeriodic() {
    Drive.getInstance().updateUltrasonics();
    teleopController.callStateMachine();  // Also runs drivetrain
    Shooter.getInstance().update();
    Indexer.getInstance().update();
    Intake.getInstance().update();

    // ControlPanel.getInstance().update();  //FIXME
    
    //The control panel can be setup more easily, and you don't need to call ColourSensor
    //ColourSensor.getInstance().update(); //FIXME
    Climber.getInstance().update();
    Bar.getInstance().update();


    //RobotMap.getIntakeServo().set(SmartDashboard.getNumber("Servo", 0.0));
    
  }


  @Override
  public void disabledInit() {
    Drive.getInstance().setGameMode(Drive.gameMode.DISABLED);
    Drive.getInstance().setBrakes(true); 
    Vision.getInstance().disableVision();
  }

  @Override
  public void disabledPeriodic() {
  Drive.getInstance().updateUltrasonics();
  }

  public void testInit() {
    Drive.getInstance().setGameMode(Drive.gameMode.TEST);
  }

  @Override
  public void testPeriodic() {
  Drive.getInstance().updateUltrasonics();
  }
}

  /*  colourSensor.update();
   
    solenoid.set(true); */
