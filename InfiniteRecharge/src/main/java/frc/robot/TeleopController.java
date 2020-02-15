/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Climber.ElevatorState;
/**
 * Add your docs here.
 */
public class TeleopController {
    private static Timer _timer;
    private static DriverControls _Controls;
    ElevatorState _ElevatorState;


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
        if(_Controls.getAutoclimb()) {
            desiredState = RobotState.CLIMB;
        }
    }

    private void trVictory() {
        if(_timer.getFPGATimestamp() <=0) {
            desiredState = RobotState.VICTORY;
        }
    }

    private void trVision() {
        if(_Controls.getVisionCommand()) 
            desiredState = RobotState.VISION;
    }

    private void trTeleop() {
        desiredState = RobotState.TELEOP;
    }

    


}
