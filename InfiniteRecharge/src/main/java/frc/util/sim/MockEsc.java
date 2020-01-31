/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.sim;

import edu.wpi.first.wpilibj.SpeedController;
import frc.util.sim.physics.MotorModel;

public class MockEsc implements SpeedController {
    MotorModel motorModel = null;

    boolean isInverted = false;
    boolean isDisabled = false;
    double speed = 0.0;

    public MockEsc() {

    }

    public MockEsc(MotorModel motorModel) {
        this.motorModel = motorModel;
    }

    @Override
    public void set(double speed) {
        this.speed = speed;
        if (isInverted) speed = -speed;
        if (isDisabled) speed = 0.0;
        if (speed > 1) speed = 1;
        if (speed < -1) speed = -1;
    }

    public double get() {
        if (speed > 1) return 1;
        if (speed < -1) return -1;
        return speed;
    }

    @Override
    public void pidWrite(double output) {
        this.set(output);
    }

    @Override
    public void setInverted(boolean isInverted) {
        this.isInverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return isInverted;
    }

    @Override
    public void disable() {
        isDisabled = true;
    }

    @Override
    public void stopMotor() {
        speed = 0.0;
    }
}