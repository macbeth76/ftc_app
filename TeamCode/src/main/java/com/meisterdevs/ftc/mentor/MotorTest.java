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

package com.meisterdevs.ftc.mentor;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * A simple example of a linear op mode that will approach an IR beacon.
 */
@TeleOp(name = "MotorTest", group = "Mentor")
@Disabled
public class MotorTest extends LinearOpMode {
  
  private static final double MOTOR_POWER = 0.15;
  
    private MotorContoller mc;
    private MotorSpeed ms;
  
    @Override
    public void runOpMode() throws InterruptedException {
        mc = new MotorContoller(hardwareMap);
        ms = new MotorSpeed(mc);

        telemetry.addData("Mode", "Start Init");
        // set up the hardware devices we are going to use

        mc.setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mc.motorsForward();
        telemetry.addData("Mode", "Finished Init");
        // wait for the start button to be pressed
        waitForStart();
        //powerDrive();
        driveToPos(2000);
        telemetry.addData("Mode", "End");
        mc.motorsLeft();
        driveToPos(4000);
        while (opModeIsActive()) {
            mc.setAllMotorSpeed(0);
            mc.getMotorInfo(telemetry);
            ms.calSpeed();
            telemetry.addData("Speed", ms.toString());
        }
    }

    private void driveToPos(int pos) {
        //setting the desired number of counts the motor will travel, setting a target
        mc.getLeftMotor().setTargetPosition(pos);
        mc.getRightMotor().setTargetPosition(pos);
        mc.getLeftRearMotor().setTargetPosition(pos);
        mc.getRightRearMotor().setTargetPosition(pos);
        //the motors will go to the target position (above)
        mc.setDriveMode(DcMotor.RunMode.RUN_TO_POSITION);

        //the motors will move at this speed
        mc.setAllMotorSpeed(MOTOR_POWER);

        while (mc.areMotorsBusy() && opModeIsActive()) {
            ms.calSpeed();
            telemetry.addData("Speed", ms.toString());
            mc.getMotorInfo(telemetry);
        }
    }

}
