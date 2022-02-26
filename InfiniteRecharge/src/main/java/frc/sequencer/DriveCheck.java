package frc.sequencer;

import frc.robot.Constants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.subsystems.Drive;
public class DriveCheck extends SequenceTransition implements SequenceStepIf
{
    Robot myRobot;
    boolean myDebug = false;
    WPI_TalonFX m_talon;
    WPI_TalonFX other_talon;
    int scancount;
    int errorStatus;
    PowerDistributionPanel m_PDP;
    int m_controllerChannel;
    int faultScanCount;
    boolean faultOnLastScan;
    String errorPresent;
    String motorName;
    boolean error;
    public DriveCheck(WPI_TalonFX talon,PowerDistributionPanel pdp,int controllerChannel, WPI_TalonFX enc_talon,String motorRef)
    {       
    m_talon = talon;
    m_PDP = pdp;
    m_controllerChannel = controllerChannel; 
    other_talon = enc_talon;
    motorName = motorRef;
    }

    public void transStart()
    {
        scancount = 0;
        faultScanCount = 0;
        faultOnLastScan = false;
        error = false;
        errorPresent = "Untested";
        errorStatus = 0;
    }

    /**
     * Called every scan of the sequencer. This allows the transition class to monitor
     * values over time.
     * This method must calculate and return whether the step has completed.
     * @return <code>true</code> if the step has completed.
     */
    public boolean transUpdate()
    {
        return isTransComplete();
    }

    /**
     * Returns whether the step has completed.
     * 
     * @return <code>true</code> if the step has completed.
     */
    public boolean isTransComplete   ()
    {
        if (error = true && faultScanCount > faultScanCountMinimum || scancount > 250)
        {
            return true;
        }
        // Need to fix when we get a robot with encoders.
        return false;
    }


    /**
     * Called when the step is started.
     * This can be used to save the starting state for steps where a delta value
     * is used (eg, a step to move 2 metres will need to remember where we started
     * from in order to calculate how far we have moved).
     */
    public void stepStart()
    {
    }

    /**
     * Called when the step is ended.
     * This can be used to stop the motion of this step (eg, turn motors off, etc)
     */
    public void stepEnd()
    {
    m_talon.set(0);
    }

    /**
     * Called every scan of the sequencer. This allows the step class to monitor
     * values over time.
     */
    public void stepUpdate(){
        errorStatus = talonFXMotorCheck();
        m_talon.set(0.3);
        if(errorStatus == 2){
            error = true;
            errorPresent = "There seems to be a critical error with the motor in the port " + motorName;
        } else if(errorStatus == 1){
            error = true;
            errorPresent = "There seems to be a small issue flagged with the motor in port " + motorName;
        }else{
            error = false;
            errorPresent = "against all odds everything seems to be fine with " + motorName;
        }

    }


    public String stepName()
    {
        return "DriveCheck";
    }

    public void setDebug(boolean aDebug)
    {
        myDebug = aDebug;
    }
    
public int talonFXMotorCheck(){
    
    if(Math.abs(m_talon.get()) >= kMinimumSpeedForCheck){
        if(scancount >= 15){
            int maxError = 0;
            maxError = Math.max(maxError, checkControllerComms());
            maxError = Math.max(maxError, checkControllerError());
            maxError = Math.max(maxError, checkControllerBusVoltage());
            maxError = Math.max(maxError, checkEncoderRotations());
            maxError = Math.max(maxError, checkMotorCurrent());
            maxError = Math.max(maxError, checkPDPCurrent());
            maxError = Math.max(maxError, checkMotorTemperature());
            maxError = Math.max(maxError, checkCorrelatedEncoder());
            if(maxError == 2)
            {
                if(faultScanCount >= faultScanCountMinimum){
                    return 2;
                }
                else if(faultScanCount > 0 && faultScanCount < faultScanCountMinimum && faultOnLastScan == true){
                    faultScanCount++;
                    return 0;
                }
                else if(faultScanCount > 0 && faultOnLastScan == false){
                    faultScanCount = 0;
                    return 0;
                }
                else{
                faultScanCount++;
                faultOnLastScan = true;
                return 0;
                }
            }
            else if(maxError ==1)
            {
                if(faultScanCount >= faultScanCountMinimum){
                    return 1;
                }
                else if(faultScanCount > 0 && faultScanCount < faultScanCountMinimum && faultOnLastScan == true){
                    faultScanCount++;
                    return 0;
                }
                else if(faultScanCount > 0 && faultOnLastScan == false){
                    faultScanCount = 0;
                    return 0;
                }
                else{
                faultScanCount++;
                faultOnLastScan = true;
                return 0;
                }
            }
            else
            {
            return 0;
            }
        }
        else{
        return 0;
        }
    }
    else{
    return 0;
    scancount++;
    }
    }
    
