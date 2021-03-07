package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;



public class LED{
    

    private static LED m_instance;
    int rainbowFirstPixelHue = 100;
    LEDState currentState;
    LEDState desiredState;
    DesiredColour desiredColour;
    AddressableLED led = new AddressableLED(0);

    AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(60);
    int ledLength = 60;

    public static LED getInstance() {
        if(m_instance == null) {
            m_instance = new LED();
        }
        return m_instance;
    }
    public enum LEDState {
        SOLID,
        FLASHING,
        CYCLING,
        IDLE,
    }

    public void ledInit() {
        // PWM port 9
        // Must be a PWM header, not MXP or DIO
        led = new AddressableLED(9);
    
        // Reuse buffer
        // Default to a length of 60, start empty output
        // Length is expensive to set, so only set it once, then just update data
        ledBuffer = new AddressableLEDBuffer(60);
        led.setLength(ledBuffer.getLength());
    
        // Set the data
        led.setData(ledBuffer);
        led.start();
      }

    private LED() {
    }


    public enum DesiredColour {
        YELLOW,
        RED,
        GREEN,
        LIGHTGREEN,
        DARKBLUE,
        BLUE,
        LIGHTBLUE,
        BLACK,
    }



    public void update() {
        switch(desiredState) {
            case SOLID:
            led.start();
            currentState = desiredState;
            break;

            case FLASHING:

            currentState = desiredState;
            break;

            case CYCLING:
            rainbow();
            led.setData(ledBuffer);
            led.start();
            currentState = desiredState;
            break;

            case IDLE:
            setColour(DesiredColour.BLACK);
            led.setData(ledBuffer);
            led.start();
            currentState = desiredState;
            break;
            
            default:
            setColour(DesiredColour.BLACK);
            led.setData(ledBuffer);
            led.start();
            currentState = desiredState;


        }
    }


    public void setDesiredState(LEDState state) {
        desiredState = state;
    }

    public LEDState getCurrentState() {
        return currentState;
    }
    public void setColour(DesiredColour desiredColour){

            switch(desiredColour)
            {
            case YELLOW:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 255, 255, 0);
            }  
            led.setData(ledBuffer);
            break;
            case RED:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 255, 0, 0);
            }
            
            break;
            case GREEN:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 0, 128, 0);
            }  
            led.setData(ledBuffer);
            break;   
            case  LIGHTGREEN:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 0, 255, 0);
            }
            led.setData(ledBuffer);
            break;
            case DARKBLUE:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 0, 0, 139);
            }
            led.setData(ledBuffer);
            break;
            case BLUE:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 0, 0, 255);
            }
            led.setData(ledBuffer);
            break;
            case LIGHTBLUE:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 0, 191, 255);
            }
            led.setData(ledBuffer);
            break;
            case BLACK:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 0, 0, 0);
            }
            led.setData(ledBuffer);
            break;
            }
    }
 private void rainbow() {
    // For every pixel
    for (var i = 0; i < ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (rainbowFirstPixelHue + (i * 180 / ledBuffer.getLength())) % 180;
      // Set the value
      ledBuffer.setHSV(i, hue, 255, 128);
    }
    // Increase by to make the rainbow "move"
    rainbowFirstPixelHue += 3;
    // Check bounds
    rainbowFirstPixelHue %= 180;
  }

    public boolean checkSafeRetraction() {
        /*if(!(indexerFlap.get())) { //FIXME
            return true;
        } else {
            return false;
        } */
        return true;
    }

}