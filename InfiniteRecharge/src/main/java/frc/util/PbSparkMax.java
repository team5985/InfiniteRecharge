/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.AlternateEncoderType;

import edu.wpi.first.wpilibj.SpeedController;

public class PbSparkMax extends CANSparkMax implements SpeedController, EncoderAdapter {
    static CANEncoder encoder;
    
    /**
     * Constructs a SPARK MAX and the encoder connected to the encoder pins or front port of the SPARK MAX.
     * 
     * The default encoder type is assumed to be the hall effect for brushless.
     * This can be modified for brushed DC to use a quadrature encoder.
     * 
     * Assumes that the encoder the is integrated encoder, configured as: 
     * 
     * EncoderType.kHallEffect, 0 counts per revolution.
     * 
     * @param deviceID
     * @param motorType
     */
    public PbSparkMax(int deviceID, MotorType motorType) {
        super(deviceID, motorType);
        encoder = super.getEncoder();
    }

    /**
     * Constructs a SPARK MAX and the encoder connected to the encoder pins or front port of the SPARK MAX.
     * @param deviceID
     * @param motorType
     * @param encoderType
     * @param counts_per_rev
     */
    public PbSparkMax(int deviceID, MotorType motorType, EncoderType encoderType, int counts_per_rev) {
        super(deviceID, motorType);
        encoder = super.getEncoder(encoderType, counts_per_rev);
    }

    /**
     * Constructs a SPARK MAX and its attached encoder connected to the alternate data port configured pins.
     * This is defined as: 
     * 
     * Multi-function Pin: Encoder A 
     * Limit Switch Reverse: Encoder B 
     * 
     * This call will disable the limit switch inputs.
     * @param deviceID
     * @param motorType
     * @param altEncoderType
     * @param counts_per_rev
     */
    public PbSparkMax(int deviceID, MotorType motorType, AlternateEncoderType altEncoderType, int counts_per_rev) {
        super(deviceID, motorType);
        encoder = super.getAlternateEncoder(altEncoderType, counts_per_rev);
    }

    @Override
    @Deprecated
    public void pidWrite(double output) {
        super.pidWrite(output);
    }

    @Override
    public void set(double speed) {
        super.set(speed);
    }

    @Override
    public double get() {
        return super.get();
    }

    @Override
    public void setInverted(boolean isInverted) {
        super.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return super.getInverted();
    }

    @Override
    public void disable() {
        super.disable();
    }

    @Override
    public void stopMotor() {
        super.stopMotor();
    }

    @Override
    public int getCounts() {
        return (int) Math.round(encoder.getPosition() * encoder.getCountsPerRevolution());
    }

    @Override
    public void reset() {
        encoder.setPosition(0.0);
    }

    @Override
    public void setCounts(int counts) {
        encoder.setPosition(counts / encoder.getCountsPerRevolution());
    }


    public void setPosition(double position) {
        encoder.setPosition(position);
    }
}
