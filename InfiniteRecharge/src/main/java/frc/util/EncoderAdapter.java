package frc.util;

public interface EncoderAdapter {
    public int getCounts();
    public void setCounts(int counts);
    public void reset();
}