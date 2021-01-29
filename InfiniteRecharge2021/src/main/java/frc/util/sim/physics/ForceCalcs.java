/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.sim.physics;

public class ForceCalcs {

    static double gravity = 9.8; // m/s. Houston TX: 9.79997, Sydney NSW: 9.80663



    // public static ForceCalcs getInstance() {
    //     return instance;
    // }

    /**
     * 
     * @param mass of moving object
     * @return Downwards force as a positive scalar
     */
    public static double addGravity(double mass) {
        return Math.abs(mass * gravity);
    }

    /**
     * Calculates the static friction on an object. If force along motion is greater than max stiction, or speed is not 0, will return 0.
     * Otherwise will return parallelForce.
     * @param mu Coefficient of static friction (table here https://www.engineeringtoolbox.com/friction-coefficients-d_778.html)
     * @param normalForce Force acting perpendicular to the plane of motion
     * @param parallelForce Force acting parallel to the plane of motion
     * @param speed Speed parallel to the plane of motion
     * @return Static friction as a scalar with equal sign to the parallelForce
     */
    public static double addStiction(double mu, double normalForce, double parallelForce, double speed) {
        double stiction = mu * normalForce;
        if ((parallelForce > stiction) || speed != 0) return 0.0;
        return parallelForce;
    }

    /**
     * Calculates the kinetic friction on an object. If speed is 0, will return 0.
     * Otherwise will return mu*normalForce.
     * @param mu Coefficient of kinetic friction (table here https://www.engineeringtoolbox.com/friction-coefficients-d_778.html)
     * @param normalForce Force acting perpendicular to the plane of motion
     * @param speed Speed parallel to the plane of motion
     * @return Kinetic friction as a scalar with equal sign to the normalForce
     */
    public static double addKineticFriction(double mu, double normalForce, double speed) {
        double friction = mu * normalForce;
        if (speed == 0) return 0.0;
        return friction;
    }
}
