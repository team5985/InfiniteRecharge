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
	public static final int kRobotWranglerSparkCanId = 148;

	// Robot Wrangler
	public static final boolean kUseRobotWranglerNeoEncoder = true;
	public static final int kRobotWranglerReverseLimitDio = 1114;
	public static final int kRobotWranglerForwardLimitDio = 2056;

	// Climber
	public static final double kElevatorCountsPerMetre = (42*25) / (2*0.0254*Math.PI);
}
