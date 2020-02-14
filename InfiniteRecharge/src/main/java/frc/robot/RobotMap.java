/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.AlternateEncoderType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import frc.util.*;


/**
 * Contains and constructs all of the devices on the robot.
 * This should be kept separate from Robot and the Subsystems so that:
 * - none of their code must change if there is a change in devices used
 * - we can run tests on each subsystem's logic using simulated devices
 */

public class RobotMap {



    public static final int kIntakeCanID = 9;
    public static final int kItntakeActuatorCanID = 10;
    public static final int kShooterACanID = 12;
    public static final int kShooterBCanID = 13;
    public static final int kIndexerCanID = 22;

    //Set up motor controllers - Declaration

    //Shooter
    public static CANSparkMax shooterMotorA = new CANSparkMax(kShooterACanID, MotorType.kBrushless);
	static CANSparkMax shooterMotorB = new CANSparkMax(kShooterBCanID, MotorType.kBrushless);
     static SpeedControllerGroup shooterMotors = new SpeedControllerGroup(shooterMotorA, shooterMotorB); 
    

    //Indexer
    static WPI_VictorSPX indexerMotor = new WPI_VictorSPX(kIndexerCanID);

    //Encoders
    //static EncoderAdapter shooterVelocityEncoder;

    //Init controllers
    /*
    shooterMotorA.setIdleMode(Config.kShooterIdleMode);
	shooterMotorB.setIdleMode(Config.kShooterIdleMode); */

    


    public static SensoredSystem getRobotWranglerSystem() {
        PbSparkMax robotWranglerMotor;

        if (Constants.kUseRobotWranglerNeoEncoder) {
            robotWranglerMotor = new PbSparkMax(Constants.kRobotWranglerSparkCanId, MotorType.kBrushless);
        } else {
            robotWranglerMotor = new PbSparkMax(Constants.kRobotWranglerSparkCanId, MotorType.kBrushless, AlternateEncoderType.kQuadrature, 2048); 
            // if using alternate encoder with neo
        }
        
        SensoredSystem system = new SensoredSystem(robotWranglerMotor);
        return system;
    }

    public static SensoredSystem getIntakeActuationSystem() {
        WPI_TalonSRX intakeActuation = new WPI_TalonSRX(kItntakeActuatorCanID);
        SensoredSystem system = new SensoredSystem(intakeActuation, null);
        return system;
    }
    public static SensoredSystem getIntakeSystem() {
        WPI_VictorSPX intakeMotor = new WPI_VictorSPX(kIntakeCanID);
        SensoredSystem system = new SensoredSystem(intakeMotor, null);
        return system;
    }
    

    public static SpeedControllerGroup getShooter() {
        
        return shooterMotors;
	
	public static CANPIDController getShooterAPIDController() {
		return shooterMotorA.getPIDController();
	}
	public static CANPIDController getShooterBPIDController() {
		return shooterMotorA.getPIDController();
	}
	

    

    public static SensoredSystem getIndexer() {
        SensoredSystem system = new SensoredSystem(indexerMotor, null);

        return system;
    }
        
    



    
}
