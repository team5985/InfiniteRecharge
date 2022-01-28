package frc.sequencer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class GyroTurn extends SequenceTransition implements SequenceStepIf
{
    double myEndAngle;
    double myAngle;
    double myMaxSteerCmd = 0.5;
    boolean myIsRelative;
    boolean myDebug = false;

    /**
     * Called when the step that this transition relates to is started.
     * This can be used to save the starting state for transitions where a delta value
     * is used (eg, a step to move 2 metres will need to remember where we started
     * from in order to calculate how far we have moved).
     */
    public void transStart()
    {
        if (myIsRelative == true)
        {
            myEndAngle = Drive.getInstance().getImuInstance().getYaw() + myAngle;
        }
        else
        {
            myEndAngle = myAngle;
        }
        if (myDebug == true)
        {
            SmartDashboard.putNumber("GT end angle", myEndAngle);
        }
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
        if (Math.abs(angleDiff(Drive.getInstance().getImuInstance().getYaw(), myEndAngle)) < myDeadband)
        {
            if (myDebug == true)
            {
                SmartDashboard.putString("Transition Complete", "TRUE");
            }
            return true;
        }
        if (myDebug == true)
        {
            SmartDashboard.putString("Transition Complete", "FALSE");
        }
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
        Drive.getInstance().setMotors(0, 0);
    }

    /**
     * Called every scan of the sequencer. This allows the step class to monitor
     * values over time.
     */
    public void stepUpdate()
    {
        double error = angleDiff(myEndAngle, Drive.getInstance().getImuInstance().getYaw());
        
        if (myDebug == true)
        {
            SmartDashboard.putNumber("GT error", error);
        }
        int direction = 1;
        if (error < 0)
        {
            direction = -1;
        }
        double steerCommand = (error * Constants.kGyroTurnKp) + Constants.kDriveTurnStictionConstant;
        if (Math.abs(steerCommand) > myMaxSteerCmd)
        {
            steerCommand = myMaxSteerCmd;
        }
        steerCommand = steerCommand * direction;
        SmartDashboard.putNumber("GT STeer Command.", steerCommand);
        Drive.getInstance().autoArcadeDrive(steerCommand, 0.0);
    }

    public void setAngle(double anAngle, boolean aRelative)
    {
        myAngle = anAngle;
        myIsRelative = aRelative;
    }

    public double getAngle()
    {
        return myAngle;
    }

    private double myDeadband = 2;
    public void setDeadband(double anAngle)
    {
        myDeadband = anAngle;
    }

    public void setMaxSteerCmd(double aMaxSteerCmd)
    {
        if (aMaxSteerCmd > 1)
        {
            myMaxSteerCmd = 1;
        }
        else
        {
            myMaxSteerCmd = aMaxSteerCmd;
        }
    }

    public String stepName()
    {
        return "GyroTurn";
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