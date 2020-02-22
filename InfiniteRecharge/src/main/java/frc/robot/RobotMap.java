/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.config.Config;
import frc.util.EncoderAdapter;
import frc.util.SensoredSystem;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.SpeedControllerGroup;


import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

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
	/**
	 * Robot Configuration
	 */
	// static final boolean useNeoEncoders = true;

	/**
	 * DIO Ports
	 */
	static final int leftDriveEncADioPort = 0;
	static final int leftDriveEncBDioPort = 1;

	static final int rightDriveEncADioPort = 2;
	static final int rightDriveEncBDioPort = 3;

	//Set up motor controllers - Declaration

    //Shooter
    public static CANSparkMax shooterMotorA = new CANSparkMax(kShooterACanID, MotorType.kBrushless);
	static CANSparkMax shooterMotorB = new CANSparkMax(kShooterBCanID, MotorType.kBrushless);
	static SpeedControllerGroup shooterMotors = new SpeedControllerGroup(shooterMotorA, shooterMotorB); 
	static CANEncoder shooterVelocityEncoder = new CANEncoder(shooterMotorA);
	static Solenoid shooterSolenoid = new Solenoid(Constants.kPcmCanId, Constants.kShooterHoodSolenoidAChannel);

	/**
	 * Drivetrain
	 */
	// Left
	static CANSparkMax leftDriveA = new CANSparkMax(Constants.kLeftDriveACanID, MotorType.kBrushless);
	static CANSparkMax leftDriveB = new CANSparkMax(Constants.kLeftDriveBCanID, MotorType.kBrushless);
	//static CANSparkMax leftDriveC = new CANSparkMax(kLeftCCanID, MotorType.kBrushless);
	static SpeedControllerGroup leftDriveMotors = new SpeedControllerGroup(leftDriveA, leftDriveB);

	// Right
	static CANSparkMax rightDriveA = new CANSparkMax(Constants.kRightDriveACanID, MotorType.kBrushless);
	static CANSparkMax rightDriveB = new CANSparkMax(Constants.kRightDriveBCanID, MotorType.kBrushless);
	//static CANSparkMax rightDriveC = new CANSparkMax(kRightCCanID, MotorType.kBrushless);
	static SpeedControllerGroup rightDriveMotors = new SpeedControllerGroup(rightDriveA, rightDriveB);

	/**
	 * Indexer
	 */
	static WPI_VictorSPX indexerMotor = new WPI_VictorSPX(kIndexerCanID);
	static SensoredSystem indexerSystem = new SensoredSystem(indexerMotor, null);

	/**
	 * Intake
	 */
	static WPI_TalonSRX intakeActuation = new WPI_TalonSRX(Constants.kItntakeActuatorCanID);
	static WPI_VictorSPX intakeMotor = new WPI_VictorSPX(Constants.kIntakeCanID);
	static SensoredSystem intakeSystem = new SensoredSystem(intakeMotor, null);

	/**
	 * Climber
	 */
	static WPI_TalonSRX winchA = new WPI_TalonSRX(Constants.kWinchACanId);  // Master
	static WPI_VictorSPX winchB = new WPI_VictorSPX(Constants.kWinchBCanId);
	static WPI_VictorSPX winchC = new WPI_VictorSPX(Constants.kWinchCCanId);
	static  WPI_VictorSPX winchD = new WPI_VictorSPX(Constants.kWinchDCanId);

	static PbDioEncoder winchEncoder = new PbDioEncoder(Constants.kWinchEncoderDioA, Constants.kWinchEncoderDioB);

	// PbSparkMax elevatorMotor = new PbSparkMax(Constants.kElevatorMotorCanId, MotorType.kBrushless);
	// SensoredSystem elevatorSystem = new SensoredSystem(elevatorMotor);

	static SensoredSystem winchSystem = new SensoredSystem(winchA, winchEncoder);

	static PbDioSwitch elevatorUpperLimit = new PbDioSwitch(Constants.kElevatorUpperLimitDio);
	static PbDioSwitch elevatorLowerLimit = new PbDioSwitch(Constants.kElevatorLowerLimitDio);
	static LimitSwitchGroup elevatorLimitSwitchGroup = new LimitSwitchGroup(elevatorUpperLimit, elevatorLowerLimit);

	static Solenoid buddySolenoid = new Solenoid(Constants.kPcmCanId, Constants.kBuddySolenoidPcmPort);

	/**
	 * @return the leftDrive
	 */
	public static SpeedControllerGroup getLeftDrive() {

		return leftDriveMotors;
	}

	/**
	 * @return the Right Drive
	 */
	public static SpeedControllerGroup getRightDrive() {

		return rightDriveMotors;
	}

	public static SpeedControllerGroup getShooter() {
		// shooterMotorA.setInverted(false);
		// shooterMotorB.setInverted(true);
		return shooterMotors;
	}

	public static CANEncoder getShooterVelocityEncoder() {
		return shooterVelocityEncoder;
	}

	public static CANPIDController getShooterAPIDController() {
		return shooterMotorA.getPIDController();
	}

	public static CANPIDController getShooterBPIDController() {
		return shooterMotorB.getPIDController();
	}

	public static Solenoid getShooterHoodSolenoid() {
		return shooterSolenoid;
	}

	public static SensoredSystem getIndexer() {
		return indexerSystem;
	}

	// public static LimitSwitchGroup getRobotWranglerLimits() {
	// PbDioSwitch robotWranglerForwardLimit = new
	// PbDioSwitch(Constants.kRobotWranglerForwardLimitDio);
	// PbDioSwitch robotWranglerReverseLimit = new
	// PbDioSwitch(Constants.kRobotWranglerReverseLimitDio);
	// LimitSwitchGroup limitSwitchGroup = new
	// LimitSwitchGroup(robotWranglerForwardLimit, robotWranglerReverseLimit);
	// return limitSwitchGroup;
	// }

	public static WPI_TalonSRX getIntakeActuationSystem() {
		intakeActuation.configMotionCruiseVelocity(Constants.kIntakeActuatorMotionCruiseVel); //3600
		intakeActuation.configMotionAcceleration(Constants.kIntakeActuatorMotionAccel); //25000
		intakeActuation.config_kF(0, Constants.kIntakeActuatorKp);
		
		return intakeActuation;
	}

	public static SensoredSystem getIntakeSystem() {
		return intakeSystem;
	}

	// Set Idle mode

	public static VictorSPX getIndexerSystem() {
		return indexerMotor;
	}

	// public static SensoredSystem getElevatorSystem() {
	// return elevatorSystem;
	// }

	public static SensoredSystem getWinchSystem() {
		winchA.configFactoryDefault();
		SupplyCurrentLimitConfiguration iConfig = new SupplyCurrentLimitConfiguration(true, Constants.kWinchCurrentLimit, 0, 0);
		winchA.configSupplyCurrentLimit(iConfig);
		winchA.configPeakCurrentLimit(0);
		winchA.enableCurrentLimit(true);
		// winchA.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.QuadEncoder, 0, 0);
		winchA.configOpenloopRamp(0.25);
		winchA.configClosedloopRamp(0.25);

		winchB.configFactoryDefault();
		winchB.follow(winchA);
		winchB.setInverted(InvertType.FollowMaster);
		winchC.configFactoryDefault();
		winchC.follow(winchA);
		winchC.setInverted(InvertType.OpposeMaster);
		winchD.configFactoryDefault();
		winchD.follow(winchA);
		winchD.setInverted(InvertType.OpposeMaster);

		return winchSystem;
	}

	public static LimitSwitchGroup getClimberLimits() {
		return elevatorLimitSwitchGroup;
    }

    public static Solenoid getClimberSolenoid() {
        return buddySolenoid;
    }
}
