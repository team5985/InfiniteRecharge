package frc.util;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * A not too hacky way of getting working functionality for both Solenoid and DoubleSolenoid with a single class.
 */
public class PbSolenoid extends DoubleSolenoid implements SolenoidAdapter {
    private enum SolenoidType {
        SINGLE,
        DOUBLE,
    }

    private SolenoidType solenoidType;
    
    /**
     * Create a single solenoid.
     * @param moduleNumber CAN ID of the PCM
     * @param channel on the PCM
     */
    public PbSolenoid(int moduleNumber, int channel) {
        super(moduleNumber, channel);
        solenoidType = SolenoidType.SINGLE;
    }

    /**
     * Create a double solenoid.
     * @param moduleNumber CAN ID of the PCM
     * @param forwardChannel on the PCM
     * @param reverseChannel on the PCM
     */
    public PbSolenoid(int moduleNumber, int forwardChannel, int reverseChannel) {
        super(moduleNumber, forwardChannel, reverseChannel);
        solenoidType = SolenoidType.DOUBLE;
    }

    @Override
    public void setForward() {
        super.set(Value.kForward);
    }

    @Override
    public void setReverse() {
        if (solenoidType == SolenoidType.SINGLE) {
            this.setOff();
        } else {
            super.set(Value.kReverse);
        }
    }

    @Override
    public void setOff() {
        this.set(Value.kOff);
    }

    @Override
    public boolean getForward() {
        return (super.get() == Value.kForward);
    }

    @Override
    public boolean getReverse() {
        if (solenoidType == SolenoidType.SINGLE) {
            return (super.get() == Value.kOff);
        } else if (solenoidType == SolenoidType.DOUBLE) {
            return (super.get() == Value.kReverse);
        } else {
            return false;
        }
    }

    @Override
    public boolean getOff() {
        return (super.get() == Value.kOff);
    }
    
}