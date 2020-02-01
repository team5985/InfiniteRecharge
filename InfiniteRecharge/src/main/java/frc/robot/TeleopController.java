/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
/**
 * Add your docs here.
 */
public class TeleopController {

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
            break;
            case ENDGAME:
                stEndGame();
            break;
            case VISION:
                stVision();
            break;
            case CLIMB:
                stClimb(); 
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


    


}
