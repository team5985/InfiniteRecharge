package frc.sequencer.jarryd;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drive;
import frc.sequencer.SequenceStepIf;
import frc.sequencer.SequenceTransition;

public class autoTurn extends SequenceTransition implements SequenceStepIf{

    @Override
    public void stepStart() {
        
    }

    @Override
    public void stepEnd() {
        Drive.getInstance().autoArcadeDrive(0, 0);        
    }

    @Override
    public void stepUpdate() {
        double angleErr = (myAngle - Drive.getInstance().getYaw()) % 360;
        if (angleErr > 180)
        {
            angleErr = angleErr - 360;
        }
        if (angleErr < -180)
        {
            angleErr = angleErr + 360;
        }
        SmartDashboard.putNumber("GT error", angleErr);
        double steerCmd = Math.sqrt(Math.abs(angleErr)) *0.045;
        if (angleErr < 0)
        {
            steerCmd = -steerCmd;
        }
        Drive.getInstance().autoArcadeDrive(steerCmd, 0);    
    }

    @Override
    public String stepName() {
        return "jturn - " + myAngle;
    }
    private double myAngle = 0;
    public void setAngle(double anAngle)
    {
        myAngle = anAngle;
    }

    @Override
    public void transStart() {
        
    }

    @Override
    public boolean transUpdate() {
        return isTransComplete();
    }

    @Override
    public boolean isTransComplete() {
        double angleErr = (myAngle - Drive.getInstance().getYaw()) % 360;
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

}
