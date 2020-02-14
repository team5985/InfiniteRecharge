package frc.util;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;



import frc.robot.Constants;


public class ColourSensor {
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch colorMatcher = new ColorMatch();

    private int oldColour = 0;
    private int newColour = 1;
    private double panelRotations = 0;
    private int colourChanges = 0;

    colorMatcher.addColorMatch(kBlueTarget);
    colorMatcher.addColorMatch(kGreenTarget);
    colorMatcher.addColorMatch(kRedTarget);
    colorMatcher.addColorMatch(kYellowTarget); 

    String gameData = DriverStation.getInstance().getGameSpecificMessage();



    /**
     * 
     * @return colour - 1 = red, 2 = yellow, 3 = green, 4 = blue, 0 = no colour/error
     */
    public int getColour() {
        Color detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        if(match.color == Constants.kRedTarget) {
            return 1;
        } else if(match.color == Constants.kYellowTarget) {
            return 2;
        } else if(match.color == Constants.kGreenTarget) {
            return 3;
        } else if(match.color == Constants.kBlueTarget) {
            return 4;
        } else {
            return 0;
        }

    }

    /**
     * 
     * @return colour changes
     */
    public int getColourChange() { 
        if(oldColour == newColour) {
            
        } else if(oldColour != newColour) {
            colourChanges++;
        }
        
        return colourChanges - 1 + Constants.kSensorOffset; //Put changes here 
    }

    public void resetColourChange() {
        colourChanges = 0;
    }

    /**
     * @return Full rotations
     */
    public double getControlPanelRotations() {
        return colourChanges/8; //Rotations
    }
    

    /**
     * 
     * @return direction - false = anti-clockwise
     */
    public boolean getControlPanelDirection() {
        return false; //Put direction the wheel is traveling in
    }
    
    /**
     * 
     * @return colour - 1 = red, 2 = yellow, 3 = green, 4 = blue, 0 = no colour/error
     */

     public int getPositionControlColourChanges() {
          if(getColourChange() + Constants.kSensorOffset > 4 && getColourChange() + Constants.kSensorOffset < 1){
              return getColourChange();
          } else if(getColourChange() + Constants.kSensorOffset > 4) {
              return getColourChange() - 4;
          } else {
              return getColourChange() + 4;
          }
     }
    public int getFmsColour() {
        if(gameData.length() > 0) {
            switch(gameData.charAt(0)) {
                case 'R':
                    return 1;
                break;
                case 'Y': 
                    return 2;
                break;
                case 'G':
                    return 3;
                break;
                case 'B':
                    return 4;
                break;
                default:
                    return 0;
                break;
                }
                
            }else {
                return 0;
            }
        }
    }


