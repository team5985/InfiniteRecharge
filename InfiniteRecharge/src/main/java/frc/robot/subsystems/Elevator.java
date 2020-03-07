package frc.robot.subsystems;

import frc.util.SensoredSystem;

public class Elevator extends Subsystem {
    private static Elevator mInstance;

    public static Elevator getInstance() {
        if (mInstance == null) {
            mInstance = new Elevator();
        }

        return mInstance;
    }

    SensoredSystem mElevatorSystem;

    public void setSystem(SensoredSystem elevatorSystem) {
        mElevatorSystem = elevatorSystem;
    }

    public void move(double speed) {
        mElevatorSystem.set(speed);
    }

	@Override
	public void update() {
		// TODO stuff
	}

    /**
     * Returns in rotations
     */
	@Override
	public double getPosition() {
		return mElevatorSystem.getCounts();
	}

    /**
     * TODO Auto-zeroing with hall effect
     */
	@Override
	public boolean zeroPosition() {
        mElevatorSystem.reset();
		return true;
	}
}