/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.*;
import frc.robot.config.*;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder; 

/**
 * Add your docs here.
 */
public class Shooter extends Subsystem {

    public double getPosition() {
        return 0.0;
    }

    public boolean zeroPosition() {
        return false;
    }

    public booolean shoot(double targetPower) {
        RobotMap.getShooterSystem.set(targetPower);
        return getShooterTargetSpeed();
    }

    public long boot(double distance, int team) {
        long fouls = 0; //Hopefully :/
        //Send robot flying to the other side of the field and beyond!
        return fouls;
    }
    public boolean getShooterTargetSpeed() {
        return (velocity >= Config.kShooterMinSpeed);
    }
    public void powerIndexer() {
        //set indexer speed.
        RobotMap.getIndexer().set(Constants.kIndexerSpeed);
    }
    public void removeShooterJam() {
        RobotMap.getShooter().set(-1);
        //Set indexer -1;
        RobotMap.getIndexer().set(-1);
    }
    public double getShooterRPM() {
        return RobotMap.shooterMotorA.getVelocity()
    }


}
