package com.meisterdevs.ftc.mentor;

import com.meisterdevs.ftc.mentor.utils.RabbitMQWrapper;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by aburger on 9/30/2017.
 */
@TeleOp(name = "RabbitMQBot", group = "Mentor")

public class RabbitMQBot extends OpMode {
  private RabbitMQWrapper rabbitMQWrapper = new RabbitMQWrapper();
  
  @Override
  public void init() {
    rabbitMQWrapper.setupConnectionFactory();
    
  
  
  }
  
  @Override
  public void loop() {
    
    if (rabbitMQWrapper.checkForMessage())
    {
      telemetry.addData("rabbitMQ",rabbitMQWrapper.getMessage());
    }
    
    
  }
  
  
}
