package frc.sequencer.jarryd;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import frc.sequencer.Sequence;

public class AutoSequence {
    public static synchronized List<Sequence> getSequences()
    {
        if (theSequences == null)
        {
            theSequences = new LinkedList<Sequence>();
      


        }
        return Collections.unmodifiableList(theSequences);

    }
   

    


    static LinkedList<Sequence> theSequences = null;
}
