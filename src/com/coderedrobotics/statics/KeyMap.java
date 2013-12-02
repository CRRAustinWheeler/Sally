package com.coderedrobotics.statics;

import com.coderedrobotics.libs.DerivativeCalculator;
import com.coderedrobotics.libs.HID.LogitechF310;
import com.coderedrobotics.libs.HID.HID;
import com.coderedrobotics.libs.HID.HID.ButtonState;
import com.coderedrobotics.libs.HID.SaitekJoystick;
import com.sun.squawk.util.MathUtils;

/**
 *
 * @author michael
 */
public class KeyMap {

    public static HID gamepad1 = new HID(1);
    public static HID gamepad2 = new HID(2);
    private static HID gamepad3 = new HID(3);
    private static HID gamepad4 = new HID(4);
    private ButtonState drvPulseLeft = LogitechF310.newButtonState();
    private ButtonState drvPulseRight = LogitechF310.newButtonState();
    private ButtonState drvPulseLeft2 = LogitechF310.newButtonState();
    private ButtonState drvPulseRight2 = LogitechF310.newButtonState();
    private ButtonState launcherToggleButtonState = LogitechF310.newButtonState();
    private ButtonState launcherToggleButtonState2 = LogitechF310.newButtonState();
    private ButtonState launcherToggleButtonState3 = LogitechF310.newButtonState();
    private ButtonState launcherSpeedIncreaseButtonState = LogitechF310.newButtonState();
    private ButtonState launcherSpeedDeincreaseButtonState = LogitechF310.newButtonState();
    private ButtonState elevationIncreaseButtonState = LogitechF310.newButtonState();
    private ButtonState elevationDeincreaseButtonState = LogitechF310.newButtonState();
    private ButtonState monocleButtonState = LogitechF310.newButtonState();
    private ButtonState monocleState = LogitechF310.newButtonState();
    private ButtonState gateButtonState = LogitechF310.newButtonState();
    private ButtonState gateButtonState2 = LogitechF310.newButtonState();
    private ButtonState gateButtonState3 = LogitechF310.newButtonState();
    private ButtonState gateState = LogitechF310.newButtonState();
    private ButtonState gateState2 = LogitechF310.newButtonState();
    private ButtonState gateState3 = LogitechF310.newButtonState();
    private ButtonState feederButtonState = LogitechF310.newButtonState();
    private ButtonState feederState = LogitechF310.newButtonState();
    //private double oldValueDrive,oldValueRotation,oldValueStrafe;
    //private long oldTimeDrive,oldTimeRotation,oldTimeStrafe;
    private DerivativeCalculator driveCalculator = new DerivativeCalculator(),
            rotationCalculator = new DerivativeCalculator(),
            strafeCalculator = new DerivativeCalculator();

    //////////   Driver (GP 1 & 3)   //////////
    public boolean getLauncherToggleButton() {
        return gamepad1.buttonPressed(LogitechF310.B, launcherToggleButtonState)
                || gamepad2.buttonPressed(LogitechF310.B, launcherToggleButtonState3)
                || gamepad3.buttonPressed(SaitekJoystick.PADDLE_LEFT, launcherToggleButtonState2);
    }

    public boolean getGateToggleButtonState() {
        return gamepad1.buttonToggled(LogitechF310.A, gateButtonState, gateState)
                == gamepad2.buttonToggled(LogitechF310.A, gateButtonState3, gateState3)
                == gamepad3.buttonToggled(SaitekJoystick.BASE_LEFT, gateButtonState2, gateState2);
    }

    public boolean getPulseLeftButton() {
        return gamepad1.buttonPressed(LogitechF310.DPAD_LEFT, drvPulseLeft)
                || gamepad3.buttonPressed(SaitekJoystick.TOPHAT_LEFT, drvPulseLeft2);
    }

    public boolean getPulseRightButton() {
        return gamepad1.buttonPressed(LogitechF310.DPAD_RIGHT, drvPulseRight)
                || gamepad3.buttonPressed(SaitekJoystick.TOPHAT_RIGHT, drvPulseRight2);
    }

