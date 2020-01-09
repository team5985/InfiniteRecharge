/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;

public class SensoredSystem implements SpeedController, PbEncoder {
    SpeedController motor;
    PbEncoder encoder;

    // DigitalInput dioA;
    // DigitalInput dioB;

    public SensoredSystem(SpeedController motor, PbEncoder encoder) {
        this.motor = motor;
        this.encoder = encoder;
    }

    // public SensoredSystem(SpeedController motor, PbEncoder encoder, DigitalInput dioA, DigitalInput dioB) {
    // }

    @Override
    public void pidWrite(double output) {
        this.set(output);
    }

    @Override
    public int getCounts() {
        return encoder.getCounts();
    }

    @Override
    public void set(double speed) {
        motor.set(speed);
    }

    @Override
    public double get() {
        return motor.get();
    }

    @Override
    public void setInverted(boolean isInverted) {
        motor.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return motor.getInverted();
    }

    @Override
    public void disable() {
        motor.disable();
    }

    @Override
    public void stopMotor() {
        motor.stopMotor();
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return null;
    }

    @Override
    public double pidGet() {
        return encoder.getCounts();
    }
}
