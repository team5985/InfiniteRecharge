/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.config.Config;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Climber.ClimberState;
import frc.robot.subsystems.Indexer.IndexerStates;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Shooter.ShooterState;
/**
 * Add your docs here.
 */
public class TeleopController {
    private static Timer _timer;
    private static DriverControls _controls;
    ClimberState _ElevatorState;
    IntakeState _IntakeState;
    IndexerStates _IndexerState;
    ShooterState _ShooterState;
    private static Drive _drive;
    private static Config _config;
    private static Shooter _shooter;
    private static Intake _intake;
    private static Indexer _indexer;
    private static Subsystem _Subsystem;


    private RobotState currentState;
    private RobotState desiredState;

    public double gameTime = Timer.getMatchTime();

    public enum RobotState {
        TELEOP,
        ENDGAME,
        VISION,
        CLIMB,
        VICTORY,
    }

    public TeleopController() {
        currentState = RobotState.TELEOP;
        desiredState = RobotState.TELEOP;

        _controls = new DriverControls();
        _ElevatorState = ClimberState.STOWED;
        _IntakeState = IntakeState.RETRACTED;
        _IndexerState = IndexerStates.IDLE;
        _ShooterState = ShooterState.IDLE;
       
        _drive = Drive.getInstance();
        _config = new Config();
        _shooter = Shooter.getInstance();
        _intake = Intake.getInstance();
        _indexer = Indexer.getInstance();
    }

    public void callStateMachine() {

        switch (currentState) {
            case TELEOP:
                stTeleop();
                trVision();
                trEnd();
            break;
            case ENDGAME:
                stEndGame();
                trVision();
                trClimb();
                trVictory();
            break;
            case VISION:
                stVision();
                trTeleop();
                trVision();
                trEnd();

            break;
            case CLIMB:
                stClimb(); 
                trVision();
                trEnd();
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
        _indexer.setDesiredState(_IndexerState.IDLE);
        if(_controls.getActionCommand()) {
            System.out.println("action command");
            if(_controls.getMechanismMode()) {

                //Check if the intake is extended
                if(_intake.getPosition() == Constants.kIntakeExtensionRevolutions) {
                    //Intake
                    _intake.setDesiredState(_IntakeState.INTAKING);
                } else {
                    //Extend the intake
                    _intake.setDesiredState(_IntakeState.EXTENDED);
                }
            } else {
                //check if shooter ia at an acceptable speed
                if(_shooter.getShooterAcceptableSpeed(_shooter.getShooterTargetSpeed())) {
                     
                    //READY - AIM - FIRE!
                    _shooter.setDesiredState(_ShooterState.SHOOTING);
                    _indexer.setDesiredState(_IndexerState.INDEXING);
                   
                } else {
                    //Let shooter keep spinning up
                    _shooter.setDesiredState(_ShooterState.SHOOTING);
                    _indexer.setDesiredState(_IndexerState.IDLE);
                }

                
            }

        } else {
            if(_controls.getMechanismMode()) {
                //Check if the intake slide is in
                if(_intake.checkSafeRetraction()) {
                    _intake.setDesiredState(_IntakeState.RETRACTED);
                } else {
                    _intake.setDesiredState(_IntakeState.EXTENDED);
                }
            } else {
                _shooter.setDesiredState(_ShooterState.IDLE);
            }
        }
        callDrive();
        
    }

    private void stEndGame() {

    }

    private void stVision() {

    }

    private void stClimb() {
    
    }

    private void stVictory() {

    }

    //Transitions
    private void trEnd() {
        if(_timer.getMatchTime() <=30) {
            desiredState = RobotState.ENDGAME;
        }
    }

    private void trClimb() {
        if(_controls.getAutoclimb()) {
            desiredState = RobotState.CLIMB;
        }
    }

    private void trVictory() {
        if(_timer.getFPGATimestamp() <=0) {
            desiredState = RobotState.VICTORY;
        }
    }

    private void trVision() {
        if(_controls.getVisionCommand()) 
            desiredState = RobotState.VISION;
    }

    private void trTeleop() {
        desiredState = RobotState.TELEOP;
    }

    public void callDrive() {
       
        if ((currentState != RobotState.CLIMB) || (currentState != RobotState.VISION)){
            // _drive.smartDrive(_controls.getDrivePower(), _controls.getDriveSteering(), _controls.getDriveThrottle(), _config.kUseStallSenseTeleopDrive);
            _drive.arcadeDrive(_controls.getDriveThrottle(), _controls.getDriveSteering(), _controls.getDrivePower());
        }
    }

    


}
