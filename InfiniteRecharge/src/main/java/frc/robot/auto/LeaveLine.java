package frc.robot.auto;

import frc.sequencer.GyroDrive;
import frc.sequencer.Sequencer;

public class LeaveLine extends AutoMode {

    public void init(Sequencer mSequencer) {
        double startAngle = 0.0;
        double endDist = 1.0;

        GyroDrive gDrive = new GyroDrive();
        gDrive.setAngle(startAngle);
        gDrive.setDistance(endDist);

        mSequencer.setInitialTransition(gDrive);
    }

}