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
    private static Indexer m_instance;

    public static Indexer getInstance() {
        if (m_instance == null) {
            m_instance = new Indexer();
        }

        return m_instance;
    }

    private IndexerState currentState;
    private IndexerState desiredState;
    
    private Indexer() {
        currentState = IndexerState.IDLE;
        desiredState = IndexerState.IDLE;
    }

    public boolean zeroPosition() {
        return false;
    }

    public double getPosition() {
        //Somewhere over the rainbow - proably
        return 0;
    }

    public void update() {
        
        switch(currentState) {
            case INDEXING:
                RobotMap.getIndexerSystem().set(ControlMode.PercentOutput, Constants.kIndexerSpeed);
                RobotMap.getIndexerSolenoid().set(true);
                
                
                currentState = desiredState;
            break;
            case UNINDEXING:
                RobotMap.getIndexerSystem().set(ControlMode.PercentOutput, Constants.kIndexerAntijam);
                RobotMap.getIndexerSolenoid().set(false);
                currentState = desiredState;

            break;
            default: 
                RobotMap.getIndexerSystem().set(ControlMode.PercentOutput, Constants.kIndexerSpeed * -1);
                                RobotMap.getIndexerSolenoid().set(false);
                currentState = desiredState;
            break;
                
            
        }
    }

    public enum IndexerState {
        INDEXING,
        UNINDEXING,
        IDLE,
    }

    public IndexerState getCurrentState() {
        return currentState;
    }
    public IndexerState setDesiredState(IndexerState state) {
        desiredState = state;
        return currentState;
    }
}
