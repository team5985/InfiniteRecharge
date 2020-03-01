package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.*;
import frc.sequencer.Sequencer;

public class AutoController {
	public static AutoController instance;

	public static AutoController getInstance() {
		if (instance == null) {
			instance = new AutoController();
		}
		return instance;
	}

	public enum AutoSelection {
		DEFAULT, LEAVELINE,
	}

	SendableChooser<AutoSelection> autoSelector;
	AutoSelection selectedAuto;
	AutoMode runningAuto;
	int currentStep;
	Sequencer mSequencer;

	LeaveLine leaveLineMode = new LeaveLine();

	private AutoController() {
		autoSelector = new SendableChooser<AutoSelection>();
		mSequencer = new Sequencer();
		
		// Add each auto to the dashboard dropdown menu
		for (AutoSelection auto : AutoSelection.values()) {
			autoSelector.addOption(auto.name(), auto);
		}
		autoSelector.setDefaultOption(AutoSelection.DEFAULT.name(), AutoSelection.DEFAULT);

		SmartDashboard.putData("Auto Selector", autoSelector);
	}

	public void initialiseAuto() {
		selectedAuto = autoSelector.getSelected();
		TeleopController.getInstance().resetAllSensors();

		switch (selectedAuto) {
			case LEAVELINE:
			leaveLineMode.init(mSequencer);
			break;
			
			default:
			leaveLineMode.init(mSequencer);
			break;
		}

		mSequencer.sequenceStart();
	}

	public void runAuto() {
		mSequencer.update();
	}
}