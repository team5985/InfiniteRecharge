package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class GyroSensor{
  

    public GyroBase getGyro() {
        GyroBase gyro;
        gyro = new ADXRS450_Gyro();
        return gyro;
    }


    public double getGyroDegrees(GyroBase gyro){
        double gyroAngle = gyro.getAngle();
        return gyroAngle;
    }
    
    public double getGyroRotationRate(GyroBase gyro){
        double rotationRate = gyro.getRate();
        return rotationRate;

    }

    public GyroBase gyroReset(GyroBase gyro){
        gyro.reset();
        return gyro;
    }


}