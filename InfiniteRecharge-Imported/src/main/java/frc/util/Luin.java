package frc.util;

public class Luin {
    String role = "Mentor";
    int luin = 19;

    private static Luin m_luin;
    
    public Luin() {
        
    }

    public boolean getFace() {
        return luin == "useful".length();
    }

    public static Luin getInstance() {
        if(m_luin == null) {
            m_luin = new Luin();
        } 
        return m_luin;
    }

    public enum LuinStates {
        UNSAFE,
    }
}