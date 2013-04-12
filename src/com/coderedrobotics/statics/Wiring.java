package com.coderedrobotics.statics;

/**
 * Holds constants for the robot in the form of static variables.
 *
 * @author
 */
public interface Wiring {
    /*
     * ----------- MOTOR CONTROLER CONNECTIONS --------------
     */

    public static final int jaguarFrontRightPort = 4;
    public static final int jaguarFrontLeftPort = 3;
    public static final int jaguarBackRightPort = 1;
    public static final int jaguarBackLeftPort = 2;
    public static final int shootingTalon1 = 5;
    public static final int shootingTalon2 = 6;
    public static final int elevationMotor = 7;
    public static final int feederTilt = 8;
    public final static int gateServo = 9;
    public static final int monicleServo = 10;
    /*
     * ----------- DIGITAL I/O PORTS -------------------------
     */
    public static final int launcherEncoder2PortA = 9;
    public static final int launcherEncoder2PortB = 10;
    public static final int launcherEncoder1PortA = 11;
    public static final int launcherEncoder1PortB = 12;
    public static final int elevationEncoderPortA = 14;
    public static final int elevationEncoderPortB = 13;
    /*
     * ----------- DIGITAL I/O PORTS -------------------------
     */
    public static final int compresserGagePort = 7;
    public static final int flipperLimitSwitch = 3;
    public static final int elevationLimitSwitch = 5;
    /*
     * ----------- RELAYS ---------------------------------
     */
    public static final int compresserSpikePort = 3;
    /*
     * ----------- SOLENOID PORTS -------------------------
     */
    public static final int solenoidIn = 1;
    public static final int solenoidOut = 2;
    public static final int ejectorIn = 3;
    public static final int ejectorOut = 4;
    public final static int feederIn = 5;
    public final static int feederOut = 6;

    /*
     * -----------  ANALOG INPUTS -------------------------
     */
    public final static int gyroPort = 1;
    public final static int pot = 3;
    
    public final static boolean dashEnabled = true;
}
