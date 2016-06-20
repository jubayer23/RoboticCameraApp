package com.creative.roboticcameraapp.appdata;


import java.util.HashMap;

import io.palaima.smoothbluetooth.SmoothBluetooth;

/**
 * Created by comsol on 11/8/2015.
 */
public class AppConstant {


    public static final int REQUEST_CHECK_SETTINGS = 100;

    public static final String[] bracketing_style = {"Single Shot", "Continuous Short", "Continuous Long"};

    public static HashMap<String,Integer> bracketing_style_map = new HashMap<>();

    public static final String[] direction = {"CW", "CCW"};

    public static HashMap<String,Integer> direction_map = new HashMap<>();

    public static final boolean DEFAULT_CONTINUOUS_ROTATION = false;
    public static final int DEFAULT_AFTER_SHOT_DELAY = 800;
    public static final int DEFAULT_NUM_OF_BRACKETED_SHOTS = 1;
    public static final int DEFAULT_STARTUP_DELAY = 0;
    public static final int DEFAULT_FOCUS_DELAY = 0;
    public static final int DEFAULT_BEFORE_SHOT_DELAY = 500;
    public static final int DEFAULT_SPEED = 12;
    public static final int DEFAULT_ACCELERATION = 1500;
    public static final int DEFAULT_MAX_FRAME_RATE = 0;
    public static final int DEFAULT_NUM_OF_PANORAMAS = 1;
    public static final int DEFAULT_DELAY_BETWEEN_PANORAMAS = 10;
    public static final int DEFAULT_SHUTTER_SIGNAL_LENGTH = 250;
    public static final int DEFAULT_FOCUS_SIGNAL_LENGTH = 250;
    public static final boolean DEFAULT_CAMERA_WAKEUP = true;
    public static final int DEFAULT_CAMERA_WAKEUP_SIGNAL_LENGTH = 300;
    public static final int DEFAULT_CAMERA_WAKEUP_DELAY = 1000;
    public static final int DEFAULT_SPEED_DIVIDER = 1;



    public static   SmoothBluetooth mSmoothBluetooth;

    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;
    public static final int LEFT = 4;

    public static final int ONRELEASE = 2;
    public static final int ONCLICK = 1;

}
