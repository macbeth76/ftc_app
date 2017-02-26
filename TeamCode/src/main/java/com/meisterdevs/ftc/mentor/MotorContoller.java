package com.meisterdevs.ftc.mentor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class MotorContoller {
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor leftRearMotor;
    DcMotor rightRearMotor;


    public MotorContoller(HardwareMap hm) {
        leftMotor = hm.dcMotor.get("lf");
        rightMotor = hm.dcMotor.get("rf");
        leftRearMotor = hm.dcMotor.get("lb");
        rightRearMotor = hm.dcMotor.get("rb");
    }

    public DcMotor getLeftMotor() {
        return leftMotor;
    }

    public DcMotor getRightMotor() {
        return rightMotor;
    }

    public DcMotor getLeftRearMotor() {
        return leftRearMotor;
    }

    public DcMotor getRightRearMotor() {
        return rightRearMotor;
    }


    /**
     * Sets the drive mode for each motor.
     * The types of Run Modes are
     * DcMotorController.RunMode.RESET_ENCODERS
     * Resets the Encoder Values to 0
     * DcMotorController.RunMode.RUN_TO_POSITION
     * Runs until the encoders are equal to the target position
     * DcMotorController.RunMode.RUN_USING_ENCODERS
     * Attempts to keep the robot running straight utilizing
     * the PID the reduces the maximum power by about 15%
     * DcMotorController.RunMode.RUN_WITHOUT_ENCODERS
     * Applies the power directly
     *
     * @param mode
     */
    public void setDriveMode(DcMotor.RunMode mode) {
        if (leftMotor.getMode() != mode) {
            leftMotor.setMode(mode);
        }
        if (rightMotor.getMode() != mode) {
            rightMotor.setMode(mode);
        }
        if (leftRearMotor.getMode() != mode) {
            leftRearMotor.setMode(mode);
        }
        if (rightRearMotor.getMode() != mode) {
            rightRearMotor.setMode(mode);
        }
    }

    public void motorsForward() {
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void motorsBackwards() {
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        leftRearMotor.setDirection(DcMotor.Direction.FORWARD);
        rightRearMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void motorsLeft() {
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        leftRearMotor.setDirection(DcMotor.Direction.FORWARD);
        rightRearMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void motorsRight() {
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setAllMotorSpeed(double speed) {
        leftMotor.setPower(speed);
        rightMotor.setPower(speed);
        leftRearMotor.setPower(speed);
        rightRearMotor.setPower(speed);
    }

    public boolean areMotorsBusy() {
        return leftMotor.isBusy() || rightMotor.isBusy() || leftRearMotor.isBusy() || rightRearMotor.isBusy();
    }


    public void getMotorInfo(Telemetry telemetry) {
        telemetry.addData("MotorPos ", "----------------");
        telemetry.addData("MotorPos: F", leftMotor.getCurrentPosition() + "\t\t" + rightMotor.getCurrentPosition());
        telemetry.addData("MotorPos: R", leftRearMotor.getCurrentPosition() + "\t\t" + rightRearMotor.getCurrentPosition());
    }

}