package com.coderedrobotics.statics;

import com.coderedrobotics.libs.Gamepad;
import com.coderedrobotics.libs.GenericJoystick;
import com.coderedrobotics.libs.GenericJoystick.ButtonState;
import com.coderedrobotics.libs.SaitekJoystick;
import com.sun.squawk.util.MathUtils;

/**
 *
 * @author michael
 */
public class KeyMap {

    public static GenericJoystick gamepad1 = new GenericJoystick(1);
    public static GenericJoystick gamepad2 = new GenericJoystick(2);
    private static GenericJoystick gamepad3 = new GenericJoystick(3);
    private static GenericJoystick gamepad4 = new GenericJoystick(4);
    private ButtonState drvPulseLeft = Gamepad.newButtonState();
    private ButtonState drvPulseRight = Gamepad.newButtonState();
    private ButtonState drvPulseLeft2 = Gamepad.newButtonState();
    private ButtonState drvPulseRight2 = Gamepad.newButtonState();
    private ButtonState launcherToggleButtonState = Gamepad.newButtonState();
    private ButtonState launcherToggleButtonState2 = Gamepad.newButtonState();
    private ButtonState launcherToggleButtonState3 = Gamepad.newButtonState();
    private ButtonState launcherSpeedIncreaseButtonState = Gamepad.newButtonState();
    private ButtonState launcherSpeedDeincreaseButtonState = Gamepad.newButtonState();
    private ButtonState elevationIncreaseButtonState = Gamepad.newButtonState();
    private ButtonState elevationDeincreaseButtonState = Gamepad.newButtonState();
    private ButtonState monocleButtonState = Gamepad.newButtonState();
    private ButtonState monocleState = Gamepad.newButtonState();
    private ButtonState gateButtonState = Gamepad.newButtonState();
    private ButtonState gateButtonState2 = Gamepad.newButtonState();
    private ButtonState gateButtonState3 = Gamepad.newButtonState();
    private ButtonState gateState = Gamepad.newButtonState();
    private ButtonState gateState2 = Gamepad.newButtonState();
    private ButtonState gateState3 = Gamepad.newButtonState();
    private ButtonState feederButtonState = Gamepad.newButtonState();
    private ButtonState feederState = Gamepad.newButtonState();
    private double oldValueDrive,oldValueRotation,oldValueStrafe;
    private long oldTimeDrive,oldTimeRotation,oldTimeStrafe;

    //////////   Driver (GP 1 & 3)   //////////
    public boolean getLauncherToggleButton() {
        return gamepad1.buttonPressed(Gamepad.B, launcherToggleButtonState)
                || gamepad2.buttonPressed(Gamepad.B, launcherToggleButtonState3)
                || gamepad3.buttonPressed(SaitekJoystick.PADDLE_LEFT, launcherToggleButtonState2);
    }

    public boolean getGateToggleButtonState() {
        return gamepad1.buttonToggled(Gamepad.A, gateButtonState, gateState)
                == gamepad2.buttonToggled(Gamepad.A, gateButtonState3, gateState3)
                == gamepad3.buttonToggled(SaitekJoystick.BASE_LEFT, gateButtonState2, gateState2);
    }

    public boolean getPulseLeftButton() {
        return gamepad1.buttonPressed(Gamepad.DPAD_LEFT, drvPulseLeft)
                || gamepad3.buttonPressed(SaitekJoystick.TOPHAT_LEFT, drvPulseLeft2);
    }

    public boolean getPulseRightButton() {
        return gamepad1.buttonPressed(Gamepad.DPAD_RIGHT, drvPulseRight)
                || gamepad3.buttonPressed(SaitekJoystick.TOPHAT_RIGHT, drvPulseRight2);
    }

    public boolean getFireButton() {
        return false;
//        return gamepad1.button(Gamepad.BUMPER_RIGHT)
//                || gamepad3.button(SaitekJoystick.TRIGGER);
    }

    public boolean getFireOverideButton() {
        return gamepad1.button(Gamepad.BUMPER_LEFT)
                || gamepad3.button(SaitekJoystick.TRIGGER_LEFT);
    }

