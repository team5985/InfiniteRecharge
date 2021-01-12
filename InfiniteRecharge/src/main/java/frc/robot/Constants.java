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

	public static final int kXboxPort = 1;
	public static final int kJoystickPort = 0;
	public static final double kShooterSpeedOffsetOffset = 0.1;

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
	public static final int kRobotWranglerReverseLimitDio = 1114;
	public static final int kRobotWranglerForwardLimitDio = 2056;



	public static final int kControlPanelMotor = 23;

	
	public static final int kPcmCanID = 50;
	public static final int kCompressorCanID = 51; //FIXME

	public static final int kSensorOffset = 2; //FIXME

	public static final boolean kControlPanelDoubleSolenoid = false;
	public static final int kControlPanelSolenoidAChannel = 7; //FIXME
	public static final int kControlPanelSolenoidBChannel = 6; //FIXME
	public static final double kRotationalControlTargetRotations = 3.5;
	//Depreciated if single solenoid
    public static final boolean kControlPanelExtendedState = true;
    public static final boolean kControlPanelRetractedState = false;

	public static final double kControlPanelManualSpeed = 0.3; 
	public static final double kRotationalControlSpeed = 0.2;
	public static final double kControlPanelPoisitionControlSpeed = 0.15; 
	public static final double kControlPanelTargetRotations = 4;
	public static final double kControlPanelHysteresis = 0.5;


	/**
     * Colour RED seen at the control panel.
     */
    public static final int kControlPanelColourRed = 0;

    /**
     * Colour GREEN seen at the control panel.
     */
    public static final int kControlPanelColourGreen = 1;

    /**
     * Colour BLUE seen at the control panel.
     */
    public static final int kControlPanelColourBlue = 2;

    /**
     * Colour YELLOW seen at the control panel.
     */
    public static final int kControlPanelColourYellow = 3;

    /**
     * The colour seen at the control panel  did not match any of the expected colours.
     */
    public static final int kControlPanelColourInvalid = -1;

    /**
     * The number of colour transitions per full rotation of the colour wheel.
     */
    public static final int kCPANEL_COLOURS_PER_ROTATION = 8;
  

	// Values from test with sensor LED turned on.
	

	//Official Colours

   /* public static final Color kLIT_CYAN = ColorMatch.makeColor(0.14,0.42,0.43);
    public static final Color kLIT_GREEN = ColorMatch.makeColor(0.19,0.53,0.27);
    public static final Color kLIT_RED = ColorMatch.makeColor(0.48,0.36,0.16);
	public static final Color kLIT_YELLOW = ColorMatch.makeColor(0.32,0.53,0.14); */

	//Our Colours
	public static final Color kLIT_CYAN = ColorMatch.makeColor(0.21,0.48,0.31);
    public static final Color kLIT_GREEN = ColorMatch.makeColor(0.23,0.58,0.19);
    public static final Color kLIT_RED = ColorMatch.makeColor(0.47,0.37,0.16);
	public static final Color kLIT_YELLOW = ColorMatch.makeColor(0.33,0.54,0.13);


	// Values from test with sensor LED turned off (ambient light).
	
	// Values with offical colours
    /*public static final Color kAMB_CYAN = ColorMatch.makeColor(0.14,0.39,0.45);
    public static final Color kAMB_GREEN = ColorMatch.makeColor(0.20,0.51,0.28);
    public static final Color kAMB_RED = ColorMatch.makeColor(0.59,0.29,0.12);
    public static final Color kAMB_YELLOW = ColorMatch.makeColor(0.38,0.49,0.13); */

	//Values with our colours
	public static final Color kAMB_CYAN = ColorMatch.makeColor(0.21,0.48,0.31);
    public static final Color kAMB_GREEN = ColorMatch.makeColor(0.23,0.58,0.19);
    public static final Color kAMB_RED = ColorMatch.makeColor(0.47,0.37,0.16);
	public static final Color kAMB_YELLOW = ColorMatch.makeColor(0.33,0.54,0.13);

	//Name Values for Colours
    public static final String kNAME_CYAN = "Cyan";
    public static final String kNAME_GREEN = "Green";
    public static final String kNAME_RED = "Red";
    public static final String kNAME_YELLOW = "Yellow";
    public static final String kNAME_UNKNOWN = "Unknown";
	


    // FMS values for colours
    public static final char kFMS_CYAN = 'B';
    public static final char kFMS_RED = 'R';
    public static final char kFMS_YELLOW = 'Y';
    public static final char kFMS_GREEN = 'G';
	public static Object getInstance() {
		return null;
	}

  
	public static final double kIntakeExtensionRevolutions = 4;
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

	//Intake
	public static final int kIntakeCanID = 9;
    public static final int kItntakeActuatorCanID = 10;

}
