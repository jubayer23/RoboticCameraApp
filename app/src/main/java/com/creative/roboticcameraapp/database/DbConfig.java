package com.creative.roboticcameraapp.database;

/**
 * Created by comsol on 02-Jun-16.
 */
public class DbConfig {
    public static String DB_PATH = "";

    public static final String DB_NAME = "robotic_camera_database.db";
    public static final String DB_NAME_2 = "maestro_data";
    public static final int DB_VERSION = 1; //{1, 200 201, 202}


    public static final String ID = "id";


    public static final String TABLE_CAMERA = "camera";
    public static final String CAMERA_NAME = "camera_name";
    public static final String CAMERA_SENSOR_WIDTH = "sensor_width";
    public static final String CAMERA_SENSOR_HEIGHT = "sensor_height";
    public static final String[] CameraColumnName = {ID, CAMERA_NAME, CAMERA_SENSOR_WIDTH, CAMERA_SENSOR_HEIGHT};
    public static final String[] CameraColumnType = {"INTEGER", "TEXT", "REAL", "REAL"};
    public static final String[] CameraColumnProperty = {" PRIMARY KEY autoincrement", "", "", ""};


    public static final String TABLE_LENS = "lens";
    public static final String LENS_NAME = "lens_name";
    public static final String LENS_FOCAL_LENGTH = "focal_length";
    public static final String LENS_FISHEYE_STATUS = "fisheye_status";
    public static final String[] LensColumnName = {ID, LENS_NAME, LENS_FOCAL_LENGTH, LENS_FISHEYE_STATUS};
    public static final String[] LensColumnType = {"INTEGER", "TEXT", "REAL", "INTEGER"};
    public static final String[] LensColumnProperty = {" PRIMARY KEY autoincrement", "", "", ""};

    public static final String TABLE_SINGLE_ROW = "single_row_threesixty";

    public static final String SINGLE_ROW_NAME = "single_row_name";
    public static final String NUMBER_OF_SHOOTING_POSITION = "number_of_shooting_position";

    public static final String CONTINUOUS_ROTATION = "continuos_rotation";
    public static final String NUMBER_OF_BRACKETED_SHOT = "number_of_bracketed_shots";
    public static final String TILT_ARM_ELEVATION = "tilt_arm_elevation";
    public static final String RETURN_TO_START = "return_to_start";
    public static final String CONTINOUS_ROTATION_SHUTTER = "continuous_rotation_shutter";
    public static final String BRACKETING_STYLE = "bracketing_style";
    public static final String AFTER_SHOT_DELAY = "after_shot_delay";
    public static final String STARTUP_DELAY = "start_up_delay";
    public static final String FOCUS_DELAY = "focus_delay";
    public static final String BEFORE_SHOT_DELAY = "before_shot_delay";
    public static final String DIRECTION = "direction";
    public static final String SPEED = "speed";
    public static final String ACCELERATION = "acceleration";
    public static final String MAX_FRAME_RATE = "max_frame_rate";
    public static final String NUM_OF_PANORAMAS = "num_of_panoramas";
    public static final String DELAY_BETWEEN_PANORAMAS = "delay_between_panoramas";
    public static final String SHUTTER_SIGNAL_LENGTH = "shutter_signal_length";
    public static final String FOCUS_SIGNAL_LENGTH = "focus_signal_length";
    public static final String CAMERA_WAKEUP = "camera_wakeup";
    public static final String CAMERA_WAKEUP_SIGNAL_LENGTH = "camera_wakeup_signal_length";
    public static final String CAMERA_WAKEUP_DELAY = "camera_wakeup_delay";
    public static final String SPEED_DIVIDER = "speed_divider";
    public static final String PANORAMA_WIDTH = "panorama_width";
    public static final String CREATED_AT = "created_at";
    public static final String[] SingleRowColumnName = {ID, SINGLE_ROW_NAME, NUMBER_OF_SHOOTING_POSITION,
            CONTINUOUS_ROTATION, NUMBER_OF_BRACKETED_SHOT, BRACKETING_STYLE, AFTER_SHOT_DELAY, STARTUP_DELAY,
            FOCUS_DELAY, BEFORE_SHOT_DELAY, DIRECTION, SPEED, ACCELERATION, MAX_FRAME_RATE, NUM_OF_PANORAMAS,
            DELAY_BETWEEN_PANORAMAS, SHUTTER_SIGNAL_LENGTH, FOCUS_SIGNAL_LENGTH, CAMERA_WAKEUP,
            CAMERA_WAKEUP_SIGNAL_LENGTH, CAMERA_WAKEUP_DELAY,
            SPEED_DIVIDER,TILT_ARM_ELEVATION,RETURN_TO_START,
            CONTINOUS_ROTATION_SHUTTER,PANORAMA_WIDTH,CREATED_AT};
    public static final String[] SingleRowColumnType = {
            "INTEGER", "TEXT", "INTEGER", "INTEGER", "INTEGER", "TEXT", "INTEGER", "INTEGER", "INTEGER",
            "INTEGER", "TEXT", "INTEGER", "INTEGER", "REAL", "INTEGER", "INTEGER", "INTEGER", "INTEGER",
            "INTEGER", "INTEGER", "INTEGER", "INTEGER","REAL","TEXT","TEXT","INTEGER","DATETIME"};
    public static final String[] SingleRowColumnProperty = {" PRIMARY KEY autoincrement", "","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","","",""," DEFAULT CURRENT_TIMESTAMP"};


