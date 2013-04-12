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
public class Ejector {

    private Solenoid ejectorOut, ejectorIn;

    public Ejector() {
        ejectorIn = new Solenoid(Wiring.ejectorIn);
        ejectorOut = new Solenoid(Wiring.ejectorOut);
    }

    public void out() {
        setState(true);
    }

    public void in() {
        setState(false);
    }

    public void setState(boolean b) {
        if (Sally.dash != null) {
            Sally.dash.streamPacket(b ? 1 : 0, "ejectorState");
        }
        ejectorIn.set(!b);
        ejectorOut.set(b);
    }
}
