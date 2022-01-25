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
            theSequences.add(create5mLine());
            theSequences.add(create2mLine());
        }
        return Collections.unmodifiableList(theSequences);
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

    static LinkedList<Sequence> theSequences = null;
}
