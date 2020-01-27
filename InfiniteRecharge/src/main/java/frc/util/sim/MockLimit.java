package frc.util.sim;

import frc.util.LimitSwitchAdapter;

public class MockLimit implements LimitSwitchAdapter {
    private boolean value;
    
    public void set(boolean value) {
        this.value = value;
    }
    
    @Override
    public boolean get() {
        return value;
    }
}
