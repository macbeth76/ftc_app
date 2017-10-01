package com.meisterdevs.ftc.mentor;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "SimpleDriver", group = "Mentor")
@Disabled
public class SimpleDriver extends LinearOpMode {
    final static double kBaseSpeed = 1;  // Higher values will cause the robot to move faster

    final static double kMinimumStrength = 0.08; // Higher values will cause the robot to follow closer
    final static double kMaximumStrength = 0.60; // Lower values will cause the robot to stop sooner
    MotorContoller mc;



    @Override
    public void runOpMode() throws InterruptedException {

        mc = new MotorContoller(hardwareMap);

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
            mc.getLeftMotor().setPower(speed);
            mc.getRightMotor().setPower(speed);
            mc.getLeftRearMotor().setPower(speed);
            mc.getRightRearMotor().setPower(speed);
            telemetry.addData("Speed ", speed);
            telemetry.addData("Speed Front", " Left=" + mc.getLeftMotor().getPower() + " Right=" + mc.getRightMotor().getPower());
            telemetry.addData("Speed Rear", " Left=" + mc.getLeftMotor().getPower() + " Right=" + mc.getRightMotor().getPower());
            telemetry.addData("FD:", mc.getLeftMotor().getDirection() + "," + mc.getRightMotor().getDirection());
            telemetry.addData("RD:", mc.getLeftRearMotor().getDirection() + "," + mc.getRightRearMotor().getDirection());
            if (i == Integer.MAX_VALUE) {
                i = 0;
            } else {
                i++;
            }


        }

    }

    private void goforward() {
        telemetry.addData("State", "Go Forward");
        mc.motorsForward();
    }

    private void gobackward() {
        telemetry.addData("State", "Go Back");
        mc.motorsBackwards();
    }

    private void goleft() {
        telemetry.addData("State", "Go Left");
        mc.motorsLeft();
    }

    private void goright() {
        telemetry.addData("State", "Go Right");
        mc.motorsRight();
    }
}
