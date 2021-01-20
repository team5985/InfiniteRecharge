/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.*;
import frc.robot.config.*;

import com.revrobotics.AlternateEncoderType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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
    private double shooterTargetRPM = Constants.kShooterDefaultRPM;

    static public Shooter getInstance() {
        if (m_instance == null) {
            m_instance = new Shooter();
        }

        return m_instance;
    }

    private Shooter() {
        currentState = ShooterState.IDLE;
        desiredState = ShooterState.IDLE;
    }

    public void update() {
        switch(desiredState) {
            case SHOOTING: 
                shooterPIDControl(shooterTargetRPM);
                System.out.println(RobotMap.getShooterVelocityEncoder().getVelocity());
               RobotMap.getShooterHoodSolenoid().set(true);

            break;
            case HOODUP: 
                RobotMap.getShooterHoodSolenoid().set(true); //FIXME
            break;
            case HOODDOWN: 
                RobotMap.getShooterHoodSolenoid().set(false); //FIXME
            break;
            default:
                shooterPIDControl(Constants.kShooterIdleSpeed);
                RobotMap.getShooterHoodSolenoid().set(false);
            break;
            
        }
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
        RobotMap.getShooter().set(-1);
        //Set indexer -1;
        RobotMap.getIndexer().set(-1);
    }
    public double getShooterRPM() {
        return RobotMap.getShooterVelocityEncoder().getVelocity();
    }

    /**
     * @param targetVelocity the target velocity for the shooter
     * @return If shooter is at target velocity
     */

    public boolean shooterPIDControl(double targetVelocity) {
        /*
        *Based of the spark max example
        */

        //Set motor PID constants
        RobotMap.getShooterAPIDController().setP(Constants.kShooterP);
        RobotMap.getShooterAPIDController().setI(Constants.kShooterI);
        RobotMap.getShooterAPIDController().setD(Constants.kShooterD);
        RobotMap.getShooterAPIDController().setIZone(Constants.kShooterIz);
        RobotMap.getShooterAPIDController().setFF(Constants.kShooterFF);
        RobotMap.getShooterAPIDController().setOutputRange(Constants.kShooterMinOutput, Constants.kShooterMaxOutput);

        RobotMap.getShooterBPIDController().setP(Constants.kShooterP);
        RobotMap.getShooterBPIDController().setI(Constants.kShooterI);
        RobotMap.getShooterBPIDController().setD(Constants.kShooterD);
        RobotMap.getShooterBPIDController().setIZone(Constants.kShooterIz);
        RobotMap.getShooterBPIDController().setFF(Constants.kShooterFF);
        RobotMap.getShooterBPIDController().setOutputRange(Constants.kShooterMinOutput, Constants.kShooterMaxOutput);
        
        // double p = SmartDashboard.getNumber("P Gain", 0);
        // double i = SmartDashboard.getNumber("I Gain", 0);
        // double d = SmartDashboard.getNumber("D Gain", 0);
        // double iz = SmartDashboard.getNumber("I Zone", 0);
        // double ff = SmartDashboard.getNumber("Feed Forward", 0);
        // double max = SmartDashboard.getNumber("Max Output", 0);
        // double min = SmartDashboard.getNumber("Min Output", 0);
        
        RobotMap.getShooterAPIDController().setReference(targetVelocity, ControlType.kVelocity);
        RobotMap.getShooterBPIDController().setReference(-targetVelocity, ControlType.kVelocity);
        System.out.println(targetVelocity);
        return getShooterAcceptableSpeed(targetVelocity);
    }

    public boolean getShooterAcceptableSpeed(double targetRPM) {
        if(getShooterRPM() >= targetRPM * 0.85) {
            return true;
        } else {
            return false;
        }
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
        IDLE,
    }

    public void setShooterTargetSpeed(double rpm) {
        shooterTargetRPM = rpm;
    }


}
