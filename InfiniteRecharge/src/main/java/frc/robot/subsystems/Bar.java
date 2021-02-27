// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Constants;
import frc.robot.RobotMap;

/** Add your docs here. */
public class Bar {

    public void setLeft() {
        RobotMap.getBarMotor().set(ControlMode.PercentOutput, Constants.kBarLeftSpeed);
    }

    public void setRight() { 
        RobotMap.getBarMotor().set(ControlMode.PercentOutput, Constants.kBarRightSpeed);
    }

    public void setIdle() {
        RobotMap.getBarMotor().set(ControlMode.PercentOutput, 0);
    }
}
