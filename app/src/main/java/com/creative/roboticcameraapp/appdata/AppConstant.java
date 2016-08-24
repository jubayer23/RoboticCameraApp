package com.creative.roboticcameraapp.appdata;


import java.util.HashMap;


/**
 * Created by comsol on 11/8/2015.
 */
public class AppConstant {

    public static final String TAG = "RoboticCameraApp";

    public static final int REQUEST_CHECK_SETTINGS = 100;

    public static final String[] bracketing_style = {"Single Shot", "Continuous Short", "Continuous Long"};
    public static final int ASENDING = 1;
    public static final int DESENDING = 2;
    public static final int DATE = 3;

    public static HashMap<String,Integer> bracketing_style_map = new HashMap<>();

    public static final String[] direction = {"CW", "CCW"};

    public static final String[] return_to_start = {"Disable", "Quick","Backtrack"};

    public static final String[] contuNiousRotationShutterRelease = {"Controller", "Camera"};

    public static HashMap<String,Integer> direction_map = new HashMap<>();

    public static HashMap<String,Integer> return_to_start_map = new HashMap<>();

    public static HashMap<String,Integer> continuos_rotation_shutter_release_map = new HashMap<>();

    public static final boolean DEFAULT_CONTINUOUS_ROTATION = false;
    public static final int DEFAULT_AFTER_SHOT_DELAY = 800;
    public static final float DEFAULT_TILT_ARM_ELEVATION = 0;
    public static final int DEFAULT_NUM_OF_BRACKETED_SHOTS = 1;
    public static final int DEFAULT_STARTUP_DELAY = 0;
    public static final int DEFAULT_FOCUS_DELAY = 0;
    public static final int DEFAULT_BEFORE_SHOT_DELAY = 500;
    public static final int DEFAULT_SPEED = 15000;
    public static final int DEFAULT_ACCELERATION = 1500;
    public static final float DEFAULT_MAX_FRAME_RATE = 0;
    public static final int DEFAULT_NUM_OF_PANORAMAS = 1;
    public static final int DEFAULT_DELAY_BETWEEN_PANORAMAS = 0;
    public static final int DEFAULT_SHUTTER_SIGNAL_LENGTH = 300;
    public static final int DEFAULT_FOCUS_SIGNAL_LENGTH = 300;
    public static final boolean DEFAULT_CAMERA_WAKEUP = true;
    public static final int DEFAULT_CAMERA_WAKEUP_SIGNAL_LENGTH = 300;
    public static final int DEFAULT_CAMERA_WAKEUP_DELAY = 1000;
    public static final int DEFAULT_SPEED_DIVIDER = 1;
    public static final boolean DEFAULT_RETURN_TO_START = true;
    public static final int DEFAULT_PANORAMA_WIDTH = 360;




    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;
    public static final int LEFT = 4;

    public static final int ONRELEASE = 2;
    public static final int ONCLICK = 1;

}
