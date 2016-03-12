/* Copyright (c) 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes.aburger;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController.RunMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;

/**
 * A simple example of a linear op mode that will approach an IR beacon
 */
public class MotorTest extends LinearOpMode {

    final static double MOTOR_POWER = 0.15; // Higher values will cause the robot to move faster
    final static double HOLD_IR_SIGNAL_STRENGTH = 0.20; // Higher values will cause the robot to follow closer

    DcMotor motorRight;
    DcMotor motorLeft;


    @Override
    public void runOpMode() throws InterruptedException {


        telemetry.addData("Mode", "Start Init");
        // set up the hardware devices we are going to use


        motorLeft = hardwareMap.dcMotor.get("left");
        motorRight = hardwareMap.dcMotor.get("right");

        RunMode runMode = RunMode.RUN_USING_ENCODERS;
        //RunMode runMode = RunMode.RUN_WITHOUT_ENCODERS
        //Encoders for Motors
        setDriveMode(runMode);

        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Mode", "Finished Init");
        // wait for the start button to be pressed
        waitForStart();
        //powerDrive();
        driveToPosition(7000);
        telemetry.addData("Mode", "End");

    }

    private void driveToPosition(int i) throws InterruptedException {

        //first distance the robot travels
        //reset tread distance encoders
        setDriveMode(RunMode.RESET_ENCODERS);

        // Wait for Encoders to be reset
        while (motorLeft.getCurrentPosition() != 0 && motorRight.getCurrentPosition() != 0) {
            waitOneFullHardwareCycle();
        }

//setting the desired number of counts the motor will travel, setting a target
        motorLeft.setTargetPosition((int) i);
        motorRight.setTargetPosition((int) i);

        //the motors will go to the target position (above)
        setDriveMode(RunMode.RUN_TO_POSITION);

        // Don't know if this is necessary
        waitOneFullHardwareCycle();

        //the motors will move at this speed
        motorLeft.setPower(MOTOR_POWER);
        motorRight.setPower(MOTOR_POWER);

        while (motorLeft.isBusy() && motorRight.isBusy()) {
            waitOneFullHardwareCycle();
            getMotorInfo();
        }
    }

    private void powerDrive() throws InterruptedException {
        motorRight.setPower(MOTOR_POWER);
        motorLeft.setPower(-MOTOR_POWER);

        // wait for the robot to center on the beacon
        for (int i = 0; i < 1000; i++) {
            waitOneFullHardwareCycle();
            telemetry.addData("Mode", "In Loop");
            getMotorInfo();
        }
        // stop the motors
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }

    private void getMotorInfo() {
        telemetry.addData("Motor1", motorLeft.getCurrentPosition());
        telemetry.addData("Motor2", motorRight.getCurrentPosition());
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
    public void setDriveMode(DcMotorController.RunMode mode) {
        if (motorLeft.getMode() != mode) {
            motorLeft.setMode(mode);
        }

        if (motorRight.getMode() != mode) {
            motorRight.setMode(mode);
        }
    }
}
