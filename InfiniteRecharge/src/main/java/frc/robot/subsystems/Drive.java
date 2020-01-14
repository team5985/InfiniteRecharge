/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.SPI;
import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.config.Config;




/**
 * Add your docs here.
 */
public class Drive extends Subsystem{

    private CANSparkMax leftDriveA; //Master
    private CANSparkMax leftDriveB; //Slave
    private CANSparkMax leftDriveC; //Slave

    private CANSparkMax rightDriveA; //Master
    private CANSparkMax rightDriveB; //Slave
    private CANSparkMax rightDriveC; //Slave

    public static Drive driveInstance;
    
    public static AHRS _imu;


    Joystick stick;
    XboxController xbox = new XboxController(Config.kXboxPort);
  
    public double getPosition() {
        return 0.0;
    }

    public boolean zeroPosition() {
        _imu.zeroYaw();
        return false;
    }


    //Drive control

    public void setMotors(double leftPower, double rightPower) {
        //set motors
        leftDriveA.set(leftPower * Config.kInvertDir);
        leftDriveB.set(leftPower * Config.kInvertDir);
        leftDriveC.set(leftPower * Config.kInvertDir);

        rightDriveA.set(rightPower * Config.kInvertDir);
        rightDriveB.set(rightPower * Config.kInvertDir);
        rightDriveC.set(rightPower * Config.kInvertDir);
    }

    public void arcadeDrive(double throttle, double steering, double power) {
        //Left
        double leftPower = (power + steering) * throttle;
        //Right
        double rightPower = (power - steering) * throttle;
        //Write to motors
        setMotors(leftPower, rightPower);
        
    }

   /**
    * Drives the robot, calculating other setings
    *Stall detection disabled currently
    * @param throttle
    * @param steering
    * @param power
    * @param stallSense
    */
    public void smartDrive(double throttle, double steering, double power, boolean stallSense) {
        double pitch = _imu.getPitch();
        double stallThreshold = (Config.kStallThreshold);
        double tipThreshold = (Config.kTipThreshold);
        if(pitch > tipThreshold) {
            setMotors(-Config.kTipCorrectionPower, -Config.kTipCorrectionPower);
        } else if(pitch < -tipThreshold) {
            setMotors(Config.kTipCorrectionPower, Config.kTipCorrectionPower);
        } else {
            //Normal
              arcadeDrive(throttle, steering, power);
        }
    }

   /**
    * @param targetHeading
    * @param maxRate
    * @return true when done
    */
   
    public boolean actionGyroTurn(double targetHeading, int maxRate) {
		double currentRate = _imu.getRate();
		double currentHeading = _imu.getYaw();
		double steering = Math.sqrt((targetHeading - currentHeading)) * Config.kDriveGyroTurnK;
		arcadeDrive(0.0, steering, Math.abs(maxRate));

		return (Math.abs(targetHeading - currentHeading) <= Config.kDriveGyroTurnThresh) && (Math.abs(currentRate) <= Config.kDriveGyroRateThresh);

    }

    /**
	 * Drive at a given distance and gyro heading.
	 * @param speed Maximum speed in m/s
	 * @param targetHeading in degrees.
	 * @param distance in metres.
	 * @return True when driven to given distance, within a threshold. @see getEncoderWithinDistance()
	 */
	/*public boolean actionSensorDrive(double speed, double targetHeading, double distance) {
		double position = getAvgEncoderDistance();
		double drivePower = (distance - position) * Constants.kDriveEncoderDrivePGain;

		double currentHeading = _imu.getYaw();
		double steering = (targetHeading - currentHeading) * Constants.kDriveSensorDriveTurnKp;
		arcadeDrive(drivePower, steering, 1);

		return encoderIsWithinDistance(distance, 0.1);
    } */
    public AHRS getImuInstance() {
		if (_imu == null) {
			_imu = new AHRS(SPI.Port.kMXP); // Must be over SPI so the JeVois can communicate through UART Serial.
		}
		return _imu;
    }
    /**
	 * Returns true if the average of the two encoders are within a certain range.
	 * @param distance is metres
	 * @param threshRange +/- in metres
	 * @return Boolean true when within range.
	 */
	/*public boolean encoderIsWithinDistance(double distance, double threshRange) {
		return Math.abs(distance - getAvgEncoderDistance()) < threshRange;
	} */

	/**
	 * Returns the average of the two drive encoders.
	 * @return average distance since reset in metres.
	 */
/*	public double getAvgEncoderDistance() {
		return (leftDriveEncoder.getDistance() + rightDriveEncoder.getDistance()) / 2;
    } */
    
    // Initialise motor controllers
		leftDriveA = new CANSparkMax(Constants.kLeftDriveACanId, MotorType.kBrushless);
        leftDriveB = new CANSparkMax(Constants.kLeftDriveBCanId, MotorType.kBrushless);
        leftDriveC = new CANSparkMax(Constants.kLeftDriveCCanId, MotorType.kBrushless);
		
		rightDriveA = new CANSparkMax(Constants.kRightDriveACanId, MotorType.kBrushless);
        rightDriveB = new CANSparkMax(Constants.kRightDriveBCanId, MotorType.kBrushless);
        rightDriveC = new CANSparkMax(Constants.kRightDriveCCanId, MotorType.kBrushless);
		
		// Set brake/coast
		leftDriveA.setIdleMode(Constants.kDriveIdleMode);
        leftDriveB.setIdleMode(Constants.kDriveIdleMode);
        leftDriveC.setIdleMode(Constants.kDriveIdleMode);
		
		rightDriveA.setIdleMode(Constants.kDriveIdleMode);
		rightDriveB.setIdleMode(Constants.kDriveIdleMode);
        rightDriveC.setIdleMode(Constants.kDriveIdleMode);
        
		// Invert right side
		leftDriveA.setInverted(Constants.kLeftDriveMotorPhase);
		leftDriveB.setInverted(Constants.kLeftDriveMotorPhase);
		rightDriveA.setInverted(!Constants.kLeftDriveMotorPhase);
		rightDriveB.setInverted(!Constants.kLeftDriveMotorPhase);

		// Set current limit to PDP fuses
		leftDriveA.setSmartCurrentLimit(40.0);
		leftDriveB.setSmartCurrentLimit(40.0);
		rightDriveA.setSmartCurrentLimit(40.0);
        rightDriveB.setSmartCurrentLimit(40.0);
}