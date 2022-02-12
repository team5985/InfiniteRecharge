package frc.sequencer.jarryd;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drive;
import frc.sequencer.SequenceStepIf;
import frc.sequencer.SequenceTransition;

public class jcurve extends SequenceTransition implements SequenceStepIf
{

    @Override
    public void stepStart() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stepEnd() {
        Drive.getInstance().autoArcadeDrive(0, 0);
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stepUpdate() {
        double nowDist = Drive.getInstance().getAvgEncoderDistance() - startDist;
        double nowAngle;
        if (myAngle < 0)
        {
            nowAngle = startAngle - (nowDist)/(2 * Math.PI * myRadius )*360;
        }
        else
        {
            nowAngle = startAngle + (nowDist)/(2 * Math.PI * myRadius )*360;
        }
        double angleErr = (nowAngle - Drive.getInstance().getYaw()) % 360;
        if (angleErr < 0)
        {
            angleErr = angleErr + 360;
        }
        if (angleErr > 180)
        {
            angleErr = angleErr - 360;
        }
        SmartDashboard.putNumber("jtest", angleErr);
        double steerCmd = angleErr * 0.01;
        SmartDashboard.putNumber("steer", steerCmd);
        

        Drive.getInstance().autoArcadeDrive(steerCmd, mySpeed);
    }

    @Override
    public String stepName() {

        // TODO Auto-generated method stub
    return "jcurve - " + myAngle;    }

    @Override
    public void transStart() {
        startDist = Drive.getInstance().getAvgEncoderDistance();
        startAngle = Drive.getInstance().getYaw();
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean transUpdate() {

        // TODO Auto-generated method stub
        return isTransComplete();
    }

    @Override
    public boolean isTransComplete() {
        double myEndAngle = myAngle + startAngle;
        if (mySpeed < 0)
        {
            myEndAngle = (360 - myAngle) + startAngle;
        }
        double angleErr = (myEndAngle - Drive.getInstance().getYaw()) % 360;
        if (angleErr > 180)
        {
            angleErr = angleErr - 360;
        }
        if (Math.abs(angleErr) <5)
        {
            return true;
        }    
        return false;
    }
    private double myAngle = 0;
    public void setAngle(double anAngle)
    {
        myAngle = anAngle;
    }

    private double myRadius = 0;
    public void setRadius(double aRadius)
    {
        myRadius = aRadius;
    }

    private double mySpeed = 0.3;
    public void setSpeed(double aSpeed)
    {
        mySpeed = aSpeed;
    }

    private double startDist = 0;
    private double startAngle = 0;


}