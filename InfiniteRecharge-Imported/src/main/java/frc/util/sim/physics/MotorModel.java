/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.sim.physics;

public class MotorModel {
    private double kT;
    private double kV;
    private double resistance;

    private double stallTorque;
    private double freeSpeed;

    double currentLimit = 1000.0; // Arbitrary value that is higher than any stall current of any FRC motor

    /**
     * Use other constructor if possible
     * @param kT Nm/amp
     * @param kV rad/s/volt (do not use RPM/volts!)
     * @param resistance ohms
     * @param voltage volts
     */
    public MotorModel(double kT, double kV, double resistance) {
        this.kT = kT;
        this.kV = kV;
        this.resistance = resistance;
    }

    /**
     * Calculates kT, kV, and resistance based on numbers available from https://motors.vex.com/
     * Note that freeSpeed is in RPM, not rad/s
     * @param stallTorque Nm
     * @param stallCurrent A
     * @param freeSpeed RPM
     * @param voltage V
     */
    public MotorModel(double stallTorque, double stallCurrent, double freeSpeed, double voltage) {
        this.kT = stallTorque / stallCurrent;
        this.kV = (freeSpeed * 2*Math.PI / 60) / voltage; // Convert RPM to rad/s
        this.resistance = voltage / stallCurrent;

        this.stallTorque = stallTorque;
        this.freeSpeed = freeSpeed;
    }

    public void setCurrentLimit(double current) {
        this.currentLimit = current;
    }    
    
    /**
     * Calculate torque produced by the motor given only its voltage and speed
     * @param voltage in volts
     * @param motorSpeed in rad/s
     * @return torque in Nm
     */
    public double getTorque(double voltage, double motorSpeed) {
        double current = (voltage - motorSpeed / kV) / resistance;
        System.out.print("   I: " + current);

        if (current > currentLimit) current = currentLimit;
        if (current < -currentLimit) current = -currentLimit;
        
        return kT * current;
    }

    /**
     * Get current of motor given voltage and physical speed
     * @param voltage
     * @param motorSpeed
     * @return
     */
    public double getCurrent(double voltage, double motorSpeed) {
        return (voltage - motorSpeed / kV) / resistance;
    }
}