    public boolean getFireButton() {
        return false;
//        return gamepad1.button(LogitechF310.BUMPER_RIGHT)
//                || gamepad3.button(SaitekJoystick.TRIGGER);
    }

    public boolean getFireOverideButton() {
        return gamepad1.button(LogitechF310.BUMPER_LEFT)
                || gamepad3.button(SaitekJoystick.TRIGGER_LEFT);
    }

    public boolean getAutoAlignButton() {
        return gamepad1.button(LogitechF310.STICK_RIGHT);
    }

    public double getStrafeAxis() {
        double raw = getStrafeAxisRaw();
        double result = raw + (strafeCalculator.calculate(raw) * 100);
        result = Math.min(result, 1);
        result = Math.max(result, -1);
        return result;
    }

    public double getStrafeAxisRaw() {
        return gamepad1.axis(LogitechF310.STICK_LEFT_X)
                + gamepad3.axis(SaitekJoystick.X);
    }

    public double getRotationAxis() {
        double raw = getRotationAxisRaw();
        double result = raw + (rotationCalculator.calculate(raw) * 100);
        result = Math.min(result, 1);
        result = Math.max(result, -1);
        return result;
    }

    public double getRotationAxisRaw() {
        return gamepad1.axis(LogitechF310.STICK_RIGHT_X)
                + gamepad3.axis(SaitekJoystick.TWIST);
    }

    public double getDriveAxis() {
        double raw = getDriveAxisRaw();
        double result = raw + (driveCalculator.calculate(raw) * 100);
        result = Math.min(result, 1);
        result = Math.max(result, -1);
        return result;
    }

    public double getDriveAxisRaw() {
        return gamepad1.axis(LogitechF310.STICK_LEFT_Y)
                + gamepad3.axis(SaitekJoystick.Y);
    }

    public boolean getEjectButton() {
        return gamepad1.button(LogitechF310.X)
                || gamepad3.button(SaitekJoystick.TRIGGER_RIGHT);
    }

    public boolean getClimbButton() {
        return gamepad1.button(LogitechF310.Y);
    }

    //////////   Manipulator (GP 2)   //////////
    public boolean getLobPresetButton() {
        return gamepad2.button(LogitechF310.Y);
    }

    public boolean getFull3ptPresetButton() {
        return gamepad2.button(LogitechF310.TRIGGER_RIGHT);
    }

    public boolean getFull2ptPresetButton() {
        return gamepad2.button(LogitechF310.TRIGGER_LEFT);
    }

    public boolean getFrontPyramid3ptPresetButton() {
        return gamepad2.button(LogitechF310.X);
    }

    public boolean getHalf3ptPresetButton() {
        return gamepad2.button(LogitechF310.BUMPER_RIGHT);
    }

    public boolean getAutonPresetButton() {
        return gamepad2.button(LogitechF310.BUMPER_LEFT);
    }

    public double getLauncherSpeedChange() {
        return -MathUtils.pow(gamepad2.axis(LogitechF310.STICK_RIGHT_Y), 3) * 0.03;
    }

    public boolean getElevationIncreaseButton() {
        return gamepad2.buttonPressed(LogitechF310.STICK_RIGHT_UP, elevationIncreaseButtonState);
    }

    public boolean getElevationDeincreaseButton() {
        return gamepad2.buttonPressed(LogitechF310.STICK_RIGHT_DOWN, elevationDeincreaseButtonState);
    }

    public double getElevationAxis() {
        return MathUtils.pow(gamepad2.axis(LogitechF310.STICK_LEFT_Y), 5);
    }

    public double getFeederAxis() {
        return -gamepad2.axis(LogitechF310.DPAD_X);
    }

    public boolean getFeederToggleButtonState() {
        return gamepad2.buttonToggled(LogitechF310.B, feederButtonState, feederState);
    }

    public boolean getMonocleToggleButtonState() {
        return gamepad2.buttonToggled(LogitechF310.B, monocleButtonState, monocleState);
    }

    public boolean getElevationResetButton() {
        return gamepad2.button(LogitechF310.START);
    }
}
