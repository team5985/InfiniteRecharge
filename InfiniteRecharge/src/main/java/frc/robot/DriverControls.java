/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.config.*;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Climber.BuddyState;
import frc.robot.subsystems.Drive.UltrasonicState;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class DriverControls {

	Joystick stick;
	Joystick pad;
	XboxController xBox;
	
	boolean end = false;
	boolean mode = false;
	double shooterSpeedOffset = 0;
    public static DriverControls mDriverControlsInstance;

    public static DriverControls getDriverControlsInstance() {
        if (mDriverControlsInstance == null) {
            mDriverControlsInstance  = new DriverControls();
        }
        return mDriverControlsInstance;
    }

    

    public DriverControls() {
		 stick = new Joystick(Constants.kJoystickPort);
		 pad = new Joystick(2);

         xBox = new XboxController(Constants.kXboxPort);

         SmartDashboard.setDefaultNumber("Power Gain", 2.0);
		 SmartDashboard.setDefaultNumber("Steering Gain", 2.0);
    }
    /**
     * Returns true if the joystick has been jerked beyond 0.7.
     * @return Driver interrupt command.
     */
    public boolean getStickInterupt() {
		end = false;

		if(stick.getX() >= 0.7) {
			end = true;
		} else if(stick.getX() <= -0.7) {
			end = true;
		} else if(stick.getY() >= 0.7) {
			end = true;
		} else if(stick.getY() <= -0.7) {
			end = true;
		} else if(stick.getZ() >= 0.7) {
			end = true;
		} else if(stick.getZ() <= -0.7) {
			end = true;
		}
		return end;
		
	}

	/**
	 * Get driver power command.
	 * @return Y from -1 to 1.
	 */
	public double getDrivePower() {
		double power = -stick.getY();
		Constants.kDriveSquaredPowerInputsExponent = SmartDashboard.getNumber("Power Gain", 2.0);
		return power;//Math.pow(Math.abs(power), Constants.kDriveSquaredPowerInputsExponent) * Math.signum(power);
	}

	/**
	 * Get driver steering command.
	 * @return X from -1 to 1.
	 */
	public double getDriveSteering() {
		double steering = stick.getX();
		Constants.kDriveSquaredSteeringInputsExponent = SmartDashboard.getNumber("Steering Gain", 2.0);
		return steering;//Math.pow(Math.abs(steering), Constants.kDriveSquaredSteeringInputsExponent) * Math.signum(steering);
	}

	/**
	 * Get driver throttle command.
	 * @return Throttle from 0 to 1.
	 */
	public double getDriveThrottle() {
		return ((-stick.getThrottle() + 1) / 2);
	}

	/**
	 * @return Traverser speed from -1 to 1.
	 *  */ 
	public double getTraverserThrottle() {
		return xBox.getY(Hand.kLeft);
	}

	//Button presses

	//Intake/Shooter mode

	/**
	 * 
	 * @return true for intake, false for shooter
	 */
	public boolean getMechanismMode() {
	if(stick.getTrigger()) {
		mode = false;
	} else if(xBox.getAButton()) {
		mode = true;
	}
	return mode;
}

	public boolean getActionCommand() {
		if(stick.getTrigger() || xBox.getAButton()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getVisionCommand() {
		if(stick.getRawButton(2)) {
			return true;
		} else {
			return false;
		}
	}

	
	
	public boolean getGetPositionControlCommand() {
		if(xBox.getBButton()) {
			return true;
		}else {
			return false;
		}
	}

	public boolean getRotationControlCommand() {
		if(xBox.getXButton()) {
			return true;
		} else {
			return false;
		}
	}
	
	// public double getShooterSpeedOffset() {
	// 	if(xBox.getBumper(Hand.kLeft)) {
	// 		shooterSpeedOffset = shooterSpeedOffset - Constants.kShooterSpeedOffsetOffset;
	// 	}else if(xBox.getBumper(Hand.kRight)) {
	// 		shooterSpeedOffset = shooterSpeedOffset + Constants.kShooterSpeedOffsetOffset;
	// 	}
	// 	return shooterSpeedOffset;
		
	// }


	//AntiJam 2000
	//left
	public boolean getIntakeAntiJam() {
		if(xBox.getPOV() == 270) {
			return true;
		} else {
			return false;
		}
	}
	//down
	public boolean getIndexerAntiJam() {
		if(xBox.getPOV() == 180) {
			return true;
		} else {
			return false;
		}
	}
	//right
	public boolean getShooterAntiJam() {
		if(xBox.getPOV() == 90) {
			return true;
		} else {
			return false;
		}
	}


	public boolean getTeleopCommand() {
		return stick.getRawButton(7);
	}
	/**
	 * 
	 * @return button 11 for confirm climb
	 */
	private boolean getClimbConfirmation() {
		return stick.getRawButton(11);
	}

	public boolean getClimbUp() {
		return (stick.getRawButton(10) && getClimbConfirmation());
	}

	public boolean getClimbDown() {
		return (stick.getRawButton(11) && getClimbConfirmation());
	}

	public void updateShooterIndex() {
		if(pad.getRawButton(7))	{
			Shooter.getInstance().setShooterZoneIndex(0);
		} else if(pad.getRawButton(8))	{
			Shooter.getInstance().setShooterZoneIndex(1);
		} else if(pad.getRawButton(9))	{
			Shooter.getInstance().setShooterZoneIndex(2);
		} else if(pad.getRawButton(10))	{
			Shooter.getInstance().setShooterZoneIndex(3);
		} else if(pad.getRawButton(11))	{
			Shooter.getInstance().setShooterZoneIndex(4);
		} 
	}

	public Drive.UltrasonicState getUltrasonicState()
	{
		if (stick.getPOV() == 45)
		{
			return UltrasonicState.FWD_RIGHT;
		}
		if (stick.getPOV() == 135)
		{
			return UltrasonicState.REV_RIGHT;
		}
		if (stick.getPOV() == 225)
		{
			return UltrasonicState.REV_LEFT;
		}
		if (stick.getPOV() == 315)
		{
			return UltrasonicState.FWD_LEFT;
		}
		return UltrasonicState.IDLE;
	}

}
