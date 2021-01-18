/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.config;

import com.revrobotics.CANSparkMax.IdleMode;


/**
 * This is the class for quickly changing values.
 */
public class Config {

    //Safty feature prevents accidental climb

    public static final boolean debugMode = true;   

    public static final boolean kPreventAccidentalClimb = true;

    //1 = normal, -1 = inverted
    public static final double kInvertDir = 1;

    //Motor phases - false = not inverted
    public static final boolean kLeftDrivePhase = false;
    public static final boolean kRightDrivePhase = false;

        //Minimum speed before shooting balls (RPM)
        public static final double kShooterMinSpeed = 100;

        //Shooter idle mode
        public static final boolean kShooterIdleMode = false;

    /*
     * Motor currents:
     * 
     * (In amps)
     * 
     * Drive defult: 40A
     * Shooter defult: 40A
    */

    public static final int kDriveCurrentLimit = 40;

    /* 
     *  Motor modes
     * 
     * Break/Coast
    */

    public static final IdleMode kDriveIdleMode = IdleMode.kCoast;

    //Tip threshold , -180 to 180
    public static final double kTipThreshold = 25;
    //-1 to 1
    public static final double kTipCorrectionPower = 0.4;
    
    //Stall threshold, -1, to 1, 0 if to disabled
    public static final double kStallThreshold = 0;
    //-1 to 1
    public static final double kStallCorrectionPower =  0.9;

    //SQRT for gyro
    public static final double kDriveGyroTurnK = 0.06;
    //Degrees
    public static final double kDriveGyroTurnThresh = 3.0;
    //Degrees/second
    public static final double kDriveGyroRateThresh = 3.0;

    //Xbox
    public static final int kXboxPort = 1;

    //Use neo encoders
    public static final boolean useNeoEncoders = true;

    //Use stallSense with normal teleop drive
    public static final boolean kUseStallSenseTeleopDrive = false;

    //Shooter solenoid - true for a double solenoid
    public static final boolean kShooterDoubleSolenoid = false;
        
    

}
