// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.util;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.subsystems.Shooter;

/** Add your docs here. */
public class ShooterCounter {

    private static ShooterCounter m_instance;

    public static ShooterCounter getInstance() {
        if (m_instance == null) {
            m_instance = new ShooterCounter();
        }
        return m_instance;
    }

    Timer timer;

    private static double passTime = 0;
    private double time;

    private int ballCount = 0;
    private int totalBallCount = 0;
    /**
     * Tests for a ball being shot by a current spike (beta)
     * @param double threshold
     * @return boolean result
     */
    public boolean getBallPass() {
        time = timer.getFPGATimestamp();
        if(Shooter.getShooterRPM() < Shooter.getShooterTarget() * Constants.kShooterPassDeviation) {
            if((passTime + 0.5) > time) {
                passTime = timer.getFPGATimestamp();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**
     * Checks if a ball passes and computes totals
     */
    public void update() {
        if(getBallPass()) {
            ballCount ++;
            totalBallCount ++;
        }
    }
    /**
     * Resets ballCount
     */
    public void resetBallCount() {
        ballCount = 0;
    }
 
    /**
     * 
     * @return int ballCount
     */
    public int getBallCount() {
        return ballCount;
    }

    /**
     * 
     * @return int totalBallCount
     */
    public int getTotalBallCount() {
        return ballCount;
    }
}
