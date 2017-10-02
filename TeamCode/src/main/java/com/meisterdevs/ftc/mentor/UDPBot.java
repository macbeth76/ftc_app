package com.meisterdevs.ftc.mentor;

import com.meisterdevs.ftc.mentor.utils.UDP_Server;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by aburger on 9/30/2017.
 */
@TeleOp(name = "UDPBot", group = "Mentor")

public class UDPBot extends OpMode {
  
  UDP_Server udp_server = new UDP_Server();
  String lastmessage = "";
  
  @Override
  public void init() {
    udp_server.runUdpServer();
    
  
  
  }
  
  @Override
  public void loop() {
    
    String newmessage = udp_server.getMessage();
    if (!newmessage.equals(lastmessage))
    {
      lastmessage = newmessage;
      telemetry.addData("lastmessage",newmessage);
    }
    
    
  }
  
  @Override
  public void stop() {
    udp_server.stop_UDP_Server();
    super.stop();
  }
}
