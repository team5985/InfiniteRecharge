/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

/**
 * Add your docs here.
 */
public abstract class Subsystem {
	/**
	 * Run the subsystem state machine / logic / magic / etc. iteratively. Should be called iteratively 
	 * by TeleopController / AutoController (and nobody else! unless there is a test somewhere)
	 */
	public abstract void update();

	/**
	 * Get the position of the mechanism's axis.
	 * @return Current position. Units will be in metres or degrees.
	 */
	public abstract double getPosition(); 
	
	/**
	 * Zero the mechanism by running a zeroing routine.
	 * @return True when the sensor is zeroed.
	 */
	public abstract boolean zeroPosition();
	
}