package frc.robot;

import edu.wpi.first.wpilibj.digitalModule;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.simpleRobot;
import edu.wpi.first.wpilibj.timer;

public class RobotTemplate extends simpleRobot 
{
    I2C i2c;
    byte[] tosend = new byte [1];
    
    public void robotInit()
    {
        digitalModule module = digitalModule.getInstance(2);
        i2c = module.getI2C (168);
    }
    public void autonomus ()
    {

    }
    public void operatorControl() 
    {
        boolean on = false;
        System.out.println("starting operationControl");
        while (isOperatorControl())
        {
            if (on)
                tosend[0] = 76;
            else
                tosend[0] = 72;
            on = ! on;
            i2c.transaction(tosend, 1,null, 0);
            timer.delay(0.0005);
        }
    }
    public void test()
    {
        
    }
}
