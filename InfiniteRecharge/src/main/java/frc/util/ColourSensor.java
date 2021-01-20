package frc.util;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorSensorV3.RawColor;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.command.PrintCommand;
import frc.robot.Constants;
public class ColourSensor
{   

    public Color getColourValue()
    {
        return sensor.getColor();
    }

    private double getRed()
    {
        return getColourValue().red;
    }

    private double getGreen()
    {
        return getColourValue().green;
    }

    private double getBlue()
    {
        return getColourValue().blue;
    }

    /**
     * The previous colour lastColour.
     */
    private int PreviousColour = Constants.kControlPanelColourInvalid;

    /** 
     * The colour since the last scan of the colour sensor.
     */
    private int lastColour = Constants.kControlPanelColourInvalid;

    /**
     * A count of how many times the sensor has scammed the same colour
     */
    private int scanCount = 0;
 /**
     * The red value in the current colour
     */
    public double ColourSensorR = 0;
 /**
     * The green value in the current colour
     */
    public double ColourSensorG = 0;
 /**
     * The blue value in the current colour
     */
    public double ColourSensorB = 0;

    /**
     * The singleton instance of this class.
     */
    private static ColourSensor theInstance = null;
    
    /**
     * The I2C colour sensor on the robot.
     */
    private ColorSensorV3 sensor;

    Color col;
    ColorMatchResult result;

    /**
     * The class that compares the observed colours with the list of colours that we are expecting.
     */
    private ColorMatch matcher = new ColorMatch();

    /**
     * The number of colour change transitions that have been counted since this was last reset.
     */
    private static int myColourChanges = 0;

	public static Object colorMatcher;

	

    /**
     * The direction of rotation for the last transition that was detected.
     * <code>true</code> is AntiClockwise. <code>false</code> is Clockwise.
     */
    private boolean myLastTransitionDir = false;

    /**
     * Constructor for this class.
     */
    private ColourSensor() {
        I2C.Port i2cPort = I2C.Port.kOnboard;
        ColorSensorV3 sensor = new ColorSensorV3(i2cPort);

        ColorMatch matcher = new ColorMatch();
        matcher.addColorMatch(Constants.kAMB_CYAN);
        matcher.addColorMatch(Constants.kAMB_RED);
        matcher.addColorMatch(Constants.kAMB_GREEN);
        matcher.addColorMatch(Constants.kAMB_YELLOW);
        matcher.setConfidenceThreshold(0.8);

       col = Constants.kColInvalid;    }

    /**
     * Creates (if necessary) and returns the one and only instance of the
     * Colour Sensor class.
     * 
     * @return the instance of {@link ColourSensor}
     */
    public static synchronized ColourSensor getInstance() {
        if (theInstance == null) {
            theInstance = new ColourSensor();
        }
        return theInstance;
    }

    /**
     * Gets the colour seen by the colour sensor. Returns one of...
     * {@link #kControlPanelColourBlue} {@link #kControlPanelColourGreen}
     * {@link #kControlPanelColourRed} {@link #CkControlPanelColourYellow}
     * {@link #kControlPanelColourInvalid}
     * 
     * @return the colour currently seen by the colour sensor.
     */
    public int getColour() {
        try {
            col = sensor.getColor();
            ColorMatchResult result = matcher.matchClosestColor(col);
        } catch(Exception e) {
            col = Constants.kColInvalid;
            System.out.println("Invalid colour sensor data");
        }
        int forReturn = Constants.kControlPanelColourInvalid;
        if (result == null) {
            forReturn = Constants.kControlPanelColourInvalid;
        } else if (result.color == null) {
            forReturn = Constants.kControlPanelColourInvalid;
        } else if (result.color == Constants.kAMB_CYAN) {
            forReturn = Constants.kControlPanelColourBlue;
        } else if (result.color == Constants.kAMB_RED) {
            forReturn = Constants.kControlPanelColourRed;
        } else if (result.color == Constants.kAMB_YELLOW) {
            forReturn = Constants.kControlPanelColourYellow;
        } else if (result.color == Constants.kAMB_GREEN) {
            forReturn = Constants.kControlPanelColourGreen;
        }
        return forReturn;
    }

    /**
     * Gets the colour seen by the colour sensor. Returns one of...
     * {@link #kControlPanelColourBlue} {@link #kControlPanelColourGreen}
     * {@link #kControlPanelColourRed} {@link #CkControlPanelColourYellow}
     * {@link #kControlPanelColourInvalid}
     * 
     * @return the colour currently seen by the colour sensor.
     */
    public String getColourString() {
        return getColourString(getColour());
    }
    public String getColourString(int aColour){
        if (aColour == Constants.kControlPanelColourBlue)
            return Constants.kNAME_CYAN;
        if (aColour == Constants.kControlPanelColourGreen)
            return Constants.kNAME_GREEN;
        if (aColour == Constants.kControlPanelColourRed)
            return Constants.kNAME_RED;
        if (aColour == Constants.kControlPanelColourYellow)
            return Constants.kNAME_YELLOW;
        return Constants.kNAME_UNKNOWN;
    }


    /**
     * Reset the colour change counter back to zero.
     */
    public void resetColourChanges() {
        myColourChanges = 0;
    }

    /**
     * Returns the number of times that colour change transitions have been detected
     * since the last reset.
     * 
     * @return colour change count
     */
    public int getColourChange() {
        return myColourChanges;
    }

    /**
     * Returns the number of full rotations, this will give values to the closest
     * eighth of a rotation.
     * 
     * @return Full rotations
     */
    public double getControlPanelRotations() {
        return (double) myColourChanges / Constants.kCPANEL_COLOURS_PER_ROTATION;


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



    public void update()
    {
        
        ColourSensorR = getRed();
        ColourSensorG = getGreen();
        ColourSensorB = getBlue();
        
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
        if(CurrentColour == Constants.kControlPanelColourInvalid){
            CurrentColour = PreviousColour;
        }     
       //update count rotations
        if ((CurrentColour != Constants.kControlPanelColourInvalid) &&
            (CurrentColour != PreviousColour) &&
            (scanCount > 3))
        {
            myColourChanges ++;
            //update direction
            if(PreviousColour == (CurrentColour+1) %4)
            {
                myLastTransitionDir = false;            
            }
            else if ((PreviousColour+1)%4 == CurrentColour)
            {
                myLastTransitionDir = true;
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
                case Constants.kFMS_CYAN :
                return Constants.kControlPanelColourBlue;
                case Constants.kFMS_GREEN :
                return Constants.kControlPanelColourGreen;
                case Constants.kFMS_RED :
                return Constants.kControlPanelColourRed;
                case Constants.kFMS_YELLOW :
                return Constants.kControlPanelColourYellow;
                default :
                return Constants.kControlPanelColourInvalid;
            }
        }
        else
        {
            return Constants.kControlPanelColourInvalid;

        }
    }

}
