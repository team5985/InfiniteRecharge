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

	
	public static final int kPcmCanID = 50;
	public static final int kCompressorCanID = 51; //FIXME

	public static final int kSensorOffset = 2; //FIXME

	public static final boolean kControlPanelDoubleSolenoid = false;
	public static final int kControlPanelSolenoidAChannel = 0; //FIXME
	public static final int kControlPanelSolenoidBChannel = 1; //FIXME
	public static final double kRotationalControlTargetRotations = 3.75;
	//Depreciated if single solenoid

	public static final double kControlPanelManualSpeed = 0.3; 
	public static final double kMaxRotationalControlSpeed = 0.4;
	public static final double kControlPanelPoisitionControlSpeed = 0.1; 
	public static final double kControlPanelTargetRotations = 4;
	public static final double kControlPanelHysteresis = 0.5;


	/**
     * Colour RED seen at the control panel.
     */
    public static final int kCPANEL_COLOUR_RED = 0;

    /**
     * Colour GREEN seen at the control panel.
     */
    public static final int kCPANEL_COLOUR_GREEN = 1;

    /**
     * Colour BLUE seen at the control panel.
     */
    public static final int kCPANEL_COLOUR_CYAN = 2;

    /**
     * Colour YELLOW seen at the control panel.
     */
    public static final int kCPANEL_COLOUR_YELLOW = 3;

    /**
     * The colour seen at the control panel  did not match any of the expected colours.
     */
    public static final int kCPANEL_COLOUR_INVALID = -1;

    /**
     * The number of colour transitions per full rotation of the colour wheel.
     */
    public static final int kCPANEL_COLOURS_PER_ROTATION = 8;
  

    // Values from test with sensor LED turned on.
    public static final Color kLIT_CYAN = ColorMatch.makeColor(0.14,0.42,0.43);
    public static final Color kLIT_GREEN = ColorMatch.makeColor(0.19,0.53,0.27);
    public static final Color kLIT_RED = ColorMatch.makeColor(0.48,0.36,0.16);
    public static final Color kLIT_YELLOW = ColorMatch.makeColor(0.32,0.53,0.14);

    // Values from test with sensor LED turned off (ambient light).
    public static final Color kAMB_CYAN = ColorMatch.makeColor(0.14,0.39,0.45);
    public static final Color kAMB_GREEN = ColorMatch.makeColor(0.20,0.51,0.28);
    public static final Color kAMB_RED = ColorMatch.makeColor(0.59,0.29,0.12);
    public static final Color kAMB_YELLOW = ColorMatch.makeColor(0.38,0.49,0.13);


    // FMS values for colours
    public static final char kFMS_CYAN = 'B';
    public static final char kFMS_RED = 'R';
    public static final char kFMS_YELLOW = 'Y';
    public static final char kFMS_GREEN = 'G';
	public static Object getInstance() {
		return null;
	}

}
