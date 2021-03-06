/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.config.Config;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Climber.ClimberState;
import frc.robot.subsystems.ControlPanel.ControlPanelState;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.LED.LEDState;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.util.ColourSensor;
import frc.util.JavaUtil;
import frc.util.Luin;
/**
 * Add your docs here.
 */
public class TeleopController {
    private static Timer m_timer;
    private static DriverControls m_controls;
    private static Config m_config;
    private static Drive m_drive;
    private static Shooter m_shooter;
    private static Intake m_intake;
    private static Indexer m_indexer;
    private static Climber m_climber;
    private static Vision m_vision;
    private static Subsystem m_Subsystem;
    private static JavaUtil m_javaUtil;
    private static ControlPanel m_controlPanel;
    private static ColourSensor m_colourSensor;
    private static RobotMap m_robotMap;
    private static LED m_LED;

    private static Luin m_luin;

    private RobotState currentState;
    private RobotState desiredState;

    public double gameTime = Timer.getMatchTime();

    private static TeleopController mInstance;
    
    public static TeleopController getInstance() {
        if (mInstance == null) {
            mInstance = new TeleopController();
        }
        return mInstance;
    }

    public enum RobotState {
        TELEOP,
        ENDGAME,
        // VISION,
        // CLIMB,
        VICTORY,
    }

    private TeleopController() {
        currentState = RobotState.TELEOP;
        desiredState = RobotState.TELEOP;

        m_luin = new Luin();

        m_controls = new DriverControls();
        m_vision = Vision.getInstance();
       
        m_config = new Config();
        m_drive = Drive.getInstance();
        m_climber = Climber.getInstance();
        m_shooter = Shooter.getInstance();
        m_intake = Intake.getInstance();
        m_indexer = Indexer.getInstance();
        m_javaUtil = JavaUtil.getInstance();
        m_controlPanel = ControlPanel.getInstance();
        m_LED = LED.getInstance();
    }

    public void callStateMachine() {
        currentState = desiredState;

        switch (currentState) {
            case TELEOP:
                stTeleop();
                trEndgame();
            break;
            case ENDGAME:
                stEndgame();
                trTeleop();
                trVictory();
            break;
            case VICTORY:
                stVictory();
            break;
            default:

            break;

        }
    }

    //States

    private void stTeleop() {
        m_indexer.setDesiredState(IndexerState.IDLE);
        m_controls.getMechanismMode();
        m_shooter.setDesiredState(ShooterState.IDLE);

        
        if(m_controls.getActionCommand()) {
            // System.out.println("action command");
            if(m_controls.getMechanismMode()) {
                m_intake.setDesiredState(IntakeState.INTAKING);
                //m_shooter.setDesiredState(ShooterState.SHOOTING);
                
            } else {
                //check if shooter is at an acceptable speed
                if(m_shooter.getShooterAcceptableSpeed(m_shooter.getShooterTargetSpeed())) {
                     
                    //READY - AIM - FIRE!
                    m_shooter.setDesiredState(ShooterState.SHOOTING);
                    m_indexer.setDesiredState(IndexerState.INDEXING);
                   
                } else {
                    //Let shooter keep spinning up
                    m_shooter.setDesiredState(ShooterState.SHOOTING);
                    m_indexer.setDesiredState(IndexerState.IDLE);
                }
            }

                
            

        } else {
            m_intake.setDesiredState(IntakeState.IDLE);
            m_shooter.setDesiredState(ShooterState.IDLE);
        
        }

        // if (m_controls.getWinchUp()) {
        //     m_climber.setDesiredState(ClimberState.WINCH_MANUAL);
        //     m_climber.winchMove(0.25);
        // } else if (m_controls.get)


        if (m_controls.getVisionCommand()) {
            // if (m_vision.getTargetAcquired()) {
                double tx = m_vision.getAngleToTarget();
                // System.out.println("tx: " + tx);
                double visionSteering = tx * Constants.kVisionTurnKp;
                m_drive.arcadeDrive(1.0, visionSteering, 0.0);
            // }
        } else if (m_luin.getFace()) {
            m_vision.blindLuin();
        } else {
            m_vision.disableVision();
            callDrive();
        }

        if(m_controls.getRotationControlCommand()) { //12
            m_controlPanel.setDesiredState(ControlPanelState.ROTATION_CONTROL);
        } else if(m_controls.getGetPositionControlCommand()) {
            m_controlPanel.setDesiredState(ControlPanelState.POSITION_CONTROL);
        } else if(m_controls.getStickInterupt()){
            m_controlPanel.setDesiredState(ControlPanelState.RETRACTED);
        }

        
    }

    private void stEndgame() {        
        if(m_controls.getAutoclimb()) {
            if(!(m_climber.getTarget())) {
                if(m_climber.getCurrentState() == ClimberState.CLIMBING) {
                    m_climber.setDesiredState(ClimberState.LIFTING);
                } else if(m_climber.getCurrentState() == ClimberState.STOWED) {
                    m_climber.setDesiredState(ClimberState.CLIMBING);
                } else {
                    m_climber.setDesiredState(ClimberState.IDLE);
                }
            }
        }
    }

    private void stVision() {

    }

    private void stVictory() {

    }

    //Transitions
    private void trEndgame() {
        if(DriverControls.getDriverControlsInstance().getAutoclimb()) {
            desiredState = RobotState.ENDGAME;
            
        }
    }

    private void trVictory() {
        if(m_timer.getFPGATimestamp() <=0) {
            desiredState = RobotState.VICTORY;
        }
    }

    private void trTeleop() {
        if (m_controls.getVisionCommand() || m_controls.getTeleopCommand()) {
            desiredState = RobotState.TELEOP;
        }
    }

    public void callDrive() {
        // m_drive.smartDrive(_controls.getDrivePower(), _controls.getDriveSteering(), _controls.getDriveThrottle(), _config.kUseStallSenseTeleopDrive);
        m_drive.arcadeDrive(m_controls.getDriveThrottle(), m_controls.getDriveSteering(), m_controls.getDrivePower());
    }

    public void resetAllSensors() {
        m_drive.resetSensors();
        // m_climber.resetSensors();
        m_intake.resetSensors();
        // Spinny thing?
    }
}
