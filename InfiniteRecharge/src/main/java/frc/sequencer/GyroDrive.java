package frc.sequencer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Drive;

public class GyroDrive extends SequenceTransition implements SequenceStepIf
{
    double myEndDist;
    double myDeltaDist;
    double myEndAngle;
    Robot myRobot;
    boolean myDebug = false;
    double myDeadband = 1;
    boolean myOnTrack = false;

    public GyroDrive()
    {        
    }

    /**
     * Called when the step that this transition relates to is started.
     * This can be used to save the starting state for transitions where a delta value
     * is used (eg, a step to move 2 metres will need to remember where we started
     * from in order to calculate how far we have moved).
     */
    public void transStart()
    {
        myEndDist = Drive.getInstance().getAvgEncoderDistance() + myDeltaDist;
    }

    /**
     * Called every scan of the sequencer. This allows the transition class to monitor
     * values over time.
     * This method must calculate and return whether the step has completed.
     * @return <code>true</code> if the step has completed.
     */
    public boolean transUpdate()
    {
        return isTransComplete();
    }

    /**
     * Returns whether the step has completed.
     * 
     * @return <code>true</code> if the step has completed.
     */
    public boolean isTransComplete()
    {
        if (Math.abs(Drive.getInstance().getAvgEncoderDistance() - myEndDist) < 0.1)
        {
            return true;
        }
        // Need to fix when we get a robot with encoders.
        return false;
    }


    /**
     * Called when the step is started.
     * This can be used to save the starting state for steps where a delta value
     * is used (eg, a step to move 2 metres will need to remember where we started
     * from in order to calculate how far we have moved).
     */
    public void stepStart()
    {
    }

    /**
     * Called when the step is ended.
     * This can be used to stop the motion of this step (eg, turn motors off, etc)
     */
    public void stepEnd()
    {
        Drive.getInstance().autoArcadeDrive(0.0, 0.0);
    }

    /**
     * Called every scan of the sequencer. This allows the step class to monitor
     * values over time.
     */
    public void stepUpdate()
    {
        double steerError = angleDiff(myEndAngle, Drive.getInstance().getImuInstance().getYaw());
        double driveError = myEndDist - Drive.getInstance().getAvgEncoderDistance();
        
        if (myDebug == true)
        {
            SmartDashboard.putNumber("GD Err", steerError);
        }

        // Steering
        double steerCommand = steerError * Constants.kGyroDriveTurnKp;
        
        if (steerCommand > 0.5)
        {
            steerCommand = 0.5;
        }
        else if (steerCommand < -0.5)
        {
            steerCommand = -0.5;
        }
        
        if (myDebug == true)
        {
            SmartDashboard.putNumber("GD Steer", steerCommand);
        }
        
        // (linear) Drive
        double driveCommand = driveError * Constants.kEncoderDriveKp;

        Drive.getInstance().autoArcadeDrive(steerCommand, driveCommand);

        if (Math.abs(steerError) < myDeadband)
        {
            myOnTrack = true;
        }
    }

    public void setDeadband(double anAngle)
    {
        myDeadband = anAngle;
    }

    public boolean getOnTrack()
    {
        return myOnTrack;
    }

    public void setAngle(double anAngle)
    {
        myEndAngle = anAngle;
    }

    public double getAngle()
    {
        return myEndAngle;
    }

    public void setDistance(double aDist)
    {
        myDeltaDist = aDist;
    }

    public String stepName()
    {
        return "GyroDrive";
    }

    public void setDebug(boolean aDebug)
    {
        myDebug = aDebug;
    }

    public double angleDiff(double angle1, double angle2)
    {
      double result;
      result = (angle1 - angle2) % 360;
      if (result > 180)
      {
        result = result - 360;
      }
      if (result < -180)
      {
        result = result + 360;
      }
      return result;
    }
  
}