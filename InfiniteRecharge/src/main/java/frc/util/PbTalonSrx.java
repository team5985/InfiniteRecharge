package frc.util;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;

public class PbTalonSrx extends WPI_TalonSRX implements SpeedController, EncoderAdapter {

    public PbTalonSrx(int deviceNumber) {
        super(deviceNumber);
    }

    @Override
    public int getCounts() {
        return super.getSelectedSensorPosition();
    }

    @Override
    public void setCounts(int counts) {
        super.setSelectedSensorPosition(counts);

    }

    @Override
    public void reset() {
        super.setSelectedSensorPosition(0);
    }
}
