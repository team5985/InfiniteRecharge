/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.config;

/**
 * This is the class for quickly changing values.
 */
public class Config {

    //1 = normal, -1 = inverted
    public static final double kInvertDir = 1;

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
        
    

}