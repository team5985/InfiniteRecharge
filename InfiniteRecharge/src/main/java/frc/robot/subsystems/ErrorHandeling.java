// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/** Add your docs here. */
public class ErrorHandeling extends Subsystem{

    private static ErrorHandeling m_instance;

    public ErrorHandeling GetInstance() {
        if(m_instance == null) {
            m_instance = new ErrorHandeling();
        }
        return m_instance;
    }

    @Override
    public void update() {
        checkCan();
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
    public void checkCan() {
        try {
            RobotMap.getLeftDriveA().getName();
        } catch (Exception e) {
            SmartDashboard.putBoolean("CAN", false);
        }   
    }

   
}

