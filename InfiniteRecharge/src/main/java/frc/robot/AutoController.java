package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
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
		new LeaveLine(),
	};

	public enum AutoSelection {
		DEFAULT,
		LEAVELINE,
		SHOOT_AND_SCOOT,
		SCOOT_AND_SHOOT,
		SHOOT_THEN_PICKUP_CENTRE,
		SHOOT_RECIEVE_SHOOT,
		CROSS_FIELD_TEN_BALL_AUTO,
		OUR_TRENCH_EIGHT_BALL_AUTO,
		
	}

	SendableChooser<AutoSelection> autoSelector;
	AutoSelection selectedAuto;
	AutoMode runningAuto;
	int currentStep = 0;
	boolean autoPeriodicPermissible = false;

	private AutoController() {
		autoSelector = new SendableChooser<AutoSelection>();
		
		// Add each auto to the dashboard dropdown menu
		for (AutoSelection auto : AutoSelection.values()) {
			autoSelector.addOption(auto.name(), auto);
		}
		autoSelector.setDefaultOption(AutoSelection.DEFAULT.name(), AutoSelection.DEFAULT);

		SmartDashboard.putData("Auto Selector", autoSelector);
	}

	public void initialiseAuto() {
		selectedAuto = autoSelector.getSelected();
		runningAuto = evaluateAutoSelection(selectedAuto);
		currentStep = 0;

		autoPeriodicPermissible = false; // wait until sensors are fully reset
		TeleopController.getInstance().resetAllSensors();
		try {
			Thread.sleep(250);
		} catch (Exception e) {
			DriverStation.reportWarning(e.toString(), true);
		}
		autoPeriodicPermissible = true;
	}

	public void runAuto() {
		if (autoPeriodicPermissible) {
			try {
				System.out.println("STEP: " + currentStep);
				boolean stepComplete = runningAuto.runStep(currentStep);
				if (stepComplete) {
					currentStep++;
				}

			} catch (NullPointerException e) {
				DriverStation.reportWarning(e.toString(), true);
			}
		}
	}

	/**
	 * @return True when the auto program has completed its motions
	 */
	public boolean exit() {
		return runningAuto.getExit();
	}

	private AutoMode evaluateAutoSelection(AutoSelection autoType) {
		AutoMode retval = null;
		
		for (AutoMode mode : autoModes) {
			if (mode.getAutoType() == autoType) {
				return mode;
			}
		}
		
		return new TrenchEightBallAuto();
	}
}