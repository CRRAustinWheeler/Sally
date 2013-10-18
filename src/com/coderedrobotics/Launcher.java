/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderedrobotics;

import com.coderedrobotics.libs.Filter;
import com.coderedrobotics.libs.PIDIntegralCalculator;
import com.coderedrobotics.libs.SpeedEncoderShell;
import com.coderedrobotics.libs.dash.PIDControllerAIAO;
import com.coderedrobotics.statics.Wiring;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author laptop
 */
public class Launcher implements Runnable {

    private PIDControllerAIAO firstPID, secondPID;
    private Filter firstFilter, secondFilter;
    private Talon secondMotor, firstMotor;
    private SpeedEncoderShell firstEncoder, secondEncoder;
    private PIDIntegralCalculator firstPIDIC, secondPIDIC;
    private DriverStationLCD ds;
    private Thread thread;
    private long time;

    public Launcher() {
        firstMotor = new Talon(Wiring.shootingTalon1);
        secondMotor = new Talon(Wiring.shootingTalon2);
        ds = DriverStationLCD.getInstance();
        firstEncoder = new SpeedEncoderShell(new Encoder(
                Wiring.launcherEncoder1PortA, Wiring.launcherEncoder1PortB,
                false, Encoder.EncodingType.k2X));
        secondEncoder = new SpeedEncoderShell(new Encoder(
                Wiring.launcherEncoder2PortA, Wiring.launcherEncoder2PortB,
                false, Encoder.EncodingType.k2X));
        firstPIDIC = new PIDIntegralCalculator(firstMotor, -1d, 1d);
        secondPIDIC = new PIDIntegralCalculator(secondMotor, -1d, 1d);
        firstPID = new PIDControllerAIAO(1.5, 0, 1.5, firstEncoder, firstPIDIC, 0.05, Sally.dash, "first");// old p:0.5 d:1.4
        secondPID = new PIDControllerAIAO(1.5, 0, 1.5, secondEncoder, secondPIDIC, 0.05, Sally.dash, "second");
        firstFilter = new Filter(0.6);
        secondFilter = new Filter(0.6);//0.9
        firstPID.setSetpoint(0);
        secondPID.setSetpoint(0);
        thread = new Thread(this);
        thread.start();
    }

    public void enable() {
        firstPID.enable();
        secondPID.enable();
        DriverStation.getInstance().setDigitalOut(1, true);
    }

    public void disable() {
        firstPID.reset();
        secondPID.reset();
        firstPIDIC.reset();
        secondPIDIC.reset();
        DriverStation.getInstance().setDigitalOut(1, false);
    }

    public void setSpeed(double speed) {
        setSpeedIndependent(speed, speed);
    }

    public void setSpeedIndependent(double first, double second) {
        firstPID.setSetpoint(first);
        secondPID.setSetpoint(second);
    }

    public void setSpeedRelative(double speed) {
        firstPID.setSetpoint(firstPID.getSetpoint() + speed);
        secondPID.setSetpoint(secondPID.getSetpoint() + speed);
    }

//    public void sendInfo() {
//        String lineA = "first:  " + setLength("" + firstPID.getSetpoint(), 5)
//                + "  " + setLength("" + (firstPID.getSetpoint() - firstPID.getError()), 5);
//        String lineB = "second: " + setLength("" + secondPID.getSetpoint(), 5)
//                + "  " + (new Double(secondPID.getSetpoint() - secondPID.getError()).toString());
//        Sally.dash.streamPacket(firstPID.getSetpoint() - firstPID.getError(), "firstSpeed");
//        Sally.dash.streamPacket(secondPID.getSetpoint() - secondPID.getError(), "secondSpeed");
//        ds.println(DriverStationLCD.Line.kUser2, 5, lineA);
//        ds.println(DriverStationLCD.Line.kUser3, 5, lineB);
//        ds.updateLCD();
//    }
    private String setLength(String string, int length) {
        if (string.length() >= length) {
            string = string.substring(0, length);
        } else {
            while (length > string.length()) {
                string = string + " ";
            }
        }
        return string;
    }

    public boolean isReady() {
        if (firstFilter.get() < 0.12
                && secondFilter.get() < 0.12
                && firstPID.getSetpoint() > 6) {
            if (time + 200 < System.currentTimeMillis()) {
                return true;
            }
        } else {
            time = System.currentTimeMillis();
        }
        return false;
    }

    boolean isEnabled() {
        return firstPID.isEnable();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            //diplay in message box
            String lineA = "first:  " + setLength("" + firstPID.getSetpoint(), 5)
                    + "  " + setLength("" + (firstPID.getSetpoint() - firstPID.getError()), 5);
            String lineB = "second: " + setLength("" + secondPID.getSetpoint(), 5)
                    + "  " + (new Double(secondPID.getSetpoint() - secondPID.getError()).toString());
            ds.println(DriverStationLCD.Line.kUser2, 5, lineA);
            ds.println(DriverStationLCD.Line.kUser3, 5, lineB);
            ds.updateLCD();
            //filter error
            firstFilter.filter(Math.abs(firstPID.getError()));
            secondFilter.filter(Math.abs(secondPID.getError()));
            //stream speed, error, setpoint, threshold, filteredError
            if (Sally.dash != null) {
                Sally.dash.streamPacket(
                        firstPID.getSetpoint() - firstPID.getError(),
                        "firstSpeed");
                Sally.dash.streamPacket(
                        secondPID.getSetpoint() - secondPID.getError(),
                        "secondSpeed");
                Sally.dash.streamPacket(firstPID.getError(), "firstError");
                Sally.dash.streamPacket(secondPID.getError(), "secondError");
                Sally.dash.streamPacket(firstPID.getSetpoint(), "firstSetpoint");
                Sally.dash.streamPacket(secondPID.getSetpoint(), "secondSetpoint");
                Sally.dash.streamPacket(firstFilter.get(), "firstFilteredError");
                Sally.dash.streamPacket(secondFilter.get(), "secondFilteredError");
                Sally.dash.streamPacket(0.12 + Sally.dash.getRegister("shooterThreshold"), "threshold");
            }
        }
    }
}
