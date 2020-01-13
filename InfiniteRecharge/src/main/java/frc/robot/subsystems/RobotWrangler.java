package frc.robot.subsystems;

import frc.util.SensoredSystem;

public class RobotWrangler extends Subsystem {
    static SensoredSystem m_controlSystem;
    // static DigitalInput m_robotSensor; // Subsystem classes should never access the HAL (Hardware Abstraction Layer), which DigitalInput does
    private WranglerState desiredState;
    private WranglerState currentState;
    
    public static RobotWrangler m_instance;
    
    public static RobotWrangler getInstance() {
        if (m_instance == null) {
            m_instance = new RobotWrangler();
        }

        return m_instance;
    }

    private RobotWrangler() {
        currentState = WranglerState.TOOTIN;
    }

    public static void setSystem(SensoredSystem controlSystem) {
        m_controlSystem = controlSystem;
    }

    // public static void setSystem(SensoredSystem controlSystem, DigitalInput robotSensor) {
    //     m_controlSystem = controlSystem;
    //     m_robotSensor = robotSensor;
    // }

    public enum WranglerState {
        WRANGLIN,
        SHOOTIN,
        TOOTIN,
        ROOTIN,
    }

    public void update() {
        switch (currentState) {
            case WRANGLIN:
                m_controlSystem.set(1.0);

                // if (m_robotSensor.get() == true) {
                //     currentState = WranglerState.TOOTIN;
                // } else {
                    currentState = desiredState;
                // }
                break;

            case SHOOTIN:
                m_controlSystem.set(-1.0);

                // if (m_robotSensor.get() == false) {
                //     currentState = WranglerState.ROOTIN;
                // } else {
                    currentState = desiredState;
                // }
                break;

            case TOOTIN:
                m_controlSystem.set(0.2);

                if (desiredState != WranglerState.SHOOTIN) {
                    currentState = desiredState;
                }
                break;

            case ROOTIN:
                m_controlSystem.stopMotor();
                break;

            default:
                m_controlSystem.stopMotor();
                break;
        }
    }

    public void setDesiredState(WranglerState desiredState) {
        this.desiredState = desiredState;
    }

    public WranglerState getCurrentState() {
        return currentState;
    }

    @Override
    public double getPosition() {
        return m_controlSystem.getCounts();
    }

    @Override
    public boolean zeroPosition() {
        m_controlSystem.setCounts(0);
        return true;
    }
}