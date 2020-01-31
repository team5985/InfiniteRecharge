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

	public static final int kRobotWranglerSparkCanId = 148;
	public static final boolean kUseRobotWranglerNeoEncoder = true;
	public static final int kRobotWranglerReverseLimitDio = 1114;
	public static final int kRobotWranglerForwardLimitDio = 2056;

	public static final double kIntakeExtensionRevolutions = 4;
	public static final double kIntakeEncoderPPR = 4096; //4096 for ctre mag encoder
	public static final double kIntakeIntakingSpeed = -1;
	public static final double kIntakeUnintakingSpeed = 0.8;
}
