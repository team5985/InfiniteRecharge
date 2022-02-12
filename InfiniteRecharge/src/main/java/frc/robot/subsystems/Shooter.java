/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.*;
import frc.robot.config.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANEncoder; 

/**
 * Add your docs here.
 */
public class Shooter extends Subsystem {
    static private Shooter m_instance;

    private ShooterState currentState;
    private ShooterState desiredState;
    int shooterZoneIndex = 0;
    double[] shooterZoneSpeed = {5800, 5300, 4800};
    private double shooterTargetRPM = 3025;//shooterZoneSpeed[shooterZoneIndex];//Constants.kShooterDefaultRPM;

  
    static public Shooter getInstance() {
        if (m_instance == null) {
            m_instance = new Shooter();
            RobotMap.getShooterA().configFactoryDefault();
            RobotMap.getShooterB().configFactoryDefault();

        }

        return m_instance;
    }

    private Shooter() {
        currentState = ShooterState.IDLE;
        desiredState = ShooterState.IDLE;
    }

    public void update() {
        //shooterTargetRPM = (SmartDashboard.getNumber("Shooter RPM", 5800*2))/2;
        //System.out.println(Vision.getInstance().getDistanceToTarget() + "m to target");
        //shooterTargetRPM = shooterZoneSpeed[shooterZoneIndex];
        switch(desiredState) {

            case SHOOTING: 
                shooterPIDControl(shooterTargetRPM);
                // System.out.println(RobotMap.getShooterA().getSelectedSensorVelocity());
                // System.out.println("Selected shooter speed = " + shooterZoneSpeed[shooterZoneIndex] + "current speed = " + RobotMap.getShooterA().getSelectedSensorVelocity() / 2048 * 600);
                currentState = desiredState;

            break;
            case HOODUP: 
                currentState = desiredState;

            break;
            case HOODDOWN: 
                currentState = desiredState;

            break;
            case ANTIJAM:
                removeShooterJam();
                currentState = desiredState;

            default:
                stopShooter();
                currentState = desiredState;

            break;
            
        }
    }
    
    /**
     * Use this to persistently set the desired shooting RPM
     * @param rpm
     */
    public void setShooterSpeed(double rpm) {
      //shooterTargetRPM = rpm;
    }

    public double getPosition() {
        return 0.0;
    }

    public boolean zeroPosition() {
        return false;
    }

    public long boot(double distance, int team) {
        long fouls = 0; //Hopefully :/
        //Send robot flying to the other side of the field and beyond!
        return fouls;
    }

    public double getShooterTargetSpeed() {
        return shooterTargetRPM;
    }
   
    public void removeShooterJam() {
        RobotMap.getShooterA().set(-0.5);
        RobotMap.getShooterB().set(0.5);
    }
    public double getShooterRPM() {
        return RobotMap.getShooterA().getSelectedSensorVelocity() * 600 / 2048;
    }

    /**
     * @param targetVelocity the target velocity for the shooter
     * @return If shooter is at target velocity
     */

    public boolean shooterPIDControl(double targetVelocity) {
        RobotMap.getShooterA().set(ControlMode.PercentOutput, -(targetVelocity/6380));
        RobotMap.getShooterB().set(ControlMode.PercentOutput, (targetVelocity/6380));

        return getShooterAcceptableSpeed(targetVelocity);
    }

    public boolean getShooterAcceptableSpeed(double targetRPM) {
        return (Math.abs(getShooterRPM()) >= Math.abs(shooterTargetRPM * 0.75));
    }


    public void setDesiredState(ShooterState state) {
        desiredState = state;
    }
    public ShooterState getCurrentState() {
        return currentState;
    }
    public enum ShooterState {
        SHOOTING,
        HOODUP,
        HOODDOWN,
        IDLE, ANTIJAM,
    }

    public void setShooterTargetSpeed(double rpm) {
        //shooterTargetRPM = rpm;
    }

    void stopShooter() {
        shooterPIDControl(Constants.kShooterIdleSpeed);

    }

    public void setShooterZoneIndex(int index) {
        shooterZoneIndex = index;
    }

}
