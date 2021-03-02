/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.util.EncoderAdapter;
import frc.util.SensoredSystem;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

import com.ctre.phoenix.motorcontrol.can.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.subsystems.RobotWrangler;

import frc.util.*;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

import frc.util.*;
import frc.util.sim.*;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.util.LimitSwitchGroup;
import frc.util.PbDioSwitch;
import frc.util.PbSolenoid;
import frc.util.PbSparkMax;
import frc.util.PbTalonSrx;
import frc.util.SensoredSystem;
import frc.util.SolenoidAdapter;

/**
 * Contains and constructs all of the devices on the robot. This should be kept
 * separate from Robot and the Subsystems so that: - none of their code must
 * change if there is a change in devices used - we can run tests on each
 * subsystem's logic using simulated devices
 */

public class RobotMap {

	public static final int kIntakeCanID = 9;
	public static final int kItntakeActuatorCanID = 10;
	public static final int kShooterACanID = 12;
	public static final int kShooterBCanID = 13;
	public static final int kIndexerCanID = 26;

	// Encoders
	// static EncoderAdapter shooterVelocityEncoder;

	// Init controllers
	/*
	 * shooterMotorA.setIdleMode(Config.kShooterIdleMode);
	 * shooterMotorB.setIdleMode(Config.kShooterIdleMode);
	 */

	/*
	 * shooterMotorA = new CANSparkMax(kShooterACanID, MotorType.kBrushless);
	 * shooterMotorB = new CANSparkMax(kShooterBCanID, MotorType.kBrushless);
	 * 
	 * shooterMotorA.setIdleMode(Config.kShooterIdleMode);
	 * shooterMotorB.setIdleMode(Config.kShooterIdleMode);
	 * 
	 * leftDriveA.setIdleMode(Config.kDriveIdleMode);
	 */
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

	// Set up motor controllers - Declaration

	// Shooter
	public static WPI_TalonFX shooterMotorA = new WPI_TalonFX(kShooterACanID);
	public static WPI_TalonFX shooterMotorB = new WPI_TalonFX(kShooterBCanID);
	static SpeedControllerGroup shooterMotors = new SpeedControllerGroup(shooterMotorA, shooterMotorB);

	/**
	 * Drivetrain
	 */
	// Left
	static WPI_TalonFX leftDriveA = new WPI_TalonFX(Constants.kLeftDriveACanID);
	static WPI_TalonFX leftDriveB = new WPI_TalonFX(Constants.kLeftDriveBCanID);
	// leftDriveB.follow(leftDriveA);
	// static CANSparkMax leftDriveC = new CANSparkMax(kLeftCCanID,
	// MotorType.kBrushless);
	public static SpeedControllerGroup leftDriveMotors = new SpeedControllerGroup(leftDriveA, leftDriveB);

	// Right
	static WPI_TalonFX rightDriveA = new WPI_TalonFX(Constants.kRightDriveACanID);
	static WPI_TalonFX rightDriveB = new WPI_TalonFX(Constants.kRightDriveBCanID);
	// rightDriveB.follow(rightDriveA);
	// static CANSparkMax rightDriveC = new CANSparkMax(kRightCCanID,
	// MotorType.kBrushless);
	public static SpeedControllerGroup rightDriveMotors = new SpeedControllerGroup(rightDriveA, rightDriveB);

	/**
	 * Indexer
	 */
	static WPI_TalonFX indexerMotor = new WPI_TalonFX(Constants.kIndexerCanID);
	static Solenoid indexerFlap = new Solenoid(Constants.kPcmCanID, Constants.kIndexerFlapChannelId);
	static SensoredSystem indexerSystem = new SensoredSystem(indexerMotor, null);

	/**
	 * Intake
	 */

	public static WPI_VictorSPX intakeMotor = new WPI_VictorSPX(Constants.kIntakeCanID);
	static SensoredSystem intakeSystem = new SensoredSystem(intakeMotor, null);
	public static Solenoid intakeActuator = new Solenoid(Constants.kPcmCanId, Constants.kIntakeFlapSolenoidChannel);

	/**
	 * Climber
	 */
	static WPI_TalonFX winchA = new WPI_TalonFX(Constants.kWinchACanId); // Master
	static WPI_TalonFX winchB = new WPI_TalonFX(Constants.kWinchBCanId);


	// PbSparkMax elevatorMotor = new PbSparkMax(Constants.kElevatorMotorCanId,
	// MotorType.kBrushless);
	// SensoredSystem elevatorSystem = new SensoredSystem(elevatorMotor);


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

	public static WPI_TalonFX getLeftDriveA() {
		return leftDriveA;
	}

	public static WPI_TalonFX getRightDriveA() {
		return rightDriveA;
	}

	public static WPI_TalonFX getLeftDriveB() {
		return leftDriveB;
	}

	public static WPI_TalonFX getRightDriveB() {
		return rightDriveB;
	}

