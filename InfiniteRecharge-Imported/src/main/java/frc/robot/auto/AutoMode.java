package frc.robot.auto;

import frc.robot.AutoController.AutoSelection;

public abstract class AutoMode {
    private String name;
    private AutoSelection autoType;

    /**
     * Returns String of the name of the auto mode.
     * @return Name of auto mode.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns what the auto does as type AutoSelection.
     * @return Type of auto mode.
     */
    public AutoSelection getAutoType() {
        return autoType;
    }

    /**
     * @return True when auto movements are complete
     */
    public abstract boolean getExit();

    public abstract void init();
    
    /**
     * Will run a given step in the autonomous period.
     * @param step
     * @return True when step is completed.
     */
    public abstract boolean runStep(int step);
}