package frc.sim;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import frc.util.LimitSwitchGroup;
import frc.util.SensoredSystem;
import frc.util.sim.*;
import frc.util.sim.physics.ForceCalcs;
import frc.util.sim.physics.MotorModel;
import frc.robot.subsystems.RobotWrangler;
import frc.robot.subsystems.RobotWrangler.WranglerState;

public class WranglerTest {
    @Test
    public void Test() {
        double drumRadius = 0.03; // metres
        double gearRatio = 10.0; // n : 1
        double transmissionEfficiency = 0.8;
        double mass = 15.0; // kg

        MotorModel neoModel = new MotorModel(3.36, 166, 5880, 12);
        MockEsc motor = new MockEsc(neoModel);
        MockEncoder encoder = new MockEncoder(2048, gearRatio);
        SensoredSystem wranglerSystem = new SensoredSystem(motor, encoder);
        
        MockLimit limit = new MockLimit();
        LimitSwitchGroup limitGroup = new LimitSwitchGroup(limit);

        RobotWrangler wrangler = RobotWrangler.getInstance();
        wrangler.setSystem(wranglerSystem, limitGroup);

        double pos = 0.0;
        double speed = 0.0; // m/s
        double voltage = 12.0;

        double dt = 0.02; // s
        double goal = 4096.0; // encoder counts
        double maxTime = 10.0; // s

        wrangler.setDesiredState(WranglerState.SHOOTIN);
        neoModel.setCurrentLimit(40);

        boolean running = true;
        double time;
        // Physics simulation loop
        for (time = 0.0; time < maxTime && running; time += dt) {
            wrangler.update();

            double motorSpeed = gearRatio * speed / drumRadius;

            // Positive is up, negative down
            double force = transmissionEfficiency * neoModel.getTorque(voltage * motor.get(), motorSpeed) * gearRatio / drumRadius;
            force -= ForceCalcs.addGravity(mass);
            // force += 100; // newtons, e.g. a constant force spring

            double acceleration = force / mass;
            speed += acceleration * dt;
            pos += speed * dt;

            encoder.update(dt, speed / drumRadius); // Assuming encoder is placed on drum, not motor

            System.out.println("Motor Command: " + motor.get() + "    Accel: " + acceleration + "    Motor Speed: " + motorSpeed + "    Speed: " + speed + "    Force: " + force + "    Pos: " + pos + 
            "    Enc: " + encoder.getCounts() + "    Time: " + time);
            
            String[] logData = {Double.toString(time), Double.toString(motor.get()), Double.toString(acceleration), Double.toString(motorSpeed), 
            Double.toString(speed), Double.toString(force), Double.toString(encoder.getCounts())};
        }

        assertEquals(goal, encoder.getCounts(), 10);
    }
}
