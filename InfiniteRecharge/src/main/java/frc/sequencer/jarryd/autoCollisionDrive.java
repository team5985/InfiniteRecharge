package frc.sequencer.jarryd;

import frc.robot.subsystems.Drive;
import frc.sequencer.SequenceTransition;

public class autoCollisionDrive extends SequenceTransition{

    @Override
    public void transStart() {
        
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean transUpdate() {
        double xAccel = Drive.getInstance().getImuInstance().getWorldLinearAccelX();
        double yAccel = Drive.getInstance().getImuInstance().getWorldLinearAccelY();

        // System.out.println(Drive.getInstance().getImuInstance().getWorldLinearAccelX() + "," + Drive.getInstance().getImuInstance().getWorldLinearAccelY());
        // System.out.println(Math.sqrt(xAccel*xAccel+yAccel*yAccel));
        // TODO Auto-generated method stub
      
        double currAccel = Math.sqrt(xAccel*xAccel+yAccel*yAccel);
        for ( int ii = 0 ; ii < pastLength - 1 ; ii++)
        {
            pastAccel[ii] = pastAccel[ii+1];
        }
        pastAccel[pastLength-1] = currAccel;
        return isTransComplete();
    }

    @Override
    public boolean isTransComplete()
    {
        double sum = 0;
        StringBuffer accBuf = new StringBuffer("Buffer - ");
        for (int ii = 0 ; ii < pastAccel.length ; ii++)
        {
            sum = sum + pastAccel[ii];
            accBuf.append(pastAccel[ii] + " ");
        }
        double average = sum / pastAccel.length;
        // System.out.println(pastAccel);
        // System.out.println(accBuf.toString() + average);
        System.out.println("Average " + String.format("%1.7f", average));
        if (average > 1.3){
            return true;
        }
        
        return false;
    
    }
    

    private final static int pastLength = 5;
    private double[] pastAccel = new double[pastLength];
}
