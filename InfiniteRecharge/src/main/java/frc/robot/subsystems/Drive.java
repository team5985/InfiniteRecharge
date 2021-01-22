/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.config.Config;
import frc.util.PbSparkMax;
import frc.util.SensoredSystem;
import frc.util.SparkGroup;
import frc.robot.Constants;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Drive extends Subsystem{

    SparkGroup mLeftDrive;
    SparkGroup mRightDrive;
    CANEncoder mLeftEnc;
    CANEncoder mRightEnc;

    public static AHRS _imu;

    Joystick stick;
    XboxController xbox = new XboxController(Config.kXboxPort);

    ProfiledPIDController profiledDriveController = new ProfiledPIDController(
        Constants.kEncoderDriveKp, 0.0, 0.0,
        new TrapezoidProfile.Constraints(Constants.kDriveMaxSpeed, Constants.kDriveMaxAccel));
    
    ProfiledPIDController profiledTurnController = new ProfiledPIDController(
        Constants.kGyroTurnKp, 0.0, 0.0, 
        new TrapezoidProfile.Constraints(Constants.kDriveMaxTurnSpeed, Constants.kDriveMaxTurnAccel));

    public static Drive driveInstance;
    
    public static Drive getInstance() {
        if (driveInstance == null) {
            driveInstance = new Drive();
        }
        return driveInstance;
    }
    
    private Drive() {
        _imu = getImuInstance();
        
    }

    public void setSystem(SparkGroup leftDrive, SparkGroup rightDrive, CANEncoder leftEncoder, CANEncoder rightEncoder) {
        mLeftDrive = leftDrive;
        mRightDrive = rightDrive;
        mLeftEnc = leftEncoder;
        mRightEnc = rightEncoder;
    }
  
    public double getPosition() {
        return getAvgEncoderDistance();
    }

    public boolean zeroPosition() {
        _imu.zeroYaw();
        return false;
    }


    //Drive control

    public void setMotors(double leftPower, double rightPower) {
        //set motors
        mLeftDrive.set(leftPower * Config.kInvertDir);

        mRightDrive.set(rightPower * Config.kInvertDir);
      
    }

    public void arcadeDrive(double throttle, double steering, double power) {
        //Left
        double leftPower = (power + steering) * throttle;
        //Right
        double rightPower = (power - steering) * throttle;
        //Write to motors
        setMotors(leftPower, -rightPower);
        
    }

   /**
    * Drives the robot, calculating other settings
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
    * @param maxRate not used
    * @return true when done
    */
    public boolean actionGyroTurn(double targetHeading, int maxRate) {
		double currentRate = _imu.getRate();
		double currentHeading = _imu.getYaw();
        double steering = (targetHeading - currentHeading) * Constants.kGyroTurnKp;
        // profiledTurnController.setConstraints(new TrapezoidProfile.Constraints(maxRate, Constants.kDriveMaxTurnAccel));
        // double steering = profiledTurnController.calculate(currentHeading, targetHeading);
		arcadeDrive(1.0, steering, 0.0);

		return (Math.abs(targetHeading - currentHeading) <= Config.kDriveGyroTurnThresh) && (Math.abs(currentRate) <= Config.kDriveGyroRateThresh);

    }

    /**
	 * Drive at a given distance and gyro heading.
	 * @param speed Maximum speed in m/s
	 * @param targetHeading in degrees.
	 * @param distance in metres.
	 * @return True when driven to given distance, within a threshold. @see getEncoderWithinDistance()
	 */
	public boolean actionSensorDrive(double maxPower, double targetHeading, double distance) {
		double steering = (targetHeading - _imu.getYaw()) * Constants.kGyroDriveTurnKp;
        double power = profiledDriveController.calculate(getAvgEncoderDistance(), distance);
		if (power >= 0 && power > maxPower) {
			power = maxPower;
		} else if (power < 0 && power < -maxPower) {
			power = -maxPower;
		}
		
		arcadeDrive(1.0, steering, power);
		return encoderIsWithinDistance(distance, 0.01);
    }
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
	public boolean encoderIsWithinDistance(double distance, double threshRange) {
		return Math.abs(distance - getAvgEncoderDistance()) < threshRange;
	}

	/**
	 * Returns the average of the two drive encoders.
	 * @return average distance since reset in metres.
	 */
	public double getAvgEncoderDistance() {
		return Constants.kDriveEncoderConversionFactor * (mLeftEnc.getPosition() + -mRightEnc.getPosition()) / 2;
    }

    /**
     * Returns gyro in degrees
     * @return gyro angle in degrees
     */
    public double getYaw() {
        return _imu.getYaw();
    }

    public void update() {

    }
    
    public void resetSensors() {
        mLeftEnc.setPosition(0.0);
        mRightEnc.setPosition(0.0);
        _imu.reset();
    }

    /**
     * Set the motor controllers' brakes on or off.
     * @param brake True to enable brake mode, false to set to coast.
     */
	public void setBrakes(boolean brake) {
        if (brake) {
            mLeftDrive.setIdleMode(IdleMode.kBrake);
            mRightDrive.setIdleMode(IdleMode.kBrake);
        } else {
            mLeftDrive.setIdleMode(IdleMode.kCoast);
            mRightDrive.setIdleMode(IdleMode.kCoast);
        }
        
	}

    /**
     * 
     * @return True if motor controllers are both set to brake.
     */
	public boolean getBrakes() {
		return mLeftDrive.getIdleMode() == IdleMode.kBrake && mRightDrive.getIdleMode() == IdleMode.kBrake;
	}
}