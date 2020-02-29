package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.*;

public class AutoController {
	public static AutoController instance;

	public static AutoController getInstance() {
		if (instance == null) {
			instance = new AutoController();
		}
		return instance;
	}

	public AutoMode[] autoModes = new AutoMode[] {
		new DefaultAuto(),
		new LeftToLeft(),
		new RightToRight(),
		new CentreToFront(),
	};

	public enum StartSelection {
		DEFAULT,
		LEFT_LEV1,
		CENTRE_LEV1,
		RIGHT_LEV1,
		LEFT_LEV2,
		RIGHT_LEV2,
	}

	public enum AutoSelection {
		DEFAULT, LEFT_CARGOSHIP, RIGHT_CARGOSHIP, CENTRE_CARGOSHIP,
		
	}

	SendableChooser<AutoSelection> autoSelector;
	AutoSelection selectedAuto;
	SendableChooser<StartSelection> positionSelector;
	StartSelection selectedPosition;
	AutoMode runningAuto;
	int currentStep;

	private AutoController() {
		autoSelector = new SendableChooser<AutoSelection>();
		positionSelector = new SendableChooser<StartSelection>();
		
		// Add each auto to the dashboard dropdown menu
		for (AutoSelection auto : AutoSelection.values()) {
			autoSelector.addOption(auto.name(), auto);
		}
		autoSelector.setDefaultOption(AutoSelection.DEFAULT.name(), AutoSelection.DEFAULT);

		for (StartSelection selection : StartSelection.values()) {
			positionSelector.addOption(selection.name(), selection);
		}
		positionSelector.setDefaultOption(StartSelection.DEFAULT.name(), StartSelection.DEFAULT);

		SmartDashboard.putData("Auto Selector", autoSelector);
		SmartDashboard.putData("Position Selector", positionSelector);
	}

	public void initialiseAuto() {
		selectedPosition = positionSelector.getSelected();		
		selectedAuto = autoSelector.getSelected();
		runningAuto = evaluateAutoSelection(selectedAuto, selectedPosition);

		TeleopController.getInstance().resetAllSensors();

		currentStep = 0;
	}

	public void runAuto() {
		boolean stepComplete = runningAuto.runStep(currentStep);
		if (stepComplete) {
			currentStep++;
		}
	}

	/**
	 * @return True when the auto program has completed its motions
	 */
	public boolean exit() {
		return runningAuto.getExit();
	}

	private AutoMode evaluateAutoSelection(AutoSelection autoType, StartSelection start) {
		AutoMode retval = null;
		
		for (AutoMode mode : autoModes) {
			if ((mode.getAutoType() == autoType) && (mode.getStartPosition() == start)) {
				return mode;
			}
		}
		
		return retval;
	}
}