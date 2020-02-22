/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DigitalSource;

/**
 * Add your docs here.
 */
public class Constants {

	public static final int kXboxPort = 1;
	public static final int kJoystickPort = 0;
	public static final double kShooterSpeedOffsetOffset = 0.1;

	// CAN IDs
	public static final int kPcmCanId = 50;
	public static final int kRobotWranglerSparkCanId = 148;

	// public static final int kElevatorMotorCanId = 0;  //FIXME
	public static final int kWinchACanId = 16;
	public static final int kWinchBCanId = 17;
	public static final int kWinchCCanId = 18;
	public static final int kWinchDCanId = 19;

	// DIO ports
	public static final int kWinchEncoderDioA = 8;
	public static final int kWinchEncoderDioB = 9;

	// Robot Wrangler
	public static final boolean kUseRobotWranglerNeoEncoder = true;

	public static final int kIntakeActuatorCanID = 10;

	public static final int kDriveCurrentLimit = 40;

    public static double kShooterMaxOutput = 1; 
    public static double kShooterMinOutput = -1;
	public static double kShootermaxRPM = 5700;
	public static double kShooterDefaultRPM = 5600;
	public static double kShooterMinRPM = 3000;
	public static double kShooterHysteresis = 10;
	public static double kShooterIdleSpeed = 0;
	public static double kShooterP = 6e-5; 
    public static double kShooterI = 0;
    public static double kShooterD = 0; 
    public static double kShooterIz = 0; 
	public static double kShooterFF = 1/kShootermaxRPM; 
	public static int kShooterHoodSolenoidAChannel = 1;
	public static int kShooterHoodSolenoidBChannel = 2;


	public static final int kRobotWranglerReverseLimitDio = 0; //1114
	public static final int kRobotWranglerForwardLimitDio = 0; //2056
	
  
	public static final double kIntakeExtensionRevolutions = 2.4;
	public static final double kIntakeEncoderPPR = 4096; //4096 for ctre mag encoder
	public static final double kIntakeIntakingSpeed = -1;
	public static final double kIntakeUnintakingSpeed = 0.8;
	public static final int kLeftDriveACanID = 1;
	public static final int kLeftDriveBCanID = 2;
	public static final int kRightDriveACanID = 3;
	public static final int kRightDriveBCanID = 4;


	public static final int kIndexerCanID = 22;
	public static final double kIndexerSpeed = 1; //FIXME
	public static final double kIndexerAntijam = -0.5; //FIXME


	public static double kDriveSquaredSteeringInputsExponent = 2.0;
    public static double kDriveSquaredPowerInputsExponent = 2.0;

	/** Climber **/
	// Elevator
	// public static final int kElevatorEncoderCountsPerRev = 42;
	// public static final double kElevatorKp = 0.0254;  //FIXME
	// public static final double kElevatorDownwardsGain = 0.2;  //FIXME

	public static final double kElevatorTopHeightRotations = 1; //FIXME
	public static final double kElevatorHeightTolerance = 0.125;

	// Winch
	public static final int kWinchEncoderCountsPerRev = 8192; // 8192 for REV encoder
	public static final double kWinchMaxVelocity = 1.84;  // Revolutions per Second
	public static final double kWinchMaxAccel = 7.37;  // rps/s
	public static final double kWinchKp = 1.0;  //FIXME

	public static final double kWinchLoClimbRotations = 0.75;  //FIXME
	public static final double kWinchHiClimbRotations = 0.25;  //FIXME

	public static final double kWinchMaxRotationsLimit = 2; //FIXME
	public static final double kWinchMinRotationsLimit = 0; //FIXME
	
	public static final int kWinchCurrentLimit = 30;

	// Sensors
	public static final int kElevatorLowerLimitDio = 1;
	public static final int kElevatorUpperLimitDio = 2;

	public static final int kBuddySolenoidPcmPort = 0;

	//Intake
	public static final int kIntakeCanID = 9;
	public static final int kItntakeActuatorCanID = 10;
	public static final int kControlPanelSolenoidAChannel = 0;
	public static final int kControlPanelSolenoidBChannel = 0;	
	//Shooter RPM
}
