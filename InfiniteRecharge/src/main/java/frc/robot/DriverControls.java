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
import frc.robot.subsystems.Climber.BuddyState;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class DriverControls {

    XboxController driverController;
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
         driverController = new XboxController(Constants.kDriverControllerPort);
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

		if(driverController.getX() >= 0.7) {
			end = true;
		} else if(driverController.getX() <= -0.7) {
			end = true;
		} else if(driverController.getY() >= 0.7) {
			end = true;
		} else if(driverController.getY() <= -0.7) {
			end = true;
		} /* else if(driverController.getZ() >= 0.7) {
			end = true;
		} else if(driverController.getZ() <= -0.7) {
			end = true;
		} */
		return end;
		
	}

	/**
	 * Get driver power command.
	 * @return Y from -1 to 1.
	 */
	public double getDrivePower() {
		double power = -driverController.getY(Hand.kLeft);
		Constants.kDriveSquaredPowerInputsExponent = SmartDashboard.getNumber("Power Gain", 2.0);
		return Math.pow(Math.abs(power), Constants.kDriveSquaredPowerInputsExponent) * Math.signum(power);
	}

	/**
	 * Get driver steering command.
	 * @return X from -1 to 1.
	 */
	public double getDriveSteering() {
		double steering = driverController.getX(Hand.kRight);
		Constants.kDriveSquaredSteeringInputsExponent = SmartDashboard.getNumber("Steering Gain", 2.0);
		return Math.pow(Math.abs(steering), Constants.kDriveSquaredSteeringInputsExponent) * Math.signum(steering);
	}

	/**
	 * Get driver throttle command.
	 * @return Throttle from 0 to 1.
	 */
	public double getDriveThrottle() {
		// return ((-driverController.getThrottle() + 1) / 2);
		return 1.0;
	}

	//Button presses

	//Intake/Shooter mode

	/**
	 * 
	 * @return true for intake, false for shooter
	 */
	public boolean getMechanismMode() {
	if(driverController.getTriggerAxis(Hand.kRight) >= 0.5) {
		mode = false;
	} else if(driverController.getTriggerAxis(Hand.kLeft) >= 0.5) {
		mode = true;
	}
	return mode;
}

	public boolean getActionCommand() {
		if(driverController.getTriggerAxis(Hand.kLeft) > 0.5 || driverController.getTriggerAxis(Hand.kRight) > 0.5) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getVisionCommand() {
		if(driverController.getRawButton(8)) { // Click right stick
			return true;
		} else {
			return false;
		}
	}

	public boolean getTrenchRunCommand() {
		if(driverController.getRawButton(5)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getAutoclimb() {
		if(Config.kPreventAccidentalClimb) {
			if(driverController.getRawButton(12) && driverController.getRawButton(3) == true) {  // 12 - D-Pad UP, 3 - X button
				return true;
			} else {
				return false;
			}
		} else {
			if(driverController.getRawButton(12)) {
				return true;
			}else {
				return false;
			}
		}
	}

	public boolean getSpinnyUp() {
		if(driverController.getYButton()) {  // 4 - Y Button (the top one)
			return true;
		}else {
			return false;
		}
	}

	public boolean getControlPanelCommand() {
		if(driverController.getRawButton(4)) {  // FIXME
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
		if(driverController.getRawButton(6)) {
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
		return driverController.getRawButton(9);
	}
}
