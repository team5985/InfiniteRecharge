package frc.util;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;

public class PbTalonSrx extends WPI_TalonSRX implements SpeedController, EncoderAdapter {

    public PbTalonSrx(int deviceNumber) {
        super(deviceNumber);
    }

    @Override
    public int getCounts() {
        return this.getSelectedSensorPosition();
    }

    @Override
    public void setCounts(int counts) {
        this.setSelectedSensorPosition(counts);

    }

    @Override
    public void reset() {
        this.setSelectedSensorPosition(0);
    }
}
