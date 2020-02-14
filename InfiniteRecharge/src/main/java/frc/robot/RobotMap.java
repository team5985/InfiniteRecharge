/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
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

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.util.LimitSwitchGroup;
import frc.util.PbDioSwitch;
import frc.util.PbSolenoid;
import frc.util.PbSparkMax;
import frc.util.PbTalonSrx;
import frc.util.SensoredSystem;
import frc.util.SolenoidAdapter;

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
	 static CANEncoder shooterVelocityEncoder = new CANEncoder(shooterMotorA);
    

    //Indexer
    static WPI_VictorSPX indexerMotor = new WPI_VictorSPX(kIndexerCanID);

    //Encoders
    //static EncoderAdapter shooterVelocityEncoder;

    //Init controllers
    /*
    shooterMotorA.setIdleMode(Config.kShooterIdleMode);
	shooterMotorB.setIdleMode(Config.kShooterIdleMode); */

   /* 
    shooterMotorA = new CANSparkMax(kShooterACanID, MotorType.kBrushless);
	shooterMotorB = new CANSparkMax(kShooterBCanID, MotorType.kBrushless);
    
    shooterMotorA.setIdleMode(Config.kShooterIdleMode);
    shooterMotorB.setIdleMode(Config.kShooterIdleMode); 

    leftDriveA.setIdleMode(Config.kDriveIdleMode); */


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
	}
	public static CANEncoder getShooterVelocityEncoder() {
		return shooterVelocityEncoder;
	}
	
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
        
    



    
    public static LimitSwitchGroup getRobotWranglerLimits() {
        PbDioSwitch robotWranglerForwardLimit = new PbDioSwitch(Constants.kRobotWranglerForwardLimitDio);
        PbDioSwitch robotWranglerReverseLimit = new PbDioSwitch(Constants.kRobotWranglerReverseLimitDio);
        LimitSwitchGroup limitSwitchGroup = new LimitSwitchGroup(robotWranglerForwardLimit, robotWranglerReverseLimit);
        return limitSwitchGroup;
    }

    public static SensoredSystem getElevatorSystem() {
        PbSparkMax elevatorMotor = new PbSparkMax(Constants.kElevatorMotorCanId, MotorType.kBrushless);
        SensoredSystem system = new SensoredSystem(elevatorMotor);
        return system;
    }
    
    public static SensoredSystem getWinchSystem() {
        PbTalonSrx winchA = new PbTalonSrx(Constants.kWinchACanId);  // Master
        WPI_TalonSRX winchB = new WPI_TalonSRX(Constants.kWinchBCanId);
        WPI_VictorSPX winchC = new WPI_VictorSPX(Constants.kWinchCCanId);
        WPI_VictorSPX winchD = new WPI_VictorSPX(Constants.kWinchDCanId);

        winchA.configFactoryDefault();
        winchA.configContinuousCurrentLimit(Constants.kWinchCurrentLimit);
        winchA.configPeakCurrentLimit(0);
        winchA.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.QuadEncoder, 0, 0);

        winchB.configFactoryDefault();
        winchB.follow(winchA);
        winchC.configFactoryDefault();
        winchC.follow(winchA);
        winchD.configFactoryDefault();
        winchD.follow(winchA);

        SensoredSystem system = new SensoredSystem(winchA);
        return system;
    }

    public static LimitSwitchGroup getClimberLimits() {
        PbDioSwitch elevatorUpperLimit = new PbDioSwitch(Constants.kElevatorUpperLimitDio);
        PbDioSwitch elevatorLowerLimit = new PbDioSwitch(Constants.kElevatorLowerLimitDio);
        LimitSwitchGroup elevatorLimitSwitchGroup = new LimitSwitchGroup(elevatorUpperLimit, elevatorLowerLimit);
        return elevatorLimitSwitchGroup;
    }

    public static SolenoidAdapter getClimberSolenoid() {
        SolenoidAdapter buddySolenoid = new PbSolenoid(Constants.kPcmCanId, Constants.kBuddySolenoidPcmPort);
        return buddySolenoid;
    }
}
