package com.meisterdevs.ftc.mentor;

import android.util.Log;

import com.meisterdev.messages.Msg;
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
    Thread thread = null;
    private static String TAG = UDPBot.class.getName();

    @Override
    public void init() {
        thread = new Thread(udpServer);
        thread.start();
        telemetry.addData(">", "Stating Server");
    }

    @Override
    public void loop() {

        byte[] buffer = udpServer.getMessagePoll();
        if (buffer != null) {
            readMessage(buffer);
        }

    }

    private void readMessage(byte[] buffer) {
        boolean proto = true;
        Msg msg = null;
        String newmessage;
        try {
            msg = Msg.parseFrom(buffer);
            proto = true;
        } catch (Exception e) {
            //Log.e(TAG, "Proto Error", e);
            //telemetry.addData("ERROR", e);
            proto = false;
        }
        if (proto) {
            newmessage = msg.getMess();
        } else {
            newmessage = new String(buffer);
        }


        if (!newmessage.equals(lastmessage)) {
            lastmessage = newmessage;
            if (proto) {
                telemetry.addData("proto message", newmessage);
            } else {
                telemetry.addData("string message", newmessage);
            }
            telemetry.update();
        }
    }

    @Override
    public void stop() {
        udpServer.stop();
        thread.interrupt();
        super.stop();
    }
}
