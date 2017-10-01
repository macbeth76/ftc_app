package com.meisterdevs.ftc.mentor;

import com.meisterdevs.ftc.mentor.utils.BNO055IMUHelper;
import com.meisterdevs.ftc.mentor.utils.I2draw;
import com.meisterdevs.ftc.mentor.utils.MRIColorBeacon;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;


/**
 * Created by ftc6347 on 8/31/17.
 */

@TeleOp(name = "Kickoff", group = "1-fta")
public class Kickoff2017 extends OpMode {

    I2draw i2draw;
    IrSeekerSensor irSeekerSensor;
    CompassSensor compassSensor;
    GyroSensor gyroSensor;
    //set gyro to read -180 to 180
    int headingVal;
    int previousHead = 0;
    BNO055IMUHelper imuHelper;
    BNO055IMU.Parameters parameters;
    MRIColorBeacon beacon;
    DcMotor demoMotor;
    DcMotor arm2;
    DeviceInterfaceModule deviceInterfaceModule;

    int red = 0;
    int blue = 0;
    int green = 0;
    int pos = 10;
    ElapsedTime elapsedTime = new ElapsedTime();
    ElapsedTime motorTimer = new ElapsedTime();
    ElapsedTime teleUpdate = new ElapsedTime();
    private static final String calibrationDataFile = "BNO055IMUCalibration.json";

    @Override
    public void init() {

        demoMotor = hardwareMap.dcMotor.get("gyroArm");
        arm2 = hardwareMap.dcMotor.get("arm2");
        arm2.setDirection(DcMotor.Direction.REVERSE);
        arm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //arm2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm2.setPower(0.0);


        demoMotor.setDirection(DcMotor.Direction.REVERSE);
        demoMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        i2draw = new I2draw("vside", "cside", this);
        irSeekerSensor = hardwareMap.irSeekerSensor.get("ir");
        beacon = new MRIColorBeacon();
        beacon.init(hardwareMap, "beacon");
        compassSensor = hardwareMap.compassSensor.get("compass");
        gyroSensor = hardwareMap.gyroSensor.get("gyro");
        gyroSensor.calibrate();
        parameters = new BNO055IMU.Parameters();
        parameters.loggingEnabled = true;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = calibrationDataFile; // see the calibration sample opmode
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imuHelper = new BNO055IMUHelper("imu", parameters, this);

        // Start the logging of measured acceleration
        imuHelper.getInstance().startAccelerationIntegration(new Position(), new Velocity(), 1000);
        imuHelper.composeTelemetry(telemetry);

        deviceInterfaceModule = hardwareMap.deviceInterfaceModule.get("cdi");
        telemetry.addData("arm2 pos: ", arm2.getCurrentPosition());


        elapsedTime.reset();
        motorTimer.reset();

    }

    @Override
    public void loop() {

        telemetry.addData("Volts= ", toData(hardwareMap.voltageSensor));
        telemetry.addData("i2draw Current= ", i2draw.getRobotCurrent());
        telemetry.addData("i2draw Power= ", i2draw.getRobotPower());
        telemetry.addData("i2draw Resistance= ", i2draw.getRobotResistance());
        telemetry.addData("i2draw Volt= ", i2draw.getRobotVoltage());
        telemetry.addData("LED 1 State", deviceInterfaceModule.getLEDState(1));
        telemetry.addData("ir", toData(irSeekerSensor));
        telemetry.addData("compass", toData(compassSensor));
        telemetry.addData("gyro", toData(gyroSensor));
        telemetry.addData("color:", "RGB " + red + " , " + green + " , " + blue);
        telemetry.addData("heading Converted: ", headingVal);
        telemetry.addData("motor power: ", demoMotor.getPower());
        telemetry.addData("arm2 pos: ", arm2.getCurrentPosition() + " of " + arm2.getTargetPosition());
        telemetry.addData("arm2 busy: ", arm2.isBusy());

        if (motorTimer.seconds() > 20) {
            arm2.setTargetPosition(pos + (int)(17 * Math.random()*100));
            arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //arm2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            arm2.setPower(1.0);
            motorTimer.reset();
        }

        if (teleUpdate.milliseconds() > 1000) {
            telemetry.update();
        }
        //set previous reading
        if (previousHead != headingVal) {
            previousHead = headingVal;
        }


        //set gyro to read -180 to 180 degree heading.
        if (gyroSensor.getHeading() > 179) {
            headingVal = ((gyroSensor.getHeading()) - 360);
        } else if (gyroSensor.getHeading() < 180) {
            headingVal = (gyroSensor.getHeading());
        }

        //change beacon color to gradient based on headingVal.

        if (previousHead != headingVal) {
            beacon.rgb((90 + (headingVal)), 0, (90 - (headingVal)));
            elapsedTime.reset();
            if (headingVal > 10) {
                deviceInterfaceModule.setLED(0, true);
                deviceInterfaceModule.setLED(1, false);
            } else if (headingVal < -10) {
                deviceInterfaceModule.setLED(0, false);
                deviceInterfaceModule.setLED(1, true);
            } else {
                deviceInterfaceModule.setLED(0, false);
                deviceInterfaceModule.setLED(1, false);
            }
        }

        //set motor to move based on gyro heading.

        if (headingVal > 5) {
            demoMotor.setPower(.007 * (headingVal));
        } else if (headingVal < -5) {
            demoMotor.setPower(.007 * (headingVal));
        } else {
            demoMotor.setPower(0);
        }


//        if (elapsedTime.seconds() > 10) {
//            changeColor();
//            elapsedTime.reset();
//        }



        /*if (gamepad1.start && gamepad1.x){
            imuHelper.writeCalFile(calibrationDataFile);
        }*/

    }

    private void changeColor() {
        blue = blue + 20;
        if (blue > 255) {
            blue = 0;
            green = green + 20;
            if (green > 255) {
                green = 0;
                red = red + 20;
                if (red > 255) {
                    red = 0;
                }
            }
        }

        beacon.rgb(red, green, blue);
    }

    private String toData(HardwareMap.DeviceMapping<VoltageSensor> voltageSensor) {
        double result = Double.POSITIVE_INFINITY;
        int num = 0;
        for (VoltageSensor sensor : hardwareMap.voltageSensor) {
            double voltage = sensor.getVoltage();
            if (voltage > 0) {
                result = Math.min(result, voltage);
            }
            num++;
        }
        return "volt[" + num + "]" + "results= " + result;
    }

    private String toData(GyroSensor gyroSensor) {
        StringBuilder builder = new StringBuilder();
        builder.append("h= ");
        builder.append(gyroSensor.getHeading());
        builder.append(" ");
        builder.append("xyz= ");
        builder.append(gyroSensor.rawX());
        builder.append(",");
        builder.append(gyroSensor.rawY());
        builder.append(",");
        builder.append(gyroSensor.rawZ());
        return builder.toString();
    }

    private String toData(CompassSensor compassSensor) {
        StringBuilder builder = new StringBuilder();
        builder.append("dir= ");
        builder.append(compassSensor.getDirection());
        return builder.toString();
    }

    private String toData(IrSeekerSensor irSeekerSensor) {
        StringBuilder builder = new StringBuilder();
        builder.append("ang= ");
        builder.append(irSeekerSensor.getAngle());
        builder.append(" ");
        builder.append("pow= ");
        builder.append(irSeekerSensor.getStrength());
        return builder.toString();
    }
}
