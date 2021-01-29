package frc.util;

public class LimitSwitchGroup {
    private final LimitSwitchAdapter[] mLimitSwitches;
    
    public LimitSwitchGroup(LimitSwitchAdapter forwardLimit, LimitSwitchAdapter reverseLimit) {
        mLimitSwitches = new LimitSwitchAdapter[2];
        mLimitSwitches[0] = forwardLimit;
        mLimitSwitches[1] = reverseLimit;
    }

    /**
     * Create a group of limit switches into one object.
     * @param limits
     */
    public LimitSwitchGroup(LimitSwitchAdapter...limits) {
        mLimitSwitches = new LimitSwitchAdapter[limits.length];
        for (int i = 0; i < limits.length; i++) {
            mLimitSwitches[i] = limits[i];
        }
    }

    /**
     * 
     * @return limit switch of index 0. Using the forward/reverse constructor this would be the forwardLimit.
     */
    public boolean getForward() {
        return mLimitSwitches[0].get();
    }

    /**
     * 
     * @return limit switch of index 1. Using the forward/reverse constructor this would be the reverseLimit.
     */
    public boolean getReverse() {
        return mLimitSwitches[1].get();
    }

    /**
     * @param index
     * @return the value of a limit switch of an arbitrary index as assigned when this LimitSwitchGroup was constructed, index starting from 0.
     */
    public boolean get(int index) {
        return mLimitSwitches[index].get();
    }

    public LimitSwitchAdapter getInstance(int index) {
        return mLimitSwitches[index];
    }
}