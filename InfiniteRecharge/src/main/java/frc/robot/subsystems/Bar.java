// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import frc.robot.Constants;
import frc.robot.DriverControls;
import frc.robot.RobotMap;

/** Add your docs here. */
public class Bar extends Subsystem{

    private static Bar m_instance;
    private static DriverControls m_controls;

    public static Bar getInstance() {
        if(m_instance == null) {
            m_instance = new Bar();
        }
        return m_instance;
    }

    public enum BarStates {
        LEFT,
        RIGHT,
        IDLE,
    }

    private BarStates currentState = BarStates.IDLE;
    private BarStates desiredState = BarStates.IDLE;



    public void setLeft() {
        RobotMap.getBarMotor().set(ControlMode.PercentOutput, Constants.kBarLeftSpeed);
    }

    public void setRight() { 
        RobotMap.getBarMotor().set(ControlMode.PercentOutput, Constants.kBarRightSpeed);
    }

    public void setIdle() {
        RobotMap.getBarMotor().set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void update() {
        setBarSpeed();
        // switch(currentState) {
        //     case LEFT:
        //         setLeft();
        //         currentState = desiredState;
        //     break;
        //     case RIGHT:
        //         setRight();
        //         currentState = desiredState;
        //     break;
        //     default:
        //         setIdle();
        //         currentState = desiredState;
        //     break;
        // }
    }

    @Override
    public double getPosition() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean zeroPosition() {
        // TODO Auto-generated method stub
        return false;
    }

    public void setDesiredState(BarStates state) {
        desiredState = state;
    }

    public BarStates getCurrentState() {
        return currentState;
    }

    private void setBarSpeed() {
        RobotMap.getBarMotor().set(ControlMode.PercentOutput, m_controls.getTraverserThrottle());
    }
}