    public boolean getAutoAlignButton() {
        return gamepad1.button(Gamepad.STICK_RIGHT);
    }

    public double getStrafeAxis() {
        double x = getStrafeAxisRaw();
        long t = System.currentTimeMillis();
        double result = x;
        if (t != oldTimeStrafe) {
            result += 300 * ((x - oldValueStrafe) / (t - oldTimeStrafe));
        }
        //result = Math.min(result, 1);
        //result = Math.max(result, -1);
        oldTimeStrafe = t;
        oldValueStrafe = x;
        return result;
    }

    public double getStrafeAxisRaw() {
        return gamepad1.axis(Gamepad.STICK_LEFT_X)
                + gamepad3.axis(SaitekJoystick.X);
    }

    public double getRotationAxis() {
        double x = getRotationAxisRaw();
        long t = System.currentTimeMillis();
        double result = x;
        if (t != oldTimeRotation) {
            result += 300 * ((x - oldValueRotation) / (t - oldTimeRotation));
        }
        //result = Math.min(result, 1);
        //result = Math.max(result, -1);
        oldTimeRotation = t;
        oldValueRotation = x;
        return result;
    }

    public double getRotationAxisRaw() {
        return gamepad1.axis(Gamepad.STICK_RIGHT_X)
                + gamepad3.axis(SaitekJoystick.TWIST);
    }

    public double getDriveAxis() {
        double x = getDriveAxisRaw();
        long t = System.currentTimeMillis();
        double result = x;
        if (t != oldTimeDrive) {
            result += 300 * ((x - oldValueDrive) / (t - oldTimeDrive));
        }
        //result = Math.min(result, 1);
        //result = Math.max(result, -1);
        oldTimeDrive = t;
        oldValueDrive = x;
        return result;
    }

    public double getDriveAxisRaw() {
        return gamepad1.axis(Gamepad.STICK_LEFT_Y)
                + gamepad3.axis(SaitekJoystick.Y);
    }

    public boolean getEjectButton() {
        return gamepad1.button(Gamepad.X)
                || gamepad3.button(SaitekJoystick.TRIGGER_RIGHT);
    }

    public boolean getClimbButton() {
        return gamepad1.button(Gamepad.Y);
    }

    //////////   Manipulator (GP 2)   //////////
    public boolean getLobPresetButton() {
        return gamepad2.button(Gamepad.Y);
    }

    public boolean getFull3ptPresetButton() {
        return gamepad2.button(Gamepad.TRIGGER_RIGHT);
    }

    public boolean getFull2ptPresetButton() {
        return gamepad2.button(Gamepad.TRIGGER_LEFT);
    }

    public boolean getFrontPyramid3ptPresetButton() {
        return gamepad2.button(Gamepad.X);
    }

    public boolean getHalf3ptPresetButton() {
        return gamepad2.button(Gamepad.BUMPER_RIGHT);
    }

    public boolean getAutonPresetButton() {
        return gamepad2.button(Gamepad.BUMPER_LEFT);
    }

    public double getLauncherSpeedChange() {
        return -MathUtils.pow(gamepad2.axis(Gamepad.STICK_RIGHT_Y), 3) * 0.03;
    }

    public boolean getElevationIncreaseButton() {
        return gamepad2.buttonPressed(Gamepad.STICK_RIGHT_UP, elevationIncreaseButtonState);
    }

    public boolean getElevationDeincreaseButton() {
        return gamepad2.buttonPressed(Gamepad.STICK_RIGHT_DOWN, elevationDeincreaseButtonState);
    }

    public double getElevationAxis() {
        return MathUtils.pow(gamepad2.axis(Gamepad.STICK_LEFT_Y), 5);
    }

    public double getFeederAxis() {
        return -gamepad2.axis(Gamepad.DPAD_X);
    }

    public boolean getFeederToggleButtonState() {
        return gamepad2.buttonToggled(Gamepad.B, feederButtonState, feederState);
    }

    public boolean getMonocleToggleButtonState() {
        return gamepad2.buttonToggled(Gamepad.B, monocleButtonState, monocleState);
    }

    public boolean getElevationResetButton() {
        return gamepad2.button(Gamepad.START);
    }
}
