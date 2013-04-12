/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderedrobotics;

import com.coderedrobotics.statics.Wiring;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author laptop
 */
public class NewFeeder {

    private Solenoid feederOut, feederIn;

    public NewFeeder() {
        feederIn = new Solenoid(Wiring.feederIn);
        feederOut = new Solenoid(Wiring.feederOut);
    }

    public void up() {
        setState(true);
    }

    public void down() {
        setState(false);
    }

    public void setState(boolean b) {
        feederIn.set(!b);
        feederOut.set(b);
    }
}
