package com.meisterdevs.ftc.mentor;

/**
 * Created by aburger on 5/14/16.
 */
public class MotorSpeed {
    private int speedAcc = 1000;
    private int speedAccCount = 1000;
    private int lastLeftMotorPos = 0;
    private int lastRightMotorPos = 0;
    private int lastLeftRearMotorPos = 0;
    private int lastRightRearMotorPos = 0;
    private double lfSpeed;
    private double rfSpeed;
    private double lrSpeed;
    private double rrSpeed;
    private MotorContoller mc;

    public MotorSpeed(MotorContoller _mc) {
        this.mc = _mc;
    }


    public void calSpeed() {
        if (speedAccCount == 0) {
            lfSpeed = mc.getLeftMotor().getCurrentPosition() - lastLeftMotorPos;
            rfSpeed = mc.getRightMotor().getCurrentPosition() - lastRightMotorPos;
            lrSpeed = mc.getLeftRearMotor().getCurrentPosition() - lastLeftRearMotorPos;
            rrSpeed = mc.getRightRearMotor().getCurrentPosition() - lastRightRearMotorPos;
            lastLeftMotorPos = mc.getLeftMotor().getCurrentPosition();
            lastRightMotorPos = mc.getRightMotor().getCurrentPosition();
            lastLeftRearMotorPos = mc.getLeftRearMotor().getCurrentPosition();
            lastRightRearMotorPos = mc.getRightRearMotor().getCurrentPosition();
            speedAccCount = speedAcc;
        } else {
            speedAccCount--;
        }

    }

    @Override
    public String toString() {
        return "C,LF,LR,RR,RF:" + speedAccCount + ":" + lfSpeed + "," + lrSpeed + "," + rrSpeed + "," + rfSpeed;
    }
}
