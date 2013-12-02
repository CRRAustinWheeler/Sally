/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderedrobotics.libs;

/**
 *
 * @author laptop
 */
public class DerivativeCalculator {

    private double derivative;
    private double oldValue;
    private long oldTime;

    public double calculate(double val) {
        long time = System.currentTimeMillis();
        if (time != oldTime) {
            derivative = (val - oldValue)/((double)(time - oldTime));
            oldTime = time;
            oldValue = val;
        }
        return derivative;
    }
}