    public static final String TABLE_MULTI_ROW = "multi_row_threesixty";

    public static final String MULTI_ROW_NAME = "multi_row_name";
    public static final String NUMBER_OF_ROWS = "number_of_rows";
    public static final String ELEVATIOn = "elevation";
    public static final String POSITION = "position";
    //public static final String PANORAMA_WIDTH = "panorama_width";
    //public static final String CONTINUOUS_ROTATION = "continuos_rotation";
    // public static final String NUMBER_OF_BRACKETED_SHOT = "number_of_bracketed_shot";
    // public static final String BRACKETING_STYLE = "bracketing_style";
    //public static final String AFTER_SHOT_DELAY = "after_shot_delay";
    // public static final String STARTUP_DELAY = "start_up_delay";
    //public static final String RETURN_TO_START = "return_to_start";
    //public static final String CONTINOUS_ROTATION_SHUTTER = "continuous_rotation_shutter";

    public static final String[] MultiRowColumnName = {
            ID,
            MULTI_ROW_NAME,
            NUMBER_OF_ROWS,
            ELEVATIOn,
            POSITION,
            DIRECTION,
            CONTINUOUS_ROTATION,
            NUMBER_OF_BRACKETED_SHOT,
            BRACKETING_STYLE,
            AFTER_SHOT_DELAY,
            STARTUP_DELAY,
            FOCUS_DELAY,
            BEFORE_SHOT_DELAY,
            SPEED,
            ACCELERATION,
            MAX_FRAME_RATE,
            NUM_OF_PANORAMAS,
            DELAY_BETWEEN_PANORAMAS,
            SHUTTER_SIGNAL_LENGTH,
            FOCUS_SIGNAL_LENGTH,
            CAMERA_WAKEUP,
            CAMERA_WAKEUP_SIGNAL_LENGTH,
            CAMERA_WAKEUP_DELAY,
            SPEED_DIVIDER,
            PANORAMA_WIDTH,
            RETURN_TO_START,
            CONTINOUS_ROTATION_SHUTTER,
            CREATED_AT};
    public static final String[] MultiRowColumnType = {
            "INTEGER",
            "TEXT",
            "INTEGER",
            "TEXT",
            "TEXT",
            "TEXT",
            "INTEGER",
            "INTEGER",
            "TEXT",
            "INTEGER",
            "INTEGER",
            "INTEGER",
            "INTEGER",
            "INTEGER",
            "INTEGER",
            "INTEGER",
            "REAL",
            "INTEGER",
            "INTEGER",
            "INTEGER",
            "INTEGER",
            "INTEGER",
            "INTEGER",
            "INTEGER",
            "INTEGER",
            "TEXT",
            "TEXT",
            "DATETIME"};
    public static final String[] MultiRowColumnProperty = {" PRIMARY KEY autoincrement",
            "", "","","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""," DEFAULT CURRENT_TIMESTAMP"};


    public static final String TABLE_PARTIAL_ROW = "partial_gigapixel";
    public static final String PARTIAL_ROW_NAME = "partial_row_name";
    public static final String PARTIAL_CAMERA_NAME = "camera_name";
    public static final String PARTIAL_LENS_NAME = "lens_name";
    public static final String PARTIAL_OVERLAP = "overlap";
    public static final String[] PartialColumnName = {ID, PARTIAL_ROW_NAME, PARTIAL_CAMERA_NAME, PARTIAL_LENS_NAME,
            PARTIAL_OVERLAP,CONTINUOUS_ROTATION,
            NUMBER_OF_BRACKETED_SHOT, BRACKETING_STYLE, AFTER_SHOT_DELAY, STARTUP_DELAY,
            FOCUS_DELAY, BEFORE_SHOT_DELAY, DIRECTION, SPEED, ACCELERATION, MAX_FRAME_RATE, NUM_OF_PANORAMAS,
            DELAY_BETWEEN_PANORAMAS, SHUTTER_SIGNAL_LENGTH, FOCUS_SIGNAL_LENGTH, CAMERA_WAKEUP,
            CAMERA_WAKEUP_SIGNAL_LENGTH, CAMERA_WAKEUP_DELAY, SPEED_DIVIDER,RETURN_TO_START,
            CONTINOUS_ROTATION_SHUTTER,CREATED_AT};
    public static final String[] PartialColumnType = {"INTEGER", "TEXT", "TEXT", "TEXT", "INTEGER","INTEGER", "INTEGER", "TEXT", "INTEGER", "INTEGER", "INTEGER",
            "INTEGER", "TEXT", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER",
            "INTEGER", "INTEGER", "INTEGER", "INTEGER","INTEGER", "TEXT","DATETIME"};
    public static final String[] PartialColumnProperty = {" PRIMARY KEY autoincrement", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","", ""," DEFAULT CURRENT_TIMESTAMP"};


    public static final String[] TABLE_NAMES = {TABLE_CAMERA, TABLE_LENS, TABLE_SINGLE_ROW, TABLE_MULTI_ROW, TABLE_PARTIAL_ROW};
    public static final String[][] TABLE_ITEM = {CameraColumnName, LensColumnName, SingleRowColumnName, MultiRowColumnName, PartialColumnName};
    public static final String[][] TABLE_TYPE = {CameraColumnType, LensColumnType, SingleRowColumnType, MultiRowColumnType, PartialColumnType};
    public static final String[][] TABLE_PROPERTY = {CameraColumnProperty, LensColumnProperty, SingleRowColumnProperty, MultiRowColumnProperty, PartialColumnProperty};

}
