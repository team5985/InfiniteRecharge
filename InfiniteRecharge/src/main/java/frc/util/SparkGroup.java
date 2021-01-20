// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.util;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;
import java.util.Arrays;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

/** Allows multiple {@link SpeedController} objects to be linked together. */
public class SparkGroup implements SpeedController, Sendable, AutoCloseable {
  private boolean m_isInverted;
  private final CANSparkMax[] m_speedControllers;
  private static int instances;

  /**
   * Create a new SpeedControllerGroup with the provided SpeedControllers.
   *
   * @param speedControllers The SpeedControllers to add
   */
  @SuppressWarnings("PMD.AvoidArrayLoops")
  public SparkGroup(
      CANSparkMax speedController, CANSparkMax... speedControllers) {
    m_speedControllers = new CANSparkMax[speedControllers.length + 1];
    m_speedControllers[0] = speedController;
    for (int i = 0; i < speedControllers.length; i++) {
      m_speedControllers[i + 1] = speedControllers[i];
    }
    init();
  }

  public SparkGroup(CANSparkMax[] speedControllers) {
    m_speedControllers = Arrays.copyOf(speedControllers, speedControllers.length);
    init();
  }

  private void init() {
    for (CANSparkMax controller : m_speedControllers) {
      SendableRegistry.addChild(this, controller);
    }
    instances++;
    SendableRegistry.addLW(this, "SpeedControllerGroup", instances);
  }

  @Override
  public void close() {
    SendableRegistry.remove(this);
  }

  @Override
  public void set(double speed) {
    for (CANSparkMax speedController : m_speedControllers) {
      speedController.set(m_isInverted ? -speed : speed);
    }
  }

  @Override
  public double get() {
    if (m_speedControllers.length > 0) {
      return m_speedControllers[0].get() * (m_isInverted ? -1 : 1);
    }
    return 0.0;
  }

  @Override
  public void setInverted(boolean isInverted) {
    m_isInverted = isInverted;
  }

  @Override
  public boolean getInverted() {
    return m_isInverted;
  }

  @Override
  public void disable() {
    for (CANSparkMax speedController : m_speedControllers) {
      speedController.disable();
    }
  }

  @Override
  public void stopMotor() {
    for (CANSparkMax speedController : m_speedControllers) {
      speedController.stopMotor();
    }
  }

  @Override
  public void pidWrite(double output) {
    set(output);
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Speed Controller");
    builder.setActuator(true);
    builder.setSafeState(this::stopMotor);
    builder.addDoubleProperty("Value", this::get, this::set);
  }

  public void setIdleMode(IdleMode mode) {
    for (CANSparkMax speedController : m_speedControllers) {
      speedController.setIdleMode(mode);
    }
  }

  public IdleMode getIdleMode() {
    for (CANSparkMax speedController : m_speedControllers) {
      return speedController.getIdleMode();
    }
    return null;
  }
}
