package frc.robot.auto;

import frc.robot.AutoController.AutoSelection;
import frc.robot.AutoController.StartSelection;

public abstract class AutoMode {
    private String name;
    private AutoSelection autoType;
    private StartSelection startPosition;

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
     * Returns where the auto starts at as type StartSelection.
     * @return Starting position of auto mode.
     */
    public StartSelection getStartPosition() {
        return startPosition;
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