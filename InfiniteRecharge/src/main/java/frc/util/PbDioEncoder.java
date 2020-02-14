/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util;

import edu.wpi.first.wpilibj.CounterBase;

public class PbDioEncoder implements EncoderAdapter {
    CounterBase encoder;
    int countOffset;

    public PbDioEncoder(CounterBase dioEncoder) {
        encoder = dioEncoder;
        countOffset = 0;
    }

    public int getCounts() {
        return encoder.get() + countOffset;
    }

    public void reset() {
        encoder.reset();
    }

    @Override
    public void setCounts(int counts) {
        this.reset();
        countOffset = counts;
    }
}

