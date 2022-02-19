package frc.sequencer.jarryd;

import frc.robot.subsystems.Drive;
import frc.sequencer.SequenceTransition;

public class jCollisionDrive extends SequenceTransition{

    @Override
    public void transStart() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean transUpdate() {
        double xAccel = Drive.getInstance().getImuInstance().getWorldLinearAccelX();
        double yAccel = Drive.getInstance().getImuInstance().getWorldLinearAccelY();

        System.out.println(Drive.getInstance().getImuInstance().getWorldLinearAccelX() + "," + Drive.getInstance().getImuInstance().getWorldLinearAccelY());
        System.out.println(Math.sqrt(xAccel*xAccel+yAccel*yAccel));
        // TODO Auto-generated method stub
        return isTransComplete();
    }

    @Override
    public boolean isTransComplete() {
        double xAccel = Drive.getInstance().getImuInstance().getWorldLinearAccelX();
        double yAccel = Drive.getInstance().getImuInstance().getWorldLinearAccelY();
        if (Math.sqrt(xAccel*xAccel+yAccel*yAccel) > 2){
            return true;
        }
        return false;
    }
    
}
