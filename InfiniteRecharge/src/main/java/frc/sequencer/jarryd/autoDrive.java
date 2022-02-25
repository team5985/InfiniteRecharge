package frc.sequencer.jarryd;

import frc.robot.subsystems.Drive;
import frc.sequencer.SequenceStepIf;
import frc.sequencer.SequenceTransition;

public class autoDrive extends SequenceTransition implements SequenceStepIf{

    @Override
    public void stepStart() {
        myEndDist = myDist + Drive.getInstance().getAvgEncoderDistance();
    
        myOldAccFwdLimit = Drive.getInstance().getAccFwdLimit();
        myOldAccRevLimit = Drive.getInstance().getAccRevLimit();
        if (myAccFwdLimit != 0){
            Drive.getInstance().setAccFwdLimit(myAccFwdLimit);
        }

        if (myAccRevLimit != 0){
        Drive.getInstance().setAccRevLimit(myAccRevLimit);
        }
    }

    @Override
    public void stepEnd() {
        Drive.getInstance().autoArcadeDrive(0, 0);
        Drive.getInstance().setAccFwdLimit(myOldAccFwdLimit);
        Drive.getInstance().setAccRevLimit(myOldAccRevLimit);       

    }

    @Override
    public void stepUpdate() {
        double angleErr = (myAngle - Drive.getInstance().getYaw()) % 360;
        if (angleErr > 180)
        {
            angleErr = angleErr - 360;
        }
        double steerCmd = angleErr *steerGain;

        double distErr = myEndDist - Drive.getInstance().getAvgEncoderDistance();

        double distCmd = Math.sqrt(Math.abs(distErr)) *distGain;
        if (distErr < 0)
        {
            distCmd = -distCmd;
        }
       
        distCmd = Math.min(mySpeed, Math.max(-mySpeed, distCmd));

        Drive.getInstance().autoArcadeDrive(steerCmd, distCmd);
        
    }

    private double steerGain = 0.01;
    public void setSteerGain (double aSteerGain)
    {
        steerGain = aSteerGain;
    }

    private double distGain = 2;
    public void setDistGain (double aDistGain)
    {
        distGain = aDistGain;
    }

    @Override
    public String stepName() {
        return "jdrive - " + myDist + " - " + myAngle;
    }
    private double myDist = 0;
    public void setDist (double aDist)
    {
        myDist = aDist;
    }

    private double myAngle = 0;
    public void setAngle(double anAngle)
    {
        myAngle = anAngle;

    }

    private double mySpeed = 0.3;
    public void setSpeed(double aSpeed)
    {
        mySpeed = aSpeed;
    }

    private double myEndDist = 0;
    
    @Override
    public boolean isTransComplete() {
        double distErr = myEndDist - Drive.getInstance().getAvgEncoderDistance();
        if (Math.abs(distErr) <0.10)
        {
            return true;
        }    
        return false;
    }

    @Override
    public void transStart() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean transUpdate() {
        // TODO Auto-generated method stub
        return isTransComplete();
    }

    private double myOldAccFwdLimit;
    private double myOldAccRevLimit;

    private double myAccFwdLimit = 0;
    public void setAccFwdLimit(double anAccFwdLimit)
    {
        myAccFwdLimit = anAccFwdLimit;
    }
    private double myAccRevLimit = 0;
    public void setAccRevLimit(double anAccRevLimit)
    {
        myAccRevLimit = anAccRevLimit;
    }


}
