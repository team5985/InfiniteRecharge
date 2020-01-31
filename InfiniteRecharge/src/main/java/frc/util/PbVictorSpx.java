package frc.util;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;

public class PbVictorSpx extends WPI_VictorSPX implements SpeedController {

    public PbVictorSpx(int deviceNumber) {
        super(deviceNumber);
    }

    public void set(double power) {
        this.set(power);
    }

}