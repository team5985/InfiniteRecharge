package frc.robot.auto;

import frc.robot.AutoController.AutoSelection;
import frc.robot.subsystems.Drive;

public class NoAuto extends AutoMode {
    static {
        name = "None";
        autoType = AutoSelection.NONE;
    }
    
    @Override
    public boolean getExit() {
        return false;
    }

    @Override
    public void init() {
        
    }

    @Override
    public boolean runStep(int step) {
        switch(step) {
            default:
            
            break;

        }

        return true;
    }

}