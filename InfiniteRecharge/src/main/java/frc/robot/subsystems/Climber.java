package frc.robot.subsystems;

import frc.util.LimitSwitchGroup;
import frc.util.SensoredSystem;

public class Climber extends Subsystem {
    public static Climber m_instance;

    public static Climber getInstance() {
        if (m_instance == null) {
            m_instance = new Climber();
        }

        return m_instance;
    }

    static SensoredSystem m_elevator;
    static SensoredSystem m_winch;
    static LimitSwitchGroup m_limitGroup;

    ElevatorState currentElevatorState;
    ElevatorState desiredElevatorState;
    


    private Climber() {
        currentElevatorState = ElevatorState.STOWED;
    }

    public static void setSystem(SensoredSystem elevator, SensoredSystem winch, LimitSwitchGroup limitGroup) {
        m_elevator = elevator;
        m_winch = winch;
        m_limitGroup = limitGroup;
    }

    public enum ElevatorState {
        STOWED,
        PREPARING,  // Specifically that it is moving from STOWED to PREPARED
        PREPARED,  // 
        CLIMBING,  // Moving from PREPARED to CLIMBED
        CLIMBED_LOW,
        CLIMBED_HI,
        ELEVATOR_MANUAL,
        WINCH_MANUAL,
    }

    public enum BuddyState {
        BUDDIES,  // Robot has a friend
        NO_BUDDIES,  // Robot does not have a friend
    }

    public void update() {
        
    }

    @Override
    public double getPosition() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
	public boolean zeroPosition() {
		// TODO Auto-generated method stub
		return false;
	}
}