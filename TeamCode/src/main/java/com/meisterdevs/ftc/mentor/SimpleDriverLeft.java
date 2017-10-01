package com.meisterdevs.ftc.mentor;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;

/**
 * Created by Jordan Burklund on 7/30/2015.
 * An example linear op mode where the pushbot
 * will track an IR beacon.
 */
@TeleOp(name = "SimpleDriverLeft", group = "Mentor")
@Disabled
public class SimpleDriverLeft extends LinearOpMode {
    final static double kBaseSpeed = .30;  // Higher values will cause the robot to move faster

    final static double kMinimumStrength = 0.08; // Higher values will cause the robot to follow closer
    final static double kMaximumStrength = 0.60; // Lower values will cause the robot to stop sooner

    IrSeekerSensor irSeeker;
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

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        //leftRearMotor.setDirection(DcMotor.Direction.REVERSE);
        // Wait for the start button to be pressed
        waitForStart();

        // Continuously track the IR beacon
        while (opModeIsActive()) {
            leftMotor.setPower(kBaseSpeed);
            //         rightMotor.setPower(kBaseSpeed);
            leftRearMotor.setPower(kBaseSpeed);
            //         rightRearMotor.setPower(kBaseSpeed);
            telemetry.addData("Speed Front", " Left=" + leftMotor.getPower() + " Right=" + rightMotor.getPower());
            telemetry.addData("Speed", " Left=" + leftMotor.getPower() + " Right=" + rightMotor.getPower());
        }

    }
}
