/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Constants;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Indexer extends Subsystem {

    public boolean zeroPosition() {
        return false;
    }

    public double getPosition() {
        //Somewhere over the rainbow - proably
        return 0;
    }

    private IndexerStates currentState;
    private IndexerStates desiredState;

    public void update() {

    switch(currentState) {
        case INDEXING:
            RobotMap.getIndexerSystem().set(ControlMode.PercentOutput, Constants.kIndexerSpeed);
            currentState = desiredState;
        break;
        case UNINDEXING:
            RobotMap.getIndexerSystem().set(ControlMode.PercentOutput, Constants.kIndexerAntijam);
            currentState = desiredState;
        break;
        default: 
            RobotMap.getIndexerSystem().set(ControlMode.PercentOutput, 0);
            currentState = desiredState;
        
    }
}

    public enum IndexerStates {
        INDEXING,
        UNINDEXING,
        IDLE,
    }
}
