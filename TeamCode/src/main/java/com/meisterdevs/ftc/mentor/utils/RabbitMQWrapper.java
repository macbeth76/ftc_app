package com.meisterdevs.ftc.mentor.utils;

import android.util.Log;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by aburger on 9/30/2017.
 */

public class RabbitMQWrapper {
  
  private static final String QUEUE_NAME = "robot";
  ConnectionFactory factory = new ConnectionFactory();
  String message = "";
  
  public String getMessage() {
    
    return message;
  }
  
  
  public void setupConnectionFactory() {
    
    
    try {
      factory.setAutomaticRecoveryEnabled(false);
      factory.setHost("192.168.64.104");
      factory.setUsername("aburger");
      factory.setPassword("aburger");
      //factory.setConnectionTimeout(20);
      //factory.setHandshakeTimeout(10);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }
  
  
  public boolean checkForMessage() {
    
    try {
      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();
      
      //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      boolean autoAck = false;
      GetResponse response = channel.basicGet(QUEUE_NAME, autoAck);
      if (response == null) {
        return false;
        // No message retrieved.
      } else {
        AMQP.BasicProperties props = response.getProps();
        byte[] body = response.getBody();
        long deliveryTag = response.getEnvelope().getDeliveryTag();
        String _message = new String(body, "UTF-8");
        this.message = _message;
        channel.basicAck(deliveryTag, false); // acknowledge receipt of the message
        return true;
      }
    } catch (Exception e) {
      Log.d("", "RabbitMQ ERROR: " + e.getClass().getName());
      
    }
    return false;
  }
}
