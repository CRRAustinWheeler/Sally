/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderedrobotics;

import com.coderedrobotics.statics.Wiring;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author laptop
 */
public class Feeder {

    private Talon talon;
    private DigitalInput feederLimit;

    public Feeder() {
        talon = new Talon(Wiring.feederTilt);
        feederLimit = new DigitalInput(Wiring.flipperLimitSwitch);
    }

    public void setSpeed(double speed) {
        if (speed > 0) {
            if (speed > 1) {
                speed = 1;
            } 
            talon.set(-speed);
        } else {
            if (feederLimit.get()) {
                if (speed < -1) {
                    speed = -1;
                }
              talon.set(-speed);
            } else {
                talon.set(0);
            }
        }
    }
}
