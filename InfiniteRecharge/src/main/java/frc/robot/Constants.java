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
	public static final int kShooterACanID = 12;
	public static final int kShooterBCanID = 13;
	public static final int kThroatCanID = 29;
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
	public static final int kIndexerFlapChannelId = 5;

	// Robot Wrangler
	public static final boolean kUseRobotWranglerNeoEncoder = true;

	public static final int kIntakeActuatorCanID = 10;
	public static final int kIntakeFlapSolenoidChannel = 6;

	public static final int kDriveCurrentLimit = 40;

    public static double kShooterMaxOutput = 1; 
    public static double kShooterMinOutput = -1;
	public static double kShootermaxRPM = 5700;
	public static double kShooterDefaultRPM = 6000;
	public static double kShooterMinRPM = 500;
	public static double kShooterHysteresis = 10;
	public static double kShooterIdleSpeed = 750 ;
	public static double kShooterP = 6e-5; 
    public static double kShooterI = 0;
    public static double kShooterD = 0; 
    public static double kShooterIz = 0; 
	public static double kShooterFF = 1/kShootermaxRPM; 
	public static int kShooterHoodSolenoidAChannel = 5;
	//public static int kShooterHoodSolenoidBChannel = 2;


	public static final int kRobotWranglerReverseLimitDio = 0; //1114
	public static final int kRobotWranglerForwardLimitDio = 0; //2056
	
 	public static final int kIntakeActuatorMotionCruiseVel = 3600;
	public static final int kIntakeActuatorMotionAccel = 25000;
	public static final double kIntakeActuatorKf = 0.6; 

	public static final double kIntakeExtensionRevolutions = 2.6;




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
	public static final double kRotationalControlSpeed = 0.3;
	public static final double kControlPanelPoisitionControlSpeed = 0.1;

	public static final double kControlPanelTargetRotations = 4;
	public static final double kControlPanelHysteresis = 0.5;

	public static final int kLED1PwmPort = 0;

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

	public static final Color kColInvalid = ColorMatch.makeColor(0,0,0);


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

	
  

	public static final double kIntakeEncoderPPR = 4096; //4096 for ctre mag encoder
	public static final double kIntakeIntakingSpeed = -1;  // .85
	public static final double kIntakeUnintakingSpeed = 0.8;
	public static final int kLeftDriveACanID = 1;
	public static final int kLeftDriveBCanID = 2;
	public static final int kRightDriveACanID = 3;
	public static final int kRightDriveBCanID = 4;


	public static final int kIndexerCanID = 26;
	public static final double kIndexerSpeed = 0.5; //FIXME
	public static final double kIndexerAntijam = -0.25; //FIXME

	public static final double kThroatSpeed = -1.0;

	public static double  kIntakeServoRetractedPos = 1;
	public static double kIntakeServoExtendedPos = 0;

	public static double kDriveSquaredSteeringInputsExponent = 2.0;
	public static double kDriveSquaredPowerInputsExponent = 2.0;
	
	// Profiled PID Controller values for autonomous movements
	// Driving to a distance
	public static double kDriveMaxSpeed = 4.18;
	public static double kDriveMaxAccel = 4.0;

	// Turning on the spot with the gyro
	public static double kDriveMaxTurnSpeed = 12.12;
	public static double kDriveMaxTurnAccel = 24.24;

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

	public static final int kBuddySolenoidPcmPort = 3;

	//Intake
	public static final int kIntakeCanID = 9;
	public static final int kItntakeActuatorCanID = 10;
	
	//Shooter RPM

	// Vision
	public static final double kVisionTurnKp = 0.023;//was 0.024; // 0.012 for 2 centre nitrile
	public static final double kDriveTurnStictionConstant = 0.0; // FIXME
	public static final double kGyroTurnKp = 0.007; // 0.004 for 2 centre nitrile
	public static final double kGyroDriveTurnKp = 0.012;  // FIXME
	public static final double kEncoderDriveKp = 0.7;  // FIXME
	public static final double kDriveEncoderConversionFactor = 0.000023077; //1; //0.00089291;

	public static final int kBarMotorCanId = 20;
	public static final double kBarLeftSpeed = -1;
	public static final double kBarRightSpeed = 1;

	public static final double kClimbLowPos = 1000; 
	public static final double kClimbHighPos = 100000;
	public static final double kElevatorHighPos = 265000;

	public static final double kElevatorClimbSpeed = 0.5;

	//Vision
	public static final double kA1 = 20; // Angle of the limelight from the robot chassie (degrees)
	public static final double kH1 = 0.65; //Height of the limelight lense (metres)
	public static final double kH2 = 2.47; //height of the middle of the target (metres)
}
