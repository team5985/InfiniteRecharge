/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.sim;

import frc.util.EncoderAdapter;

public class MockEncoder implements EncoderAdapter {
    int countsPerRev;
    double gearboxRatio;

    int counts = 0;
    double pos = 0.0;

    public MockEncoder(int countsPerRev, double gearboxRatio) {
        this.countsPerRev = countsPerRev;
        this.gearboxRatio = gearboxRatio;
    }

    public int getCounts() {
        return (int) Math.round(pos * countsPerRev);
    }

    public double getCalculatedPosition() {
        return pos;
    }

    public void update(double dt, double speed) {
        double posDifference = speed * dt;
        pos += posDifference;
    }

    public void reset() {
        counts = 0;
        pos = 0.0;
    }

    @Override
    public void setCounts(int counts) {
        this.counts = counts;
    }
}
