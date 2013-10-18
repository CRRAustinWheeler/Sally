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
public class Trigger {

    private Solenoid launcherOut, launcherIn;
    private long disableTime = 0;
    private Gate gate;

    public Trigger(Gate gate) {
        launcherIn = new Solenoid(Wiring.solenoidIn);
        launcherOut = new Solenoid(Wiring.solenoidOut);
        this.gate = gate;
    }

    /**
     * true = out
     * false = in
     */
    public boolean setState(boolean b) {
        if (b) {
            disableTime = System.currentTimeMillis() + 30;
        } else if (System.currentTimeMillis() < disableTime) {
            b = true;
        }
        if (!gate.isDown()) {
            b = false;
        }
        if (Sally.dash != null) {
            Sally.dash.streamPacket(b ? 1 : 0, "triggerState");
        }
        launcherIn.set(!b);
        launcherOut.set(b);
        return b;
    }
}
