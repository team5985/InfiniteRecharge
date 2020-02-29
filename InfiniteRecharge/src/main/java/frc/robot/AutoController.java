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
		DEFAULT, LEFT_CARGOSHIP, RIGHT_CARGOSHIP, CENTRE_CARGOSHIP,
	}

	SendableChooser<AutoSelection> autoSelector;
	AutoSelection selectedAuto;
	AutoMode runningAuto;
	int currentStep;
	Sequencer mSequencer;

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
		TeleopController.resetAllSensors();

		switch (selectedAuto) {
			default:
			LeaveLine.init(mSequencer);
			break;
		}

		mSequencer.startSequence();
	}

	public void runAuto() {
		mSequencer.update();
	}
}