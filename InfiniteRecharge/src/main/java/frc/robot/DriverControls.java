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
		 SmartDashboard.setDefaultBoolean("Buddy Climb", getBoolBuddyState());
		 updateBuddyState();
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

	//Button presses

	//Intake/Shooter mode

	/**
	 * 
	 * @return true for intake, false for shooter
	 */
	public boolean getMechanismMode() {
	if(stick.getRawButton(3)) {
		mode = false;
	} else if(stick.getRawButton(4)) {
		mode = true;
	}
	return mode;
}

	public boolean getActionCommand() {
		if(stick.getTrigger()) {
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

	public boolean getTrenchRunCommand() {
		if(stick.getRawButton(5)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean getAutoclimb() {
		if(/*Config.kPreventAccidentalClimb*/false) {
			if(stick.getRawButton(7) && stick.getRawButton(8) == true) {
				return true;
			} else {
				return false;
			}
		} else {
			if(stick.getRawButton(8)) {
				return true;
			}else {
				return false;
			}
		}
	}

	public boolean getGetPositionControlCommand() {
		if(stick.getRawButton(12)) {
			return true;
		}else {
			return false;
		}
	}

	public boolean getRotationControlCommand() {
		if(stick.getRawButton(11)) {
			return true;
		} else {
			return false;
		}
	}
	public double getShooterSpeedOffset() {
		if(xBox.getBumper(Hand.kLeft)) {
			shooterSpeedOffset = shooterSpeedOffset - Constants.kShooterSpeedOffsetOffset;
		}else if(xBox.getBumper(Hand.kRight)) {
			shooterSpeedOffset = shooterSpeedOffset + Constants.kShooterSpeedOffsetOffset;
		}
		return shooterSpeedOffset;
		
	}

	public boolean getHoodUpCommand() {
		if(xBox.getYButton()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getHoodDownCommand() {
		if(xBox.getAButton()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getIntakeOut() {
		if(xBox.getXButton()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getIntakeIn() {
		if(xBox.getBButton()) {
			return true;
		} else {
			return false;
		}
	}

	//AntiJam 2000
	public boolean getAntiJam() {
		if(stick.getRawButton(6)) {
			return true;
		} else {
			return false;
		}
	}
	

	private boolean getBoolBuddyState() {
		if(true == true) { //TODO
			return true;
		} else {
			return false;
		}
	}

	private void updateBuddyState() {
		if(SmartDashboard.getBoolean("Buddy Climb", false)) {
			
		} else {
			
		}
	}

	public boolean getTeleopCommand() {
		return stick.getRawButton(7);
	}

	public boolean getClimbUp() {
		return stick.getRawButton(10);
	}

	public boolean getClimbDown() {
		return stick.getRawButton(11);
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

	public boolean getBarLeft() {
		return stick.getPOV() == 270;
	}
	public boolean getBarRight() {
		return stick.getPOV() == 90;
	}

}
