package frc.util;

import edu.wpi.first.wpilibj.I2C;

public class UltrasonicI2C
{
    /**
     * The I2C reference to the Ultrasonic sensor.
     */
    final private I2C myDevice;

    /**
     * The last measurement counter from the device.
     * Subsequent messages with the same counter are just repeating data and
     * do not represent a new measurement.
     */
    private byte myLastCounter;

    /**
     * The speed of sound in metres/second;
     */
    final private static float SPEED_OF_SOUND = 343;

    /**
     * The frequency of the clock measuring the sound transit time.
     * Version 1.0 boards with the ATTiny2313 CPU are at 0.5MHz
     * Version 2.0 boards with the ATTiny214 CPU are at 5/6 MHz.
     */
    //final private static int TIMER_FREQ = 500000;
    final private static int TIMER_FREQ = 833333;

    /**
     * The length in bytes of the message to read from the device.
     */
    final private static int MESSAGE_LENGTH = 4;

    public int myCountReads = 0;
    public int myCountBadCRC = 0;
    public int myCountBadMeas = 0;
    public int myCountNew = 0;


    /**
     * Creates an instance of the Ultrasonic Sensor.
     * <pre>
     * Example instantiation is...
     * <code>I2C.Port i2cp = I2C.Port.kOnboard;
     * I2C usLink = new I2C(i2cp, 0x13);
     * UltrasonicI2C usi2c = new UltrasonicI2C(usLink);</code>
     * </pre>
     * @param aDevice The I2C device for the UltraSonic sensor.
     */
    public UltrasonicI2C(I2C aDevice)
    {
        myDevice = aDevice;
    }
    private usResults myResults;
    public void update()
    {
        myResults = readSensor();
    }
    public usResults getResults()
    {
        return myResults;
    }

    private usResults readSensor()
    {
        myCountReads++;
        byte[] data = new byte[MESSAGE_LENGTH];
        myDevice.read(0x00, MESSAGE_LENGTH, data);
        byte checksum = (byte)(data[0] ^ data[1] ^ data[2] ^ data[3]);
        // System.out.println("Data - " + data[0] + ", " + data[1] + ", " + data[2] + ", " + data[3]);
        if (checksum != 0)
        {
            myCountBadCRC++;
            return new usResults(0, false, false);
        }
        int counts = (data[1]&0xFF) + (data[2]&0xff) * 256;
        // If no return pulse was detected in time then invalid reading.
        // This usually occurs if nothing is in the sensor's range.
        if (counts == 0)
        {
            myCountBadMeas++;
            return new usResults(0, false, false);
        }
        double dist = (0.5 * counts / TIMER_FREQ) * SPEED_OF_SOUND * 1000;
        boolean newRead = false;
        if (data[0] != myLastCounter)
        {
            newRead = true;
            myCountNew++;
        }
        myLastCounter = data[0];

        //System.out.println("Counts = " + counts + " - Dist = " + dist);
        return new usResults(dist, newRead, true);
    }

    public class usResults
    {
        private final double myResult;
        private final boolean myNew;
        private final boolean myValid;

        private usResults(double aResult, boolean aNew, boolean aValid)
        {
            myResult = aResult;
            myNew = aNew;
            myValid = aValid;
        }

        /**
         * @return The distance measured in millimetres.
         */
        public double getResult()
        {
            return myResult;
        }

        /**
         * @return <code>true</code> if this represents a new measurement,
         * Otherwise the data is simply another copy of the previous
         * measurement.
         */
        public boolean getNew()
        {
            return myNew;
        }

        /**
         * @return <code>true</code> if this represents a valid reading.
         * Having nothing within 4 metres of the sensor is one possible cause
         * of an invalid reading.
         * Message corruption (invalid checksum) will also cause an invalid
         * reading.
         */
        public boolean getValid()
        {
            return myValid;
        }
    }
}