    private int checkControllerComms() {
        if (m_talon.getDeviceID() < 0) {
            return 2;
        } else {
            return 0;
        }
    }
    private int checkCorrelatedEncoder(){
        if(Math.abs(other_talon.getSelectedSensorVelocity()) < 0.05){
            return 2;
        } else{
            return 0;
        }
    }
//FIXME
    private int checkControllerError() {
        Faults faults = null;
        m_talon.getFaults(faults);
        if (faults.hasAnyFault()) {
            return 2;
        } else {
            return 0;
        }

    }

    private int checkControllerBusVoltage() {
        if (m_talon.getBusVoltage() < kControllerBusVoltageShutdown) {
            return 2;
        } else if (m_talon.getBusVoltage() < kControllerBusVoltageWarning) {
            return 1;
        } else {
            return 0;
        }
    }
    //FIXME
    // MAKE SURE TO TEST
    private int checkMotorCurrent() {
        if (m_talon.getSupplyCurrent() < kMotorOutputCurrentShutdown) {
            return 2;
        } else if (m_talon.getSupplyCurrent() < kMotorOutputCurrentWarning) {
            return 1;
        } else {
            return 0;
        }

    } 
    //FIXME
    private int checkPDPCurrent() {
        if (m_PDP.getCurrent(m_controllerChannel) < kPDPOutputCurrentShutdown) {
            return 2;
        } else if (m_PDP.getCurrent(m_controllerChannel) < kPDPOutputCurrentWarning) {
            return 1;
        } else {
            return 0;
        }
    }
    private int checkEncoderRotations() {
        if (Math.sqrt(Math.abs(m_talon.getSelectedSensorVelocity())) <= kEncoderVelocityShutdown) {
            return 2;
        } else if (Math.abs(m_talon.get()) <= kEncoderVelocityWarning && Math.abs(m_talon.getSelectedSensorVelocity()) > kEncoderVelocityShutdown) {
            return 1;
        } else {
            return 0;
        }
    }

    public int checkMotorTemperature() {
        if (m_talon.getTemperature() >= kMotorTemperatureShutdown) {
            return 2;
        } else if (m_talon.getTemperature() >= kMotorTemperatureWarning) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getErrorInt(){
        return errorStatus;
    }
    public Boolean getErrorBool(){
        return error;
    }
    public String getErrorString(){
        return errorPresent;
    }

    private final int kMotorTemperatureShutdown = 85;
    private final int kMotorTemperatureWarning = 70;
    private final int kPDPOutputCurrentShutdown = 1;
    private final int kPDPOutputCurrentWarning = 2;
    private final int kMotorOutputCurrentShutdown = 20;
    private final int kMotorOutputCurrentWarning = 25;
    private final int kControllerBusVoltageShutdown = 10;
    private final int kControllerBusVoltageWarning = 12;
    private final int kEncoderVelocityShutdown = 1;
    private final int kEncoderVelocityWarning = 10;
    private final double kMinimumSpeedForCheck = 0.1;
    private final int faultScanCountMinimum = 50;
}