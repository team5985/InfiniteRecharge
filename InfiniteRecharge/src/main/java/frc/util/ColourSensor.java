package frc.util;



import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class ColourSensor
{   
    /**
     * Colour RED seen at the control panel.
     */
    public static final int CPANEL_COLOUR_RED = 0;

    /**
     * Colour GREEN seen at the control panel.
     */
    public static final int CPANEL_COLOUR_GREEN = 1;

    /**
     * Colour BLUE seen at the control panel.
     */
    public static final int CPANEL_COLOUR_CYAN = 2;

    /**
     * Colour YELLOW seen at the control panel.
     */
    public static final int CPANEL_COLOUR_YELLOW = 3;

    /**
     * The colour seen at the control panel  did not match any of the expected colours.
     */
    public static final int CPANEL_COLOUR_INVALID = -1;

    /**
     * The number of colour transitions per full rotation of the colour wheel.
     */
    public static final int CPANEL_COLOURS_PER_ROTATION = 8;
  

    // Values from test with sensor LED turned on.
    public static final Color LIT_CYAN = ColorMatch.makeColor(0.14,0.42,0.43);
    public static final Color LIT_GREEN = ColorMatch.makeColor(0.19,0.53,0.27);
    public static final Color LIT_RED = ColorMatch.makeColor(0.48,0.36,0.16);
    public static final Color LIT_YELLOW = ColorMatch.makeColor(0.32,0.53,0.14);

    // Values from test with sensor LED turned off (ambient light).
    public static final Color AMB_CYAN = ColorMatch.makeColor(0.14,0.39,0.45);
    public static final Color AMB_GREEN = ColorMatch.makeColor(0.20,0.51,0.28);
    public static final Color AMB_RED = ColorMatch.makeColor(0.59,0.29,0.12);
    public static final Color AMB_YELLOW = ColorMatch.makeColor(0.38,0.49,0.13);

    /**
     * The previous colour the color was.
     */
    private int PreviousColour = CPANEL_COLOUR_INVALID;
    /** 
     * The colour since the last scan of the colour sensor.
     */
    private int lastColour = CPANEL_COLOUR_INVALID;
    /**
     * A count of how many times the sensor has scammed the smae colour
     */
    private int scanCount = 0;

    // FMS values for colours
    public static final char FMS_CYAN = 'B';
    public static final char FMS_RED = 'R';
    public static final char FMS_YELLOW = 'Y';
    public static final char FMS_GREEN = 'G';

    /**
     * The singleton instance of this class.
     */
    private static ColourSensor theInstance = null;
    
    /**
     * The I2C colour sensor on the robot.
     */
    private ColorSensorV3 sensor;

    /**
     * The class that compares the observed colours with the list of colours that we are expecting.
     */
    private ColorMatch matcher = new ColorMatch();

    /**
     * The number of colour change transitions that have been counted since this was last reset.
     */
    private int myColourChanges = 0;

    /**
     * The direction of rotation for the last transition that was detected.
     * <code>true</code> is Clockwise.
     * <code>false</code> is AntiClockwise.
     */
    private boolean myLastTransitionDir = false;

    /**
     * Constructor for this class.
     */
    private ColourSensor()
    {
        I2C.Port i2cPort = I2C.Port.kOnboard;
        sensor = new ColorSensorV3(i2cPort);

        matcher = new ColorMatch();
        matcher.addColorMatch(AMB_CYAN);
        matcher.addColorMatch(AMB_RED);
        matcher.addColorMatch(AMB_GREEN);
        matcher.addColorMatch(AMB_YELLOW);
        matcher.setConfidenceThreshold(0.8);
    }

    /**
     * Creates (if necessary) and returns the one and only instance of the ColourSensor
     * class.
     * 
     * @return the instance of {@link ColourSensor}
     */
    public static synchronized ColourSensor getInstance()
    {
        if (theInstance == null)
        {
            theInstance = new ColourSensor();
        }
        return theInstance;
    }

    /**
     * Gets tyhe colour seen by the colour sensor.
     *  Returns one of...
     * {@link #CPANEL_COLOUR_CYAN}
     * {@link #CPANEL_COLOUR_GREEN}
     * {@link #CPANEL_COLOUR_RED}
     * {@link #CPANEL_COLOUR_YELLOW}
     * {@link #CPANEL_COLOUR_INVALID}
     * 
     * @return the colour currently seen by the colour sensor.
     */
    public int getColour()
    {
        Color col = sensor.getColor();
        ColorMatchResult result = matcher.matchColor(col);
        int forReturn = CPANEL_COLOUR_INVALID;
        if (result.color == AMB_CYAN)
        {
            forReturn = CPANEL_COLOUR_CYAN;
        }
        else if (result.color == AMB_RED)
        {
            forReturn = CPANEL_COLOUR_RED;
        }
        else if (result.color == AMB_YELLOW)
        {
            forReturn = CPANEL_COLOUR_YELLOW;
        }
        else if (result.color == AMB_GREEN)
        {
            forReturn = CPANEL_COLOUR_GREEN;
        }
        return forReturn;
    } 

    /**
     * Reset the colour change counter back to zero.
     */
    public void resetColourChanges()
    {
        myColourChanges= 0;
    }

    /**
     * Returns the number of times that colour change transitions have been detected since the last
     * reset.
     * 
     * @return colour change count
     */
    public int getColourChange()
    {
        return myColourChanges; 
    }

    /**
     * Returns the number of full rotations, this will give values to the closest eighth of a rotation.
     * 
     * @return Full rotations
     */
    public double getControlPanelRotations()
    {
        return (double)myColourChanges / CPANEL_COLOURS_PER_ROTATION;
    }
    

    /**
     * Returns the direction of the last transition detected.
     * 
     * @return direction - false = anti-clockwise
     */
    public boolean getControlPanelDirection()
    {
        return myLastTransitionDir;
    }



    public void runPeriodic()
    {
        int CurrentColour = getColour();
        if (CurrentColour == lastColour)
        {
            scanCount++;
        }
        else
        {
            lastColour = CurrentColour;
            scanCount = 0;
        }

        //update count rotations
        if ((CurrentColour != CPANEL_COLOUR_INVALID) &&
            (CurrentColour != PreviousColour) &&
            (scanCount > 2))
        {
            myColourChanges ++;
            //update direction
            if(PreviousColour == (CurrentColour+1) %4)
            {
                myLastTransitionDir = true;            
            }
            else if ((PreviousColour+1)%4 == CurrentColour)
            {
                myLastTransitionDir = false;
            }
            PreviousColour = CurrentColour; 
        }
    }

    

    /**
     * 
     * @return colour - 1 = red, 2 = yellow, 3 = green, 4 = blue, 0 = no colour/error
     */
    public int getFmsColour()
    {
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0)
        {
            switch (gameData.charAt(0))
            {
                case FMS_CYAN :
                return CPANEL_COLOUR_CYAN;
                case FMS_GREEN :
                return CPANEL_COLOUR_GREEN;
                case FMS_RED :
                return CPANEL_COLOUR_RED;
                case FMS_YELLOW :
                return CPANEL_COLOUR_YELLOW;
                default :
                return CPANEL_COLOUR_INVALID;
            }
        }
        else
        {
            return CPANEL_COLOUR_INVALID;
        }
    }

}

