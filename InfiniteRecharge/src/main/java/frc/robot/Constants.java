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
	public static final double kIndexerSpeed = 1;

	public static final int kIntakeCanID = 9;
	public static final int kIntakeActuatorCanID = 10;

	public static final int kDriveCurrentLimit = 40;

	public static double kShooterP = 6e-5; 
    public static double kShooterI = 0;
    public static double kShooterD = 0; 
    public static double kShooterIz = 0; 
	public static double kShooterFF = 0.000015; 
    public static double kShooterMaxOutput = 1; 
    public static double kShooterMinOutput = -1;
    public static double kShootermaxRPM = 5700;
}
