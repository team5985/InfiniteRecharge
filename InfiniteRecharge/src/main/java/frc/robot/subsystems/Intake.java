package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;


import frc.robot.RobotMap;

public class Intake extends Subsystem {



    public double getPosition() {
        return 0.0; //position
      
    }

    public boolean zeroPosition() {
        return false;

    }

    public boolean extendIntake() {
        //Motion profile stuff
        RobotMap.getIntakeActuationSystem().set(1);
        return false;
    }
    public boolean retractIntake() {
        //Motion profile stuff
        RobotMap.getIntakeActuationSystem().set(-1);
        return false;
    }
    public void suckBalls() {
        RobotMap.getIntakeSystem().set(1.0);
    }
    public void spitBalls() {
        RobotMap.getIntakeSystem().set(-1.0);
    }

}