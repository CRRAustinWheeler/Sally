package com.coderedrobotics;

import com.coderedrobotics.libs.dash.DashBoard;
import com.coderedrobotics.statics.KeyMap;
import com.coderedrobotics.statics.Wiring;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Sally extends IterativeRobot {

    Feeder feeder;
    Shooter shooter;
    Compressor compressor;
    Drive drive;
    Monocle monocle;
    KeyMap keyMap;
    Gyro gyro;
    public static DashBoard dash;
    int autoStage = 0;
    int testStage = 0;
    long startTime;
    private int pulse;
    private int pulseValue;

    public void robotInit() {
        System.out.println("robot init");

        dash = new DashBoard();
        dash.prtln("", 4);
        drive = new Drive();
        shooter = new Shooter();
        feeder = new Feeder();
        monocle = new Monocle();
        keyMap = new KeyMap();
        gyro = new Gyro(Wiring.gyroPort);
        compressor = new Compressor(
                Wiring.compresserGagePort,
                Wiring.compresserSpikePort);
    }

    public void autonomousInit() {
        shooter.launcher.enable();
        shooter.applyPreset(Shooter.AUTON);
        shooter.gate.setState(false);
        autoStage = 0;
        compressor.start();
        startTime = System.currentTimeMillis();
    }

    public void autonomousPeriodic2() {
        switch (autoStage) {
            case 0:
                if (shooter.fireIfReady()) {
                    autoStage++;
                }
                break;
            case 1:
                if (!shooter.fireIfReady()) {
                    autoStage++;
                }
                break;
            case 2:
                if (shooter.fireIfReady()) {
                    autoStage++;
                }
                break;
            case 3:
                if (!shooter.fireIfReady()) {
                    autoStage++;
                }
                break;
            case 4:
                if (shooter.fireIfReady()) {
                    autoStage++;
                }
                break;
            case 5:
                if (!shooter.fireIfReady()) {
                    autoStage++;
                }
                break;
            case 6:
                if (shooter.fireIfReady()) {
                    autoStage++;
                }
                break;
            case 7:
                if (!shooter.fireIfReady()) {
                    autoStage++;
                }
                break;
            case 8:
                if (shooter.fireIfReady()) {
                    autoStage++;
                }
                break;
            case 9:
                if (!shooter.fireIfReady()) {
                    autoStage++;
                }
            case 10:
                shooter.trigger.setState(false);
                shooter.launcher.disable();
                break;
        }
    }

    public void autonomousPeriodic() {
        long time = System.currentTimeMillis() - startTime;
        if (time < 6000) {
            shooter.trigger.setState(false);
        } else if (time < 6500) {
            shooter.trigger.setState(true);
        } else if (time < 8000) {
            shooter.trigger.setState(false);
        } else if (time < 8500) {
            shooter.trigger.setState(true);
        } else if (time < 10000) {
            shooter.trigger.setState(false);
        } else if (time < 10500) {
            shooter.trigger.setState(true);
        } else if (time < 12000) {
            shooter.trigger.setState(false);
        } else if (time < 12500) {
            shooter.trigger.setState(true);
        } else {
            shooter.trigger.setState(false);
            shooter.launcher.disable();
        }
    }

    public void teleopInit() {
        compressor.start();
    }

    public void teleopPeriodic() {

        //System.out.println(shooter.elevation.encoder.pidGet());
        //System.out.println(shooter.launcher.firstPID.getSetpoint());

        if (Sally.dash != null) {
            dash.streamPacket(gyro.getAngle(), "gyroAngle");
            dash.streamPacket(
                    DriverStation.getInstance().getBatteryVoltage(),
                    "batteryVoltage");
        }

        drive.auto = keyMap.getAutoAlignButton();

        shooter.gate.setState(keyMap.getGateToggleButtonState() && !keyMap.getFireButton());

        if (keyMap.getFireOverideButton()) {
            shooter.trigger.setState(true);
        } else {
            if (keyMap.getFireButton()) {
                shooter.fireIfReady();
            } else {
                shooter.trigger.setState(false);
                shooter.resetTriggerOveride();
            }
        }

        shooter.launcher.setSpeedRelative(keyMap.getLauncherSpeedChange());

        shooter.elevation.setRelative((int) (keyMap.getElevationAxis() * 1000));

        if (keyMap.getLauncherToggleButton()) {
            if (shooter.launcher.isEnabled()) {
                shooter.launcher.disable();
            } else {
                shooter.launcher.enable();
            }
        }

        if (keyMap.getEjectButton()) {
            shooter.ejector.setState(true);
        } else {
            shooter.ejector.setState(false);
        }

        feeder.setSpeed(keyMap.getFeederAxis());

        if (keyMap.getPulseLeftButton()) {
            pulse = 1;
            pulseValue = 1;
        }
        if (keyMap.getPulseRightButton()) {
            pulse = 1;
            pulseValue = -1;
        }

        double rot = keyMap.getRotationAxis();
        if (pulse > 0) {
            pulse--;
            rot = pulseValue;
        }

        double speed = keyMap.getDriveAxis();

        if (keyMap.getClimbButton()) {
            speed = -0.5;
        }

        drive.Move(keyMap.getStrafeAxis(), rot, speed);

        monocle.setEnabled(keyMap.getMonocleToggleButtonState());

        if (keyMap.getHalf3ptPresetButton()) {//right bumper
            shooter.applyPreset(Shooter.HALF_3PT_SHOT);
        }
        if (keyMap.getAutonPresetButton()) {//left bumper
            shooter.applyPreset(Shooter.AUTON);
        }
        if (keyMap.getFull3ptPresetButton()) {//right trigger
            shooter.applyPreset(Shooter.FULL_2PT_SHOT_ALT);///////////////////////////////////////
        }
        if (keyMap.getElevationResetButton()) {
            shooter.elevation.reset();
        }
    }

    public void testInit() {
        testStage = 0;
        startTime = System.currentTimeMillis();
        shooter.elevation.reset();
        compressor.stop();
    }

    public void testPeriodic() {
        long time = System.currentTimeMillis() - startTime;
        if (time < 5000) {
            //elevation reset
        } else if (time < 10000) {
            shooter.applyPreset(Shooter.AUTON);
            shooter.launcher.enable();
        } else if (time < 15000) {
            shooter.launcher.disable();
            drive.Move(0, 0.3, 0);
        } else if (time < 20000) {
            drive.Move(0, -0.3, 0);
        } else if (time < 26000) {
            drive.Move(0, 0, 0);
            compressor.start();
            shooter.trigger.setState(false);
            shooter.ejector.setState(false);
        } else if (time < 27000) {
            compressor.stop();
        } else if (time < 28000) {
            shooter.gate.setState(false);
            shooter.trigger.setState(true);
            shooter.ejector.setState(true);
        } else if (time < 29000) {
            shooter.gate.setState(true);
            shooter.trigger.setState(false);
            shooter.ejector.setState(false);
        } else if (time < 30000) {
            shooter.gate.setState(false);
        } else {
            feeder.setSpeed(keyMap.getFeederAxis());
        }
    }
}
