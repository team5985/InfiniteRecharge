/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.LinkedList;

import javax.swing.plaf.basic.BasicBorders.RolloverButtonBorder;

import edu.wpi.first.hal.simulation.RoboRioDataJNI;
// import edu.wpi.first.wpilibj.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.LED.DesiredColour;
import frc.sequencer.Sequence;
import frc.sequencer.aSequenceTest;
import frc.sequencer.Sequencer;
import frc.sequencer.jarryd.SequenceTest;
import frc.sequencer.jarryd.autoCollisionDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Robot extends TimedRobot {
  AutoController autoController;
  TeleopController teleopController;
  Compressor comp;
  ErrorHandeling m_errors;
  //Solenoid solenoid;
  static Timer _timer = new Timer();
  Joystick js = new Joystick(Constants.kJoystickPort);


  public void robotInit() {
    // START Sequence setup section
    // This grabs the different sequences and adds them to the
    // SmartDashboard menu selection.
    // As an example, SequenceTest.getSequences() has been setup
    // to provide some sequences, however others can be added as
    // well. Simply create a list of Sequence objects in seqList
    // and they will be added to the chooser menu.
    // Note that the very first item in the list will become the
    // default selection.
    LinkedList<Sequence> seqList = new LinkedList<Sequence>();
    seqList.addAll(aSequenceTest.getSequences());
    seqList.addAll(SequenceTest.getSequences());
    seqChooser = new SendableChooser<Sequence>();
    boolean first = true;
    for (Sequence s : seqList)
    {
      if (first)
      {
        first = false;
        seqChooser.setDefaultOption(s.getName(), s);
        myDefault = s;
      }
      else
      {
        seqChooser.addOption(s.getName(), s);
      }
    }
    SmartDashboard.putData(seqChooser);
    robotChooser = new SendableChooser<String>();
    robotChooser.setDefaultOption("Phoenix", "Phoenix");
    robotChooser.addOption("DriveBase", "DriveBase");
    SmartDashboard.putData(robotChooser);
    //END Sequence setup section


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
    SmartDashboard.putBoolean("Default", seqChooser.getSelected() == myDefault);
  }

  SendableChooser<Sequence> seqChooser;
  Sequence myDefault = null;
  Sequencer mySeq;
  SendableChooser<String> robotChooser;

   

  @Override
  public void autonomousInit() {
    Drive.getInstance().setGameMode(Drive.gameMode.AUTO);
    Drive.getInstance().zeroPosition();
    Indexer.getInstance().setDesiredState(IndexerState.IDLE);
    // autoController.initialiseAuto();

    // The following code gets whichever sequence is selected
    // from the SmartDashboard and sets up the sequencer to
    // run it.
    Sequence selectedAuto = seqChooser.getSelected();
    Drive.getInstance().setAngle(getFieldAngle(selectedAuto.getStartPos()));
    mySeq = new Sequencer();
    mySeq.setInitialSteps(selectedAuto.getInitialSteps());
    mySeq.setInitialTransitions(selectedAuto.getInitialTransitions());
    mySeq.sequenceStart();
  }

  @Override
  public void autonomousPeriodic() {
    Drive.getInstance().updateUltrasonics();
    mySeq.update();
    Drive.getInstance().autoUpdate();
    SmartDashboard.putString("Auto Step", mySeq.getStepName());
    // autoController.runAuto();
    Shooter.getInstance().update();
    Indexer.getInstance().update();
    Intake.getInstance().update();
    
  }

 @Override
  public void teleopInit() {
    Drive.getInstance().setGameMode(Drive.gameMode.TELEOP);
    Indexer.getInstance().setDesiredState(IndexerState.IDLE);
    RobotMap.getIndexer().setSelectedSensorPosition(9);
        comp.enableDigital();
    
    Climber.getInstance().zeroPosition();
    jcd = new autoCollisionDrive();

    jcdcount = 0;
  }
  autoCollisionDrive jcd;
  int jcdcount = 0;

  double maxAccel = 0;



  @Override
  public void teleopPeriodic() {
    //Drive.getInstance().updateUltrasonics();
    teleopController.callStateMachine();  // Also runs drivetrain
    Shooter.getInstance().update();
    Indexer.getInstance().update();
    Intake.getInstance().update();

    // ControlPanel.getInstance().update();  //FIXME
    
    //The control panel can be setup more easily, and you don't need to call ColourSensor
    //ColourSensor.getInstance().update(); //FIXME
    Climber.getInstance().update();
    Bar.getInstance().update();
    if (jcd.transUpdate())
    {
      jcdcount++;
    }
    // System.out.println("count: " + jcdcount);
    if (DriverControls.getDriverControlsInstance().getStick().getTrigger())
    {
      maxAccel = 0;
    }
    
    SmartDashboard.putNumber("jcd count", jcdcount);
    
    double xAccel = Drive.getInstance().getImuInstance().getWorldLinearAccelX();
    double yAccel = Drive.getInstance().getImuInstance().getWorldLinearAccelY();
    double accel = (Math.sqrt(xAccel*xAccel+yAccel*yAccel));

    if (maxAccel < accel){
      maxAccel = accel;
    }
    // System.out.println("max accel: " + maxAccel);
    // System.out.println("" + accel);
    //RobotMap.getIntakeServo().set(SmartDashboard.getNumber("Servo", 0.0));
    
  }


  @Override
  public void disabledInit() {
    Drive.getInstance().setGameMode(Drive.gameMode.DISABLED);
    Drive.getInstance().setBrakes(false); 
    Vision.getInstance().disableVision();
  }

  @Override
  public void disabledPeriodic() {
  Drive.getInstance().updateUltrasonics();
  }

  public void testInit() {
    Drive.getInstance().setGameMode(Drive.gameMode.TEST);
  }
int ctr = 0;
  @Override
  public void testPeriodic() {
    ctr++;
    DriverStation.reportWarning("Test " + ctr, false);
  Drive.getInstance().updateUltrasonics();
  }

  private static double getFieldAngle(int aPosition)
  {
    if (aPosition == 1)
    {
      return -91.5;
    }
    if (aPosition == 2)
    {
      return -46.5;
    }
    if (aPosition == 3)
    {
      return -1.5;
    }
    if (aPosition == 4)
    {
      return 43.5;
    }
    return 0.0;
  }
}

  /*  colourSensor.update();
   
    solenoid.set(true); */

  