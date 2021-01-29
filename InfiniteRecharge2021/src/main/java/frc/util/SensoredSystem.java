/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util;

import edu.wpi.first.wpilibj.SpeedController;

public class SensoredSystem implements SpeedController, EncoderAdapter {
    SpeedController motor;
    EncoderAdapter encoder;
    
    public SensoredSystem(SpeedController motor, EncoderAdapter encoder) {
        this.motor = motor;
        this.encoder = encoder;
    }

    public SensoredSystem(PbSparkMax sparkMax) {
        this.motor = sparkMax;
        this.encoder = sparkMax;
    }

    public SensoredSystem(PbTalonSrx canTalon) {
        this.motor = canTalon;
        this.encoder = canTalon;
    }

    @Override
    public void pidWrite(double output) {
        motor.set(output);
    }

    @Override
    public int getCounts() {
        return encoder.getCounts();
    }

    public void setCounts(int counts) {
        encoder.setCounts(counts);
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
    public void reset() {
        encoder.reset();
    }

    @Override
    public void setVoltage(double outputVolts) {
        motor.setVoltage(outputVolts);
    }
    
}