	static PbTalonSrx controlPanelMotor = new PbTalonSrx(Constants.kControlPanelMotor);
	static SensoredSystem controlPanelSystem = new SensoredSystem(controlPanelMotor);

	static Solenoid controlPanelSolenoid = new Solenoid(Constants.kPcmCanID, Constants.kControlPanelSolenoidAChannel);

	static VictorSPX barMotor = new VictorSPX(Constants.kBarMotorCanId);

	public static VictorSPX getBarMotor() {
		return barMotor;
	}

	/**
	 * @return the Right Drive
	 */
	public static SpeedControllerGroup getRightDrive() {

		return rightDriveMotors;
	} /*
		 * // Initialise motor controllers leftDriveA = new
		 * CANSparkMax(Constants.kLeftDriveACanID, MotorType.kBrushless); leftDriveB =
		 * new CANSparkMax(Constants.kLeftDriveBCanID, MotorType.kBrushless);
		 * //leftDriveC = new CANSparkMax(kLeftDriveCCanId, MotorType.kBrushless);
		 * 
		 * rightDriveA = new CANSparkMax(Constants.kRightDriveACanID,
		 * MotorType.kBrushless); rightDriveB = new
		 * CANSparkMax(Constants.kRightDriveBCanID, MotorType.kBrushless); //rightDriveC
		 * = new CANSparkMax(kRightDriveCCanId, MotorType.kBrushless);
		 * 
		 * // Set brake/coast leftDriveA.setIdleMode(Config.kDriveIdleMode);
		 * leftDriveB.setIdleMode(Config.kDriveIdleMode);
		 * //leftDriveC.setIdleMode(Config.kDriveIdleMode);
		 * 
		 * rightDriveA.setIdleMode(Config.kDriveIdleMode);
		 * rightDriveB.setIdleMode(Config.kDriveIdleMode);
		 * //rightDriveC.setIdleMode(Config.kDriveIdleMode);
		 * 
		 * // Invert right side leftDriveA.setInverted(Config.kLeftDrivePhase);
		 * leftDriveB.setInverted(Config.kLeftDrivePhase);
		 * //leftDriveC.setInverted(Config.kLeftDrivePhase);
		 * 
		 * rightDriveA.setInverted(Config.kRightDrivePhase);
		 * rightDriveB.setInverted(Config.kRightDrivePhase);
		 * //rightDriveB.setInverted(Config.kRightDrivePhase);
		 * 
		 * 
		 * // Set current limit to PDP fuses
		 * leftDriveA.setSmartCurrentLimit(Config.kDriveCurrentLimit);
		 * leftDriveB.setSmartCurrentLimit(Config.kDriveCurrentLimit);
		 * //leftDriveC.setSmartCurrentLimit(Config.kDriveCurrentLimit);
		 * 
		 * rightDriveA.setSmartCurrentLimit(Config.kDriveCurrentLimit);
		 * rightDriveB.setSmartCurrentLimit(Config.kDriveCurrentLimit);
		 * //rightDriveC.setSmartCurrentLimit(Config.kDriveCurrentLimit);
		 */

	public static SensoredSystem getControlPanelSystem() {

		return controlPanelSystem;
	}



	public static SpeedControllerGroup getShooter() {
		return shooterMotors;
	}


	public static WPI_TalonFX getIndexer() {
		return indexerMotor;
	}

	public static Solenoid getIndexerSolenoid() {
		return indexerFlap;
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

	public static LimitSwitchGroup getRobotWranglerLimits() {
		PbDioSwitch robotWranglerForwardLimit = new PbDioSwitch(Constants.kRobotWranglerForwardLimitDio);
		PbDioSwitch robotWranglerReverseLimit = new PbDioSwitch(Constants.kRobotWranglerReverseLimitDio);
		LimitSwitchGroup limitSwitchGroup = new LimitSwitchGroup(robotWranglerForwardLimit, robotWranglerReverseLimit);
		return limitSwitchGroup;

	}

	public static Solenoid getControlPanelSolenoid() {
		return controlPanelSolenoid;
	}

	public static SensoredSystem getIntakeSystem() {
		return intakeSystem;

	}

	public static Solenoid getIntakeActuationSystem() {
		return intakeActuator;
	}

	// Set Idle mode

	// Set Idle mode

	public static WPI_TalonFX getIndexerSystem() {
		return indexerMotor;
	}

	// public static SensoredSystem getElevatorSystem() {
	// return elevatorSystem;
	// }

	public static LimitSwitchGroup getClimberLimits() {
		return elevatorLimitSwitchGroup;
	}

	public static WPI_TalonFX getClimberA() {
		return winchA;
	}

	public static WPI_TalonFX getClimberB() {
		return winchB;
	}

	public static Solenoid getClimberSolenoid() {
		return buddySolenoid;
	}

	public static WPI_TalonFX getShooterA() {
		return shooterMotorA;
	}
	public static WPI_TalonFX getShooterB() {
		return shooterMotorB;
	}
}
