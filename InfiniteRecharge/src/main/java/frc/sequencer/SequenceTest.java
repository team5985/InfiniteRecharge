package frc.sequencer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SequenceTest
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
            theSequences.add(createBenignDefault());
            theSequences.add(create5mLine());
            theSequences.add(create2mLine());
            theSequences.add(createShoot());
        }
        return Collections.unmodifiableList(theSequences);
    }

    /**
     * A benign sequence to be the default if people forget to select a sequence.
     * This will make the robot rotate slowly.
     */
    private static Sequence createBenignDefault()
    {
        GyroTurn gt1 = new GyroTurn();
        gt1.setAngle(120, true);
        gt1.setDeadband(20);
        gt1.setMaxSteerCmd(0.15);
        gt1.setDebug(true);
        gt1.setNextTrans(gt1);
        gt1.setNextSteps(gt1);
        Sequence seq = new Sequence("Benign Default", 0);
        seq.setInitialSteps(gt1);
        seq.setInitialTransitions(gt1);
        return seq;
    }

    private static Sequence create5mLine()
    {
        GyroDrive gd1 = new GyroDrive();
        gd1.setAngle(0);
        gd1.setDistance(5);
        gd1.setDeadband(0.1);
        Sequence seq = new Sequence("5 Metre Test", 0);
        seq.setInitialSteps(gd1);
        seq.setInitialTransitions(gd1);
        return seq;
    }

    private static Sequence create2mLine()
    {
        GyroDrive gd1 = new GyroDrive();
        gd1.setAngle(0);
        gd1.setDistance(2);
        gd1.setDeadband(0.1);
        Sequence seq = new Sequence("2 Metre Test", 0);
        seq.setInitialSteps(gd1);
        seq.setInitialTransitions(gd1);
        return seq;
    }

    private static Sequence createShoot()
    {
        GyroDrive drive = new GyroDrive();
        drive.setDistance(1);

        ShootStep shoot = new ShootStep();
        TimedStep time1 = new TimedStep();
        time1.setDelay(5);
        drive.setNextSteps(shoot);
        drive.setNextTrans(time1);

        TimedStep time2 = new TimedStep();
        time2.setDelay(5);
        IndexShootStep ishoot = new IndexShootStep();
        time1.setNextSteps(shoot, ishoot);
        time1.setNextTrans(time1);

        Sequence seq = new Sequence("Shooter Test", 0);
        seq.setInitialSteps(drive);
        seq.setInitialTransitions(drive);
        return seq;
    }


    static LinkedList<Sequence> theSequences = null;
}
