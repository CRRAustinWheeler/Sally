/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderedrobotics;

import com.coderedrobotics.statics.Wiring;
import edu.wpi.first.wpilibj.Servo;

/**
 *
 * @author Derek
 */
public class Monocle {

    Servo servo;

    public Monocle() {
        servo = new Servo(Wiring.monicleServo);
        setEnabled(true);
    }

    public void setEnabled(boolean state) {
        servo.set(state ? 1.0 : 0.5);
    }
}