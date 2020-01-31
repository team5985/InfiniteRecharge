package frc.util;

import edu.wpi.first.wpilibj.DigitalInput;

public class PbDioSwitch extends DigitalInput implements LimitSwitchAdapter {
    public PbDioSwitch(int channel) {
        super(channel);
    }
}