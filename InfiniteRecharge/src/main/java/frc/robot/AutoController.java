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
		new RightTrenchAllBallAuto(),
		new ScootAndShoot(),
		new ShootAndScoot(),	
	};

	public enum AutoSelection {
		//DEFAULT,
		LEAVELINE,
		SHOOT_AND_SCOOT,
		SCOOT_AND_SHOOT,
		//SHOOT_THEN_PICKUP_CENTRE,
		//SHOOT_RECIEVE_SHOOT,
		//LEFT_TRENCH_CROSS_FIELD_AUTO,
		RIGHT_TRENCH_ALL_BALL_AUTO,
		//RIGHT_TRENCH_ALL_BALL_REPOSITION_AUTO,
		//DISTTEST,
		
	}

	SendableChooser<AutoSelection> autoSelector;
	AutoSelection selectedAuto;
	static AutoMode runningAuto;
	int currentStep = 0;
	boolean autoPeriodicPermissible = false;

	private AutoController() {
		autoSelector = new SendableChooser<AutoSelection>();
		
		// Add each auto to the dashboard dropdown menu
		for (AutoSelection auto : AutoSelection.values()) {
			if (auto == AutoSelection.LEAVELINE)
			{
				autoSelector.setDefaultOption(auto.name(), auto);
			}
			else
			{
				autoSelector.addOption(auto.name(), auto);
			}
		}

		SmartDashboard.putData("Auto Selector", autoSelector);
	}

	public void initialiseAuto() {
		selectedAuto = autoSelector.getSelected();
		SmartDashboard.putString("Sel auto", "" + selectedAuto);
		//runningAuto = //evaluateAutoSelection(selectedAuto);
		if(selectedAuto == AutoSelection.LEAVELINE) {
			runningAuto = new LeaveLine();
		} else if(selectedAuto == AutoSelection.RIGHT_TRENCH_ALL_BALL_AUTO) {
			runningAuto = new RightTrenchAllBallAuto();
		} else if(selectedAuto == AutoSelection.SCOOT_AND_SHOOT) {
			runningAuto = new ScootAndShoot();
		} else if(selectedAuto == AutoSelection.SHOOT_AND_SCOOT) {
			runningAuto = new ShootAndScoot();
		} else {
			runningAuto = null;
		}
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
		SmartDashboard.putString("Selected auto", "" + runningAuto);
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

	// private AutoMode evaluateAutoSelection(AutoSelection autoType) {
	// 	AutoMode retval = null;
		
	// 	for (AutoMode mode : autoModes) {
	// 		System.out.println("Auto " + mode + " type " + autoType);
	// 		if (mode.getAutoType() == autoType) {
	// 			return mode;
	// 		}
	// 	}
		
		
		
	// }
}