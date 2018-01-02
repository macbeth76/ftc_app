package com.meisterdevs.ftc.mentor;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meisterdevs.ftc.ContextHolder;
import com.meisterdevs.ftc.mentor.pojo.ConfigPOJO;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


@TeleOp(name = "GSONTesterWrite", group = "Mentor")
public class GsonTeleopWrite extends LinearOpMode {

    public static final String TAG = "GsonTeleop";

    public void createConfigFolder() {
        Context context = ContextHolder.getInstance().getContext();
        AppUtil appUtil = AppUtil.getInstance();
        File robotDir = TeamUtil.TEAM_FILES_DIR;
        boolean createdDir = true;

        if (!robotDir.exists()) {
            createdDir = robotDir.mkdir();
        }

        if (!createdDir) {
            RobotLog.ee(TAG, "Can't create the Robot Config Files directory!");
            appUtil.showToast(UILocation.BOTH, context, context.getString(com.qualcomm.ftccommon.R.string.toastCantCreateRobotConfigFilesDir));
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

        boolean success = false;
        File folder = TeamUtil.TEAM_FILES_DIR;
        createConfigFolder();
        if (!folder.exists()) {
            Log.d(TAG, "Folder doesn't exist");
        } else {
            success = true;
        }
        if (success) {
            Log.d(TAG, "Folder exists");
            File file = new File(TeamUtil.TEAM_FILES_DIR, TeamUtil.CONFIG_FILE);
            FileOutputStream stream = null;
            try {
                stream = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(stream, "UTF-8");

                Log.d(TAG, "Writing JSON");

                ConfigPOJO configPOJO = new ConfigPOJO();
                configPOJO.setName("test1");

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(configPOJO, osw);

                osw.close();

                String jsonInString = gson.toJson(configPOJO);
                telemetry.addData(TAG, jsonInString);
                telemetry.update();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    // Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

        waitForStart();
        while (opModeIsActive()) {

        }
    }
}
