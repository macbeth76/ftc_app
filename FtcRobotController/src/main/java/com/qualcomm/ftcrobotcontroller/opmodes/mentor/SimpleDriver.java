package com.qualcomm.ftcrobotcontroller.opmodes.mentor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class SimpleDriver extends LinearOpMode {
    final static double kBaseSpeed = 1;  // Higher values will cause the robot to move faster

    final static double kMinimumStrength = 0.08; // Higher values will cause the robot to follow closer
    final static double kMaximumStrength = 0.60; // Lower values will cause the robot to stop sooner

    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor leftRearMotor;
    DcMotor rightRearMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        leftMotor = hardwareMap.dcMotor.get("lf");
        rightMotor = hardwareMap.dcMotor.get("rf");
        leftRearMotor = hardwareMap.dcMotor.get("lb");
        rightRearMotor = hardwareMap.dcMotor.get("rb");

        goforward();
        // Wait for the start button to be pressed
        waitForStart();
        Integer i = 0;
        int caseValue = 0;
        int caseSwitch = 100;
        int oldCase = 0;
        double speed = kMinimumStrength;
        // Continuously track the IR beacon
        while (opModeIsActive()) {
            if (speed < kMaximumStrength) {
                speed = speed + .001;
            }
            caseValue = (i / caseSwitch) % 4;
            if (caseValue != oldCase) {
                if (caseValue >= 2) {
                    speed = kMinimumStrength + .10;
                } else {
                    speed = kMinimumStrength;
                }
                oldCase = caseValue;
            }
            telemetry.addData("Case:", "i=" + i + "%" + caseSwitch + "=" + caseValue);
            switch (caseValue) {
                case 0:
                    goforward();
                    break;
                case 1:
                    gobackward();
                    break;
                case 2:
                    goleft();
                    break;
                case 3:
                    goright();
                    break;
                default:
                    new Exception("Shouldn't Get Here");
            }
            leftMotor.setPower(speed);
            rightMotor.setPower(speed);
            leftRearMotor.setPower(speed);
            rightRearMotor.setPower(speed);
            telemetry.addData("Speed ", speed);
            telemetry.addData("Speed Front", " Left=" + leftMotor.getPower() + " Right=" + rightMotor.getPower());
            telemetry.addData("Speed Rear", " Left=" + leftMotor.getPower() + " Right=" + rightMotor.getPower());
            telemetry.addData("FD:", leftMotor.getDirection() + "," + rightMotor.getDirection());
            telemetry.addData("RD:", leftRearMotor.getDirection() + "," + rightRearMotor.getDirection());
            if (i == Integer.MAX_VALUE) {
                i = 0;
            } else {
                i++;
            }


        }

    }

    private void goforward() {
        telemetry.addData("State", "Go Forward");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    private void gobackward() {
        telemetry.addData("State", "Go Back");
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        leftRearMotor.setDirection(DcMotor.Direction.FORWARD);
        rightRearMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    private void goleft() {
        telemetry.addData("State", "Go Left");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        leftRearMotor.setDirection(DcMotor.Direction.FORWARD);
        rightRearMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    private void goright() {
        telemetry.addData("State", "Go Right");
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.REVERSE);
    }
}
