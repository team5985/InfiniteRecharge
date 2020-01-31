/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Constants {
	// CAN IDs
	public static final int kPcmCanId = 0;
	public static final int kRobotWranglerSparkCanId = 148;

	public static final int kElevatorMotorCanId = 0;  //FIXME
	public static final int kWinchACanId = 0;  //FIXME
	public static final int kWinchBCanId = 0;  //FIXME
	public static final int kWinchCCanId = 0;  //FIXME
	public static final int kWinchDCanId = 0;  //FIXME

	// Robot Wrangler
	public static final boolean kUseRobotWranglerNeoEncoder = true;
	public static final int kRobotWranglerReverseLimitDio = 1114;
	public static final int kRobotWranglerForwardLimitDio = 2056;

	// Climber
	// Elevator
	public static final int kElevatorEncoderCountsPerRev = 42;
	public static final double kElevatorCountsPerMetre = (kElevatorEncoderCountsPerRev*25) / (2*0.0254*Math.PI);  //FIXME
	public static final double kElevatorKp = 1.0;  //FIXME
	public static final double kElevatorMaxHeight = 1.3; //FIXME
	public static final double kElevatorDownwardsGain = 0.2;  //FIXME

	// Winch
	public static final int kWinchEncoderCountsPerRev = 2048;
	public static final double kWinchCountsPerMetre = (kWinchEncoderCountsPerRev*99.9) / (2*0.0254*Math.PI);  //FIXME
	public static final double kWinchMaxVelocity = 0;  //FIXME
	public static final double kWinchMaxAccel = 0;  //FIXME
	public static final double kWinchKp = 0;  //FIXME

	public static final double kWinchLoClimbHeight = 0;  //FIXME
	public static final double kWinchHiClimbHeight = 0;  //FIXME

	public static final int kWinchCurrentLimit = 0;  //FIXME

	// Sensors
	public static final int kElevatorLowerLimitDio = 0;
	public static final int kElevatorUpperLimitDio = 0;

	public static final int kBuddySolenoidPcmPort = 0;
}
