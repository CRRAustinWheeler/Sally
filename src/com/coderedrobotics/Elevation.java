/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderedrobotics;

import com.coderedrobotics.libs.dash.PIDControllerAIAO;
import com.coderedrobotics.statics.Wiring;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author laptop
 */
public class Elevation implements PIDOutput {

    private Talon talon;
    public EncoderShell encoder;
    private DigitalInput limitSwitch;
    private PIDControllerAIAO controller;
    private DriverStationLCD ds;
    private int encoderSetupPosition = -46547;
    private boolean resetMode = false;

    public Elevation() {
        talon = new Talon(Wiring.elevationMotor);
        ds = DriverStationLCD.getInstance();
        encoder = new EncoderShell(new Encoder(
                Wiring.elevationEncoderPortA, Wiring.elevationEncoderPortB,
                false, CounterBase.EncodingType.k2X), 0.5);
        encoder.set(encoderSetupPosition);
        limitSwitch = new DigitalInput(Wiring.elevationLimitSwitch);
        controller = new PIDControllerAIAO(
                0.0015d, 0d, 0.0015d, encoder, this, Sally.dash, "elevation");
        controller.setSetpoint(encoderSetupPosition);
        controller.enable();
    }

    public synchronized void reset() {
        resetMode = true;
        setTalon(1d);
    }

    private void setTalon(double speed) {
        if (!limitSwitch.get()) {
            if (speed < 0d) {
                talon.set(-speed);
            } else {
                talon.set(0d);
            }
        } else {
            talon.set(-speed);
        }
    }

    public synchronized void pidWrite(double output) {
        double result = output;
        if (Math.abs(result) < 0.5d) {
            result = 0;
        }
        if (Sally.dash != null) {
            Sally.dash.streamPacket(encoder.get(), "elevationPosition");
        }
        if (!resetMode) {
            setTalon(result);
        } else {
            if (!limitSwitch.get()) {
                encoder.set(0);
                controller.setSetpoint(encoderSetupPosition);
                resetMode = false;
            }
        }
    }

    public void set(double value) {
        controller.setSetpoint(value);
        sendData();
    }

    public void setRelative(double value) {
        set(value + controller.getSetpoint());
    }

    private void sendData() {
        if (Sally.dash != null) {
            Sally.dash.streamPacket(controller.getSetpoint(), "elevationSetpoint");
        }
        String sp = "" + controller.getSetpoint();
        if (sp.length() > 10) {
            sp = sp.substring(0, 9);
        }
        String pos = "" + encoder.get();
        if (pos.length() > 10) {
            pos = pos.substring(0, 9);
        }
        ds.println(DriverStationLCD.Line.kUser4, 5, "setpoint: " + sp);
        ds.println(DriverStationLCD.Line.kUser5, 5, "position: " + pos);
        ds.updateLCD();
    }

    public boolean isReady() {
        return Math.abs(controller.get()) < 0.6;
    }

    public class EncoderShell implements PIDSource {//make private

        Encoder encoder;
        private double offset;
        private double multiplyer;

        private EncoderShell(Encoder encoder, double multiplyer) {
            this.multiplyer = multiplyer;
            this.encoder = encoder;
            encoder.start();
        }

        private void set(double value) {
            offset = (((double) encoder.getRaw()) * multiplyer) - value;
        }

        private double get() {
            return (encoder.getRaw() * multiplyer) - offset;
        }

        public double pidGet() {
            return get();
        }
    }
}
