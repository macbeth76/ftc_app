package com.meisterdevs.ftc.mentor;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

/**
 * Created by burgera on 1/1/18.
 */

class TeamUtil {
    /**
     * {@link #TEAM_FOLDER} is the root of the tree we use in non-volatile storage. Robot configurations
     * are stored in this folder.
     */
    public static final File TEAM_FOLDER = new File(AppUtil.ROOT_FOLDER + "/FIRSTTEAM/");

    /**
     * Dirctory in which .xml robot configurations should live
     */
    public static final File TEAM_FILES_DIR = TEAM_FOLDER;

    public static final String CONFIG_FILE = "config.json";
}
