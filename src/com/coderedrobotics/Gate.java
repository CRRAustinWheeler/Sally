/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderedrobotics;

import com.coderedrobotics.statics.Wiring;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Servo;

/**
 *
 * @author laptop
 */
public class Gate {

    private Servo servo;
    private boolean isSetToDown;
    private boolean triggerOveride = false;
    private long downTime;
    private DriverStationLCD ds;

    public Gate() {
        servo = new Servo(Wiring.gateServo);
        ds = DriverStationLCD.getInstance();
    }

    public void setState(boolean open) {
        if (triggerOveride) {
            servo.setAngle(120);
            isSetToDown = true;
            return;
        }
        if (open) {
            servo.setAngle(120);
            isSetToDown = false;
        } else {
            servo.setAngle(22);
            if (!isSetToDown) {
                downTime = System.currentTimeMillis() + 380;
            }
            isSetToDown = true;
        }
        if (Sally.dash != null) {
            Sally.dash.streamPacket(open ? 1 : 0, "gateState");
        }
        if (open) {
            ds.println(DriverStationLCD.Line.kUser6, 5, "gate is up     ");
        } else {
            ds.println(DriverStationLCD.Line.kUser6, 5, "gate is down");
        }
        ds.updateLCD();
    }

    public boolean isDown() {
        if (!isSetToDown) {
            return false;
        }
        if (downTime > System.currentTimeMillis()) {
            return false;
        }
        return true;
    }

    void setTriggerOveride(boolean b) {
        triggerOveride = b;
    }
}
