package com.coderedrobotics;

import com.coderedrobotics.statics.Wiring;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;

public class Drive implements PIDOutput {

    Jaguar leftFront;
    Jaguar rightFront;
    Jaguar leftRear;
    Jaguar rightRear;
    boolean auto = false;
    double x, y, rot;

    public Drive() {
        leftFront = new Jaguar(Wiring.jaguarFrontLeftPort);
        rightFront = new Jaguar(Wiring.jaguarFrontRightPort);
        leftRear = new Jaguar(Wiring.jaguarBackLeftPort);
        rightRear = new Jaguar(Wiring.jaguarBackRightPort);
    }

    public void disableAuto() {
        auto = false;
    }

    public void Move(double x, double rot, double y) {
        if (!auto) {
            this.rot = rot;
        }
        this.x = x;
        this.y = y;
        refresh();
    }

    public void pidWrite(double output) {
        if (auto) {
            rot = output;
            refresh();
        }
    }

    private synchronized void refresh() {
        double fr, fl, br, bl;
        double max;

        fl = y;
        fr = -y;
        bl = -y;
        br = y;

        fl += x;
        fr += x;
        bl -= x;
        br -= x;

        fl += rot;
        fr += rot;
        bl += rot;
        br += rot;

        max = Math.max(Math.max(Math.abs(fl), Math.abs(fr)), Math.max(Math.abs(br), Math.abs(bl)));

        if (max > 1) {
            fl = fl / max;
            fr = fr / max;
            bl = bl / max;
            br = br / max;
        }
        leftFront.set(fl);
        rightFront.set(fr);
        leftRear.set(br);
        rightRear.set(bl);
    }
}
