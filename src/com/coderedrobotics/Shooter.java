package com.coderedrobotics;

/**
 * Class which controls the firing mechanism on the shooter.
 *
 * @author dvanvoorst
 */
public class Shooter {

    public Trigger trigger;
    public Elevation elevation;
    public Launcher launcher;
    public Ejector ejector;
    public Gate gate;
    private boolean readyLastTime = false;
    public static final Preset FULL_2PT_SHOT =
            new Preset(-20056, 11.2, 14, 0.4, 1.4, false);
    public static final Preset FULL_2PT_SHOT_ALT =
            new Preset(-20056, 22, 22, 5, 0, false);
    public static final Preset HALF_3PT_SHOT =
            new Preset(0, 0, 0, 0, 0, true);
    public static final Preset FRONT_PYRAMID_3PT_SHOT =
            new Preset(0, 10, 10, 0, 0, true);
    public static final Preset AUTON =
            new Preset(-46547, 9.6, 12.4, 0.4, 1.0, true);//1.5, 1.5
    public static final Preset FULL_3PT_SHOT =
            new Preset(0, 13.2, 13.4, 2.3, 0.8, true);
    public static final Preset MIDDLE_AUTON =
            new Preset(-56672, 7.2, 10.0, 0.4, 1.0, true);//1.5, 1.5
    public static final Preset NEW_FULL_3PT_SHOT =
            new Preset(-23810, 14.2, 14.2, 0, 0, true);

    public Shooter() {
        gate = new Gate();
        trigger = new Trigger(gate);
        elevation = new Elevation();
        launcher = new Launcher();
        ejector = new Ejector();
    }

    public boolean fireIfReady() {
        boolean b = trigger.setState(elevation.isReady() && launcher.isReady());
        gate.setTriggerOveride(b);
        return b;
    }

    public void resetTriggerOveride() {
        gate.setTriggerOveride(false);
    }

    public void applyPreset(Preset preset) {
        elevation.set(preset.elevation);
        launcher.setSpeedIndependent(preset.first, preset.second);
    }

    public static class Preset {

        private double elevation, first, second, np, nd;
        private boolean isThreePoint;

        private Preset(double elevation, double first, double second,
                double np, double nd, boolean threePoint) {
            this.elevation = elevation;
            this.first = first;
            this.second = second;
            this.np = np;
            this.nd = nd;
            this.isThreePoint = threePoint;
        }
    }
}
