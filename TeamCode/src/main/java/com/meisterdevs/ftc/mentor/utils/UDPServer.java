package com.meisterdevs.ftc.mentor.utils;

import android.util.Log;

//import com.meisterdev.messages.Msg;

import com.meisterdev.messages.Msg;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by aburger on 10/1/2017.
 */

public class UDPServer implements Runnable {

    private static final int SERVER_PORT = 5000;
    private static boolean Server_aktiv = true;
    BlockingQueue<byte[]> messageQueue = new ArrayBlockingQueue<>(1200);
    private static final String TAG = "UDPServer";

    @Override
    public void run() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(SERVER_PORT, InetAddress.getByName("0.0.0.0"));
            ds.setBroadcast(true);
            Log.d(TAG,"Server Started");

            while (Server_aktiv) {
                byte[] buffer = new byte[65507];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                ds.receive(packet);
                //String message = new String(buffer, 0, dp.getLength());
                byte[] bytes = new byte[packet.getLength()];
                for (int i = 0; i < packet.getLength(); i++) {
                    bytes[i] = buffer[i];
                }
                this.messageQueue.put(bytes);
                Log.d(TAG,"message count" + messageQueue.size());

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage(),e);
        } finally {
            if (ds != null) {
                ds.close();
            }
        }

    }

    public void stop() {
        Server_aktiv = false;
    }

    public byte[] getMessageTake() throws InterruptedException {
        return messageQueue.take();
    }

    public byte[] getMessagePoll() {
        return messageQueue.poll();
    }
}
