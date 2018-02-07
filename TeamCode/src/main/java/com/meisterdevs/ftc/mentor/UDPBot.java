package com.meisterdevs.ftc.mentor;

import android.util.Log;

import com.meisterdevs.ftc.mentor.utils.UDPServer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by aburger on 9/30/2017.
 */
@TeleOp(name = "UDPBot", group = "Mentor")

public class UDPBot extends OpMode {

    String lastmessage = "";
    UDPServer udpServer = new UDPServer();
    Thread thread = new Thread(udpServer);
    private static String TAG = UDPBot.class.getName();

    @Override
    public void init() {
        thread.start();
    }

    @Override
    public void loop() {

        byte[] buffer = udpServer.getMessagePoll();
        if (buffer != null) {
            String message = new String(buffer);
            Log.d(TAG,"Message is " + message);
            String newmessage = new String(buffer);

            if (!newmessage.equals(lastmessage)) {
                lastmessage = newmessage;
                telemetry.addData("lastmessage", newmessage);
                telemetry.update();
            }
        }


    }

    @Override
    public void stop() {
        udpServer.stop();
        super.stop();
    }
}
