package com.meisterdevs.ftc.mentor.utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by aburger on 10/1/2017.
 */

public class UDP_Server {
  
  private static final int SERVER_PORT = 5000;
  private AsyncTask<Void, Void, Void> async;
  private boolean Server_aktiv = true;
  private String message = "";
  
  public String getMessage() {
    
    return message;
  }
  
  @SuppressLint("NewApi")
  public void runUdpServer() {
    
    async = new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        
        byte[] lMsg = new byte[4096];
        DatagramPacket dp = new DatagramPacket(lMsg, lMsg.length);
        DatagramSocket ds = null;
        
        try {
          ds = new DatagramSocket(SERVER_PORT, InetAddress.getByName("0.0.0.0"));
          ds.setBroadcast(true);
          
          while (Server_aktiv) {
            ds.receive(dp);
            message=  new String(lMsg, 0, dp.getLength());
          
          }
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          if (ds != null) {
            ds.close();
          }
        }
        
        return null;
      }
    };
  
    if (Build.VERSION.SDK_INT >= 11) {
      async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    } else {
      async.execute();
    }
  }
  
  public void stop_UDP_Server() {
    
    Server_aktiv = false;
  }
  
}
