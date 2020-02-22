package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {
    private static Vision mInstance;

    public static Vision getInstance() {
      if (mInstance == null) {
        mInstance = new Vision();
      }
      return mInstance;
    }

    private Vision() {
      setPipeline(0);
    }

    public boolean getTargetAcquired() {
      setPipeline(1);
      final boolean targetAcquired = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getBoolean(false);
      return targetAcquired;
    }

    public double getAngleToTarget() {
      setPipeline(1);
      final double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
      return tx;
    }

    public void disableVision() {
      setPipeline(0);
    }

    private void setPipeline(final int pipelineId) {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipelineId);
    }

    public void blindLuin() {
      setPipeline(1);
    }
}
