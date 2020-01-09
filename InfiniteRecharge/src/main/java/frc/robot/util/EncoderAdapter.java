/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.PIDSourceType;

public class EncoderAdapter implements PbEncoder {
    

    public EncoderAdapter(CounterBase dioEncoder) {
        
    }

    public EncoderAdapter(CANEncoder canEncoder) {

    }

    @Override
    public int getCounts() {
        return 0;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return null;
    }

    @Override
    public double pidGet() {
        return 0;
    }

}
