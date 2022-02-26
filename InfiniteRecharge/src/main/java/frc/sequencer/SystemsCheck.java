package frc.sequencer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SystemsCheck
{
    // This is a simple example of how to provide a number of
    // sequences to the SmartDashboard to be able to be chosen.
    // Simply create a new method for each sequence, the method
    // must return the sequence object, and they will be added
    // to SmartDashboard.
    public static synchronized List<Sequence> getSequences()
    {
        if (theSequences == null)
        {
            theSequences = new LinkedList<Sequence>();
            theSequences.add(createTalonFX1Test());
            theSequences.add(createTalonFX2Test());
            theSequences.add(createTalonFX3Test());
            theSequences.add(createTalonFX4Test());

        }
        return Collections.unmodifiableList(theSequences);
    }

    /**
     * A benign sequence to be the default if people forget to select a sequence.
     * This will make the robot rotate slowly.
     */
 
    private static Sequence createTalonFX1Test()
    {
        DriveCheck dc1 = new DriveCheck();
        Sequence seq = new Sequence("Testing TalonFX 1 on the drivebase", 0);
        seq.setInitialSteps(dc1);
        seq.setInitialTransitions(dc1);
        return seq;
    }

    private static Sequence createTalonFX2Test()
    {
        DriveCheck dc2 = new DriveCheck();
        Sequence seq = new Sequence("Testing TalonFX 2 on the drivebase", 0);
        seq.setInitialSteps(dc2);
        seq.setInitialTransitions(dc2);
        return seq;
    }
    
    private static Sequence createTalonFX3Test()
    {
        DriveCheck dc3 = new DriveCheck();
        Sequence seq = new Sequence("Testing TalonFX 3 on the drivebase", 0);
        seq.setInitialSteps(dc3);
        seq.setInitialTransitions(dc3);
        return seq;
    }
    private static Sequence createTalonFX4Test()
    {
        DriveCheck dc4 = new DriveCheck();
        Sequence seq = new Sequence("Testing TalonFX 4 on the drivebase", 0);
        seq.setInitialSteps(dc4);
        seq.setInitialTransitions(dc4);
        return seq;
    }



    static LinkedList<Sequence> theSequences = null;
}
