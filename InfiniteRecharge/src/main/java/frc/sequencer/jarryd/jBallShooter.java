package frc.sequencer.jarryd;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Shooter;
import frc.sequencer.SequenceTransition;

public class jBallShooter extends SequenceTransition{

    @Override
    public void transStart() {
        Shooter.getInstance().getShooterRPM();
        Shooter.getInstance().getShooterTargetSpeed(); 
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean transUpdate() {
        if (waitCounts > 0)
        {
            waitCounts --;
        }
        double currRPM = Shooter.getInstance().getShooterRPM();
        // compare currRPM with pastRPM[pastLength]
        if ( (currRPM / pastRPM[0]) < 0.95)
        {
            //ball detected
            if (waitCounts == 0)
            {
                waitCounts = waitTime;
                ballDetected();
            }
        }
        for ( int ii = 0 ; ii < pastLength - 1 ; ii++)
        {
            pastRPM[ii] = pastRPM[ii+1];
        }
        pastRPM[pastLength-1] = currRPM;
        return isTransComplete();
    }

    private void ballDetected()
    {
        numBalls = numBalls + 1;
        SmartDashboard.putNumber("balls shot", numBalls);
    }

    private double numBalls = 0;
    private final static int waitTime = 25;
    private int waitCounts = waitTime;


    @Override
    public boolean isTransComplete() {
        if (numBalls >= 2)
        {
            return true;
        }
        return false;
    } 
    

    private final static int pastLength = 5;
    private double[] pastRPM = new double[pastLength];
}

