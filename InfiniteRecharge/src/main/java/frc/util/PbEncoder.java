/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util;

import edu.wpi.first.wpilibj.CounterBase;

public class PbEncoder implements EncoderAdapter {
    CounterBase encoder;

    public PbEncoder(CounterBase dioEncoder) {
        encoder = dioEncoder;
    }

    public int getCounts() {
        return encoder.get();
    }

    public void reset() {
        encoder.reset();
    }

    @Override
    public void setCounts(int counts) {
        
    }
}
