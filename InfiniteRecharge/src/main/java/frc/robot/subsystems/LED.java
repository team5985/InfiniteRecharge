package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;



public class LED{
    

    private static LED m_instance;
    int rainbowFirstPixelHue;
    LEDState currentState;
    LEDState desiredState;
    DesiredColour desiredColour;
    AddressableLED led;
    AddressableLEDBuffer ledBuffer;
    int ledLength;
    public enum LEDState {
        SOLID,
        FLASHING,
        CYCLING,
        IDLE,
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

    public LED(int ledLENGTH, int port, int hue) {
        currentState = LEDState.IDLE;
        desiredState = LEDState.IDLE;
        led = new AddressableLED(port);
        ledLength = ledLENGTH;
        ledBuffer = new AddressableLEDBuffer(ledLength);
        led.setLength(ledBuffer.getLength());
        rainbowFirstPixelHue = hue;
       }


    public void update() {
        switch(desiredState) {
            case SOLID:
            led.setData(ledBuffer);
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
            break;   
            case  LIGHTGREEN:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 0, 255, 0);
            }
            break;
            case DARKBLUE:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 0, 0, 139);
            }
            break;
            case BLUE:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 0, 0, 255);
            }
            break;
            case LIGHTBLUE:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 0, 191, 255);
            }
            break;
            case BLACK:
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                ledBuffer.setRGB(i, 0, 0, 0);
            }
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
