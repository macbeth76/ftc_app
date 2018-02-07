package com.meisterdevs.ftc.mentor.loggingtester;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by burgera on 2/4/18.
 */

public class LoggingExample extends OpMode {
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);
    @Override
    public void init() {

    }

    @Override
    public void loop() {

        long startTime = System.nanoTime();
        logger.debug("Time",System.nanoTime());

    }
}
