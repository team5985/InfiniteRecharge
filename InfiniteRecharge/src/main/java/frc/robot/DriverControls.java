/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class DriverControls {

    Joystick stick;
    XboxController xbox;
    public static DriverControls mDriverControlsInstance;

    public static DriverControls getDriverControlsInstance() {
        if (mDriverControlsInstance == null) {
            mDriverControlsInstance  = new DriverControls();
        }
        return mDriverControlsInstance;
    }

    

    public DriverControls() {
         stick = new Joystick(Constants.kJoystickPort);
         xbox = new XboxController(Constants.kXboxPort);

         SmartDashboard.setDefaultNumber("Power Gain", 2.0);
         SmartDashboard.setDefaultNumber("Steering Gain", 2.0);
    }
    /**
     * Returns true if the joystick has been jerked beyond 0.7.
     * @return Driver interrupt command.
     */
    public boolean getStickInterupt() {

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
		} else if(xBox.getX(Hand.kLeft) < -0.7){
			end = true;
		} else if(xBox.getX(Hand.kLeft) > 0.7){
			end = true;
		} else if(xBox.getX(Hand.kRight) < -0.7){
			end = true;
		} else if(xBox.getX(Hand.kRight) > 0.7){
			end = true;
		} else if(xBox.getY(Hand.kLeft) < -0.7){
			end = true;
		} else if(xBox.getY(Hand.kLeft) > 0.7){
			end = true;
		} else if(xBox.getY(Hand.kRight) < -0.7){
			end = true;
		} else if(xBox.getY(Hand.kRight) > 0.7){
			end = true;
		}
	    return end;
	}
}
