/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util;

/**
 * Add your docs here.
 */
public class JavaUtil {

    private JavaUtil() {

    }

    /**
     * @param double data sample to base calculations off
     * @param double target value
     * @param double tollerence 
     * @return if the data is within the tollerences
     */
    public boolean getWithinTolerance(double data, double target, double tolerance) {
        if(data >= (target - tolerance) && data <= (target + tolerance)) {
            return true;
        } else {
            return false;
        }
    }

    private static JavaUtil m_instance;

    public static JavaUtil getInstance() {
        if (m_instance == null) {
            m_instance = new JavaUtil();
        }

        return m_instance;
    }
}
