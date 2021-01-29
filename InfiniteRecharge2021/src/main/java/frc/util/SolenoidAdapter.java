package frc.util;

public interface SolenoidAdapter {
    public void setForward();
    public void setReverse();
    public void setOff();

    public boolean getForward();
    public boolean getReverse();
    public boolean getOff();
}