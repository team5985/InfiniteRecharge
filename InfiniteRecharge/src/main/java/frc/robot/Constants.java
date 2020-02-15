/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;

/**
 * Add your docs here.
 */
public class Constants {

	public static final int kRobotWranglerSparkCanId = 148;
	public static final boolean kUseRobotWranglerNeoEncoder = true;
	public static final int kRobotWranglerReverseLimitDio = 1114;
	public static final int kRobotWranglerForwardLimitDio = 2056;

	public static final int kControlPanelMotor = 23;

	public static final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
	public static final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
	public static final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
	public static final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
	
	public static final int kPcmCanID = 50;
	public static final int kCompressorCanID = 51; //FIXME

	public static final int kSensorOffset = 2; //FIXME

	public static final boolean kControlPanelDoubleSolenoid = false;
	public static final int kControlPanelSolenoidAChannel = 0; //FIXME
	public static final int kControlPanelSolenoidBChannel = 1; //FIXME
	//Depreciated if single solenoid

	public static final double kControlPanelManualSpeed = 0.5; //FIXME
	public static final double kControlPanelRotationControlSpeed = 0.5; //FIXME
	public static final double kControlPanelPoisitionControlSpeed = 0.5; //FIXME
	public static final double kControlPanelTargetRotations = 4;
	public static final double kControlPanelHysteresis = 0.5;
}
