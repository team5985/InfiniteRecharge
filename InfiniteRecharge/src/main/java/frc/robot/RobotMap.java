/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.subsystems.RobotWrangler;
import frc.util.LimitSwitchGroup;
import frc.util.PbDioSwitch;
import frc.util.PbSolenoid;
import frc.util.PbSparkMax;
import frc.util.PbTalonSrx;
import frc.util.SensoredSystem;

/**
 * Contains and constructs all of the devices on the robot.
 * This should be kept separate from Robot and the Subsystems so that:
 * - none of their code must change if there is a change in devices used
 * - we can run tests on each subsystem's logic using simulated devices
 */
public class RobotMap {
    public static SensoredSystem getRobotWranglerSystem() {
        PbSparkMax robotWranglerMotor;

        if (Constants.kUseRobotWranglerNeoEncoder) {
            robotWranglerMotor = new PbSparkMax(Constants.kRobotWranglerSparkCanId, MotorType.kBrushless);
        } else {
            robotWranglerMotor = new PbSparkMax(Constants.kRobotWranglerSparkCanId, MotorType.kBrushless, AlternateEncoderType.kQuadrature, 2048); 
            // if using alternate encoder with neo
        }
        
        SensoredSystem system = new SensoredSystem(robotWranglerMotor);
        return system;
	}
	
	public static SensoredSystem getControlPanelSystem() {
		PbTalonSrx controlPanelMotor;
		controlPanelMotor = new PbTalonSrx(Constants.kControlPanelMotor);
		SensoredSystem system = new SensoredSystem(controlPanelMotor);
		return system;
	}

    public static LimitSwitchGroup getRobotWranglerLimits() {
        PbDioSwitch robotWranglerForwardLimit = new PbDioSwitch(Constants.kRobotWranglerForwardLimitDio);
        PbDioSwitch robotWranglerReverseLimit = new PbDioSwitch(Constants.kRobotWranglerReverseLimitDio);
        LimitSwitchGroup limitSwitchGroup = new LimitSwitchGroup(robotWranglerForwardLimit, robotWranglerReverseLimit);
        return limitSwitchGroup;
	}
	public static PbSolenoid getControlPanelSolenoid() {
		if(Constants.kControlPanelDoubleSolenoid) {
		PbSolenoid controlPanelSolenoid = new PbSolenoid(Constants.kPcmCanID, Constants.kControlPanelSolenoidAChannel,
		 Constants.kControlPanelSolenoidBChannel);
		} else {
		PbSolenoid controlPanelSolenoid = new PbSolenoid(Constants.kPcmCanID, Constants.kControlPanelSolenoidAChannel);
		}
		return controlPanelSolenoid;	 
	} 
}
