package com.creative.roboticcameraapp.database;

/**
 * Created by comsol on 02-Jun-16.
 */
public class DbConfig {
    public static final String DB_NAME = "robotic_camera_database.db";
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
    public static final String NUMBER_OF_BRACKETED_SHOT = "number_of_bracketed_shots";
    public static final String BRACKETING_STYLE = "bracketing_style";
    public static final String[] SingleRowColumnName = {ID, SINGLE_ROW_NAME, NUMBER_OF_SHOOTING_POSITION, NUMBER_OF_BRACKETED_SHOT, BRACKETING_STYLE};
    public static final String[] SingleRowColumnType = {"INTEGER", "TEXT", "INTEGER", "INTEGER", "TEXT"};
    public static final String[] SingleRowColumnProperty = {" PRIMARY KEY autoincrement", "", "", "", ""};


    public static final String TABLE_MULTI_ROW = "multi_row_threesixty";

    public static final String MULTI_ROW_NAME = "multi_row_name";
    public static final String NUMBER_OF_ROWS = "number_of_rows";
    public static final String ELEVATIOn = "elevation";
    public static final String POSITION = "position";
    public static final String DIRECTION = "direction";
    // public static final String NUMBER_OF_BRACKETED_SHOT = "number_of_bracketed_shot";
    // public static final String BRACKETING_STYLE = "bracketing_style";
    public static final String AFTER_SHOT_DELAY = "after_shot_delay";
    public static final String STARTUP_DELAY = "start_up_delay";

    public static final String[] MultiRowColumnName = {ID, MULTI_ROW_NAME, NUMBER_OF_ROWS, ELEVATIOn, POSITION, DIRECTION, NUMBER_OF_BRACKETED_SHOT, BRACKETING_STYLE
            , AFTER_SHOT_DELAY, STARTUP_DELAY};
    public static final String[] MultiRowColumnType = {"INTEGER", "TEXT", "INTEGER", "TEXT", "TEXT", "TEXT", "INTEGER", "TEXT", "INTEGER", "INTEGER"};
    public static final String[] MultiRowColumnProperty = {" PRIMARY KEY autoincrement", "", "", "", "", "", "", "", "", ""};


    public static final String TABLE_PARTIAL_ROW = "partial_gigapixel";
    public static final String PARTIAL_ROW_NAME = "partial_row_name";
    public static final String PARTIAL_CAMERA_NAME = "camera_name";
    public static final String PARTIAL_LENS_NAME = "lens_name";
    public static final String PARTIAL_OVERLAP = "overlap";
    public static final String[] PartialColumnName = {ID, PARTIAL_ROW_NAME, PARTIAL_CAMERA_NAME, PARTIAL_LENS_NAME, PARTIAL_OVERLAP};
    public static final String[] PartialColumnType = {"INTEGER", "TEXT", "TEXT", "TEXT", "INTEGER"};
    public static final String[] PartialColumnProperty = {" PRIMARY KEY autoincrement", "", "", "", ""};








    public static final String[] TABLE_NAMES = {TABLE_CAMERA, TABLE_LENS, TABLE_SINGLE_ROW, TABLE_MULTI_ROW, TABLE_PARTIAL_ROW};
    public static final String[][] TABLE_ITEM = {CameraColumnName, LensColumnName, SingleRowColumnName, MultiRowColumnName,PartialColumnName};
    public static final String[][] TABLE_TYPE = {CameraColumnType, LensColumnType, SingleRowColumnType, MultiRowColumnType,PartialColumnType};
    public static final String[][] TABLE_PROPERTY = {CameraColumnProperty, LensColumnProperty, SingleRowColumnProperty, MultiRowColumnProperty,PartialColumnProperty};

}
