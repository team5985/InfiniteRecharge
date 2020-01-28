/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.config.Config;
import frc.robot.util.EncoderAdapter;
import frc.robot.util.PbEncoder;
import frc.robot.util.SensoredSystem;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.util.LimitSwitchGroup;
import frc.util.PbDioSwitch;
import frc.util.PbSparkMax;

/**
 * Contains and constructs all of the devices on the robot.
 * This should be kept separate from Robot and the Subsystems so that:
 * - none of their code must change if there is a change in devices used
 * - we can run tests on each subsystem's logic using simulated devices
 */
public class RobotMap {
	/**
	 * Robot Configuration
	 */
	// static final boolean useNeoEncoders = true;

	/**
	 * CAN IDs
	 */
	public static final int kLeftACanID = 1;
	public static final int kLeftBCanID = 2;
	public static final int kLeftCCanID = 3;

	public static final int kRightACanID = 4;
	public static final int kRightBCanID = 5;
	public static final int kRightCCanID = 6;

	/**
	 * DIO Ports
	 */
	static final int leftDriveEncADioPort = 0;
	static final int leftDriveEncBDioPort = 1;

	static final int rightDriveEncADioPort = 2;
	static final int rightDriveEncBDioPort = 3;

	/**
	 * Drivetrain
	 */
	// Left
	static CANSparkMax leftDriveA = new CANSparkMax(kLeftACanID, MotorType.kBrushless);
	static CANSparkMax leftDriveB = new CANSparkMax(kLeftBCanID, MotorType.kBrushless);
	//static CANSparkMax leftDriveC = new CANSparkMax(kLeftCCanID, MotorType.kBrushless);
	static SpeedControllerGroup leftDriveMotors = new SpeedControllerGroup(leftDriveA, leftDriveB);

	// Right
	static CANSparkMax rightDriveA = new CANSparkMax(kRightACanID, MotorType.kBrushless);
	static CANSparkMax rightDriveB = new CANSparkMax(kRightBCanID, MotorType.kBrushless);
	//static CANSparkMax rightDriveC = new CANSparkMax(kRightCCanID, MotorType.kBrushless);
	static SpeedControllerGroup rightDriveMotors = new SpeedControllerGroup(rightDriveA, rightDriveB);

	// Encodersa
	static EncoderAdapter leftEncoder;
	static EncoderAdapter rightEncoder;

	public static SensoredSystem leftDrive;
	public static SensoredSystem rightDrive;

	/**
	 * @return the leftDrive
	 */
	public static SensoredSystem getLeftDrive() {
		if (Config.useNeoEncoders) {
			leftEncoder = new EncoderAdapter(leftDriveA.getEncoder());
		} else {
			leftEncoder = new EncoderAdapter( new Encoder(leftDriveEncADioPort, leftDriveEncBDioPort) );
		}
			
		leftDrive = new SensoredSystem(leftDriveMotors, leftEncoder);
		return leftDrive;
	} 
	
	/**
	 * @return the Right Drive
	 */
	public static SensoredSystem getRightDrive() {
		if (Config.useNeoEncoders) {
			rightEncoder = new EncoderAdapter(rightDriveA.getEncoder());
		} else {
			rightEncoder = new EncoderAdapter( new Encoder(leftDriveEncADioPort, leftDriveEncBDioPort) );
		}
			
		rightDrive = new SensoredSystem(rightDriveMotors, rightEncoder);
		return rightDrive;
	
		 // Initialise motor controllers
		 leftDriveA = new CANSparkMax(Constants.kLeftDriveACanId, MotorType.kBrushless);
		 leftDriveB = new CANSparkMax(kLeftDriveBCanId, MotorType.kBrushless);
		 //leftDriveC = new CANSparkMax(kLeftDriveCCanId, MotorType.kBrushless);
		 
		 rightDriveA = new CANSparkMax(kRightDriveACanId, MotorType.kBrushless);
		 rightDriveB = new CANSparkMax(kRightDriveBCanId, MotorType.kBrushless);
		 //rightDriveC = new CANSparkMax(kRightDriveCCanId, MotorType.kBrushless);
		 
		 // Set brake/coast
		 leftDriveA.setIdleMode(Config.kDriveIdleMode);
		 leftDriveB.setIdleMode(Config.kDriveIdleMode);
		 //leftDriveC.setIdleMode(Config.kDriveIdleMode);
		 
		 rightDriveA.setIdleMode(Config.kDriveIdleMode);
		 rightDriveB.setIdleMode(Config.kDriveIdleMode);
		 //rightDriveC.setIdleMode(Config.kDriveIdleMode);
		 
		 // Invert right side
		 leftDriveA.setInverted(Config.kLeftDrivePhase);
		 leftDriveB.setInverted(Config.kLeftDrivePhase);
		 //leftDriveC.setInverted(Config.kLeftDrivePhase);

		 rightDriveA.setInverted(Config.kRightDrivePhase);
		 rightDriveB.setInverted(Config.kRightDrivePhase);
		 //rightDriveB.setInverted(Config.kRightDrivePhase);
		 
 
		 // Set current limit to PDP fuses
		 leftDriveA.setSmartCurrentLimit(Config.kDriveCurrentLimit);
		 leftDriveB.setSmartCurrentLimit(Config.kDriveCurrentLimit);
		 //leftDriveC.setSmartCurrentLimit(Config.kDriveCurrentLimit);

		 rightDriveA.setSmartCurrentLimit(Config.kDriveCurrentLimit);
		 rightDriveB.setSmartCurrentLimit(Config.kDriveCurrentLimit);
		 //rightDriveC.setSmartCurrentLimit(Config.kDriveCurrentLimit);
	}



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

    public static LimitSwitchGroup getRobotWranglerLimits() {
        PbDioSwitch robotWranglerForwardLimit = new PbDioSwitch(Constants.kRobotWranglerForwardLimitDio);
        PbDioSwitch robotWranglerReverseLimit = new PbDioSwitch(Constants.kRobotWranglerReverseLimitDio);
        LimitSwitchGroup limitSwitchGroup = new LimitSwitchGroup(robotWranglerForwardLimit, robotWranglerReverseLimit);
        return limitSwitchGroup;
    }
}
