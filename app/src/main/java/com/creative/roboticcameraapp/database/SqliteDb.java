package com.creative.roboticcameraapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.creative.roboticcameraapp.model.Camera;
import com.creative.roboticcameraapp.model.Lens;
import com.creative.roboticcameraapp.model.MultiRow;
import com.creative.roboticcameraapp.model.Partial;
import com.creative.roboticcameraapp.model.SingleRow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fahad_000 on 7/8/2015.
 */
public class SqliteDb extends SQLiteOpenHelper {


    SQLiteDatabase db;

    private final Context mContext;


    public SqliteDb(Context context) {
        super(context, DbConfig.DB_NAME, null, DbConfig.DB_VERSION);

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DbConfig.DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DbConfig.DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        this.mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, DbConfig.TABLE_NAMES, DbConfig.TABLE_ITEM, DbConfig.TABLE_TYPE, DbConfig.TABLE_PROPERTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int current_version) {

    }

    public void createDataBase() throws IOException {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                Log.e("DEBUG", "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase() {
        File dbFile = new File(DbConfig.DB_PATH + DbConfig.DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DbConfig.DB_NAME);
        String outFileName = DbConfig.DB_PATH + DbConfig.DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }


    public void open() {
        db = this.getWritableDatabase();
    }

    //Open the database, so we can query it
    public void openDataBaseAfterCopy() throws SQLException {
        String mPath = DbConfig.DB_PATH + DbConfig.DB_NAME;
        //Log.v("mPath", mPath);
        SQLiteDatabase temp = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        temp.close();
        //mDataBase.close();
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        //mDataBase = this.getWritableDatabase();
    }

    public void executeQuery(String query) {
        db.execSQL(query);
    }


    /******************************************
     * CAMERA
     ************************************/


    public void addCamera(Camera camera) {
        if (db == null) {
            db = getWritableDatabase();
        }

        ContentValues values = new ContentValues();
        values.put(DbConfig.CAMERA_NAME, camera.getCameraName());
        values.put(DbConfig.CAMERA_SENSOR_WIDTH, camera.getSensorWidth());
        values.put(DbConfig.CAMERA_SENSOR_HEIGHT, camera.getSensorHeight());
        db.insert(DbConfig.TABLE_CAMERA, null, values);
    }

    public void updateCamera(Camera camera) {
        if (db == null) {
            db = getWritableDatabase();
        }

        ContentValues values = new ContentValues();
        values.put(DbConfig.CAMERA_NAME, camera.getCameraName());
        values.put(DbConfig.CAMERA_SENSOR_WIDTH, camera.getSensorWidth());
        values.put(DbConfig.CAMERA_SENSOR_HEIGHT, camera.getSensorHeight());
        db.update(DbConfig.TABLE_CAMERA, values, "id=" + camera.getId(), null);
    }

    public Camera getCamera(int id) {
        Camera camera = null;
        if (db == null) {
            db = getReadableDatabase();
        }
        String sql = "select * from " + DbConfig.TABLE_CAMERA + " where " + DbConfig.ID + "=" + id;
        Cursor c = db.rawQuery(sql, null);
        if (c != null && c.moveToFirst()) {
            camera = new Camera();
            camera.setId(c.getInt(c.getColumnIndex(DbConfig.ID)));
            camera.setCameraName(c.getString(c.getColumnIndex(DbConfig.CAMERA_NAME)));
            camera.setSensorWidth(c.getDouble(c.getColumnIndex(DbConfig.CAMERA_SENSOR_WIDTH)));
            camera.setSensorHeight(c.getDouble(c.getColumnIndex(DbConfig.CAMERA_SENSOR_HEIGHT)));
        }
        return camera;
    }

    public void deleteCamera(int id) {
        if (db == null) {
            db = getReadableDatabase();
        }
        db.delete(DbConfig.TABLE_CAMERA, DbConfig.ID + "=" + id, null);
    }

    public List<Camera> getAllCamera() {
        if (db == null) {
            db = getWritableDatabase();
        }
        List<Camera> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + DbConfig.TABLE_CAMERA, null);
        if (c != null && c.moveToFirst()) {
            do {
                Camera camera = getCamera(c.getInt(c.getColumnIndex(DbConfig.ID)));
                list.add(camera);
            } while (c.moveToNext());
        }
        return list;
    }


    /******************************************
     * LENS
     ************************************/

    public void addLens(Lens lens) {
        if (db == null) {
            db = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        values.put(DbConfig.LENS_NAME, lens.getLensName());
        values.put(DbConfig.LENS_FOCAL_LENGTH, lens.getFocalLength());
        values.put(DbConfig.LENS_FISHEYE_STATUS, lens.isFishEyeStatus());
        db.insert(DbConfig.TABLE_LENS, null, values);
    }

    public void updateLens(Lens lens) {
        if (db == null) {
            db = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        values.put(DbConfig.LENS_NAME, lens.getLensName());
        values.put(DbConfig.LENS_FOCAL_LENGTH, lens.getFocalLength());
        values.put(DbConfig.LENS_FISHEYE_STATUS, lens.isFishEyeStatus());
        db.update(DbConfig.TABLE_LENS, values, "id=" + lens.getId(), null);
    }


    public Lens getLens(int id) {
        Lens lens = null;
        if (db == null) {
            db = getReadableDatabase();
        }
        String sql = "select * from " + DbConfig.TABLE_LENS + " where id=" + id;
        Cursor c = db.rawQuery(sql, null);
        if (c != null && c.moveToFirst()) {
            lens = new Lens();
            lens.setId(c.getInt(c.getColumnIndex(DbConfig.ID)));
            lens.setLensName(c.getString(c.getColumnIndex(DbConfig.LENS_NAME)));
            lens.setFocalLength(c.getDouble(c.getColumnIndex(DbConfig.LENS_FOCAL_LENGTH)));
            lens.setFishEyeStatus(c.getInt(c.getColumnIndex(DbConfig.LENS_FISHEYE_STATUS)) == 0 ? false : true);
        }
        return lens;
    }

    public List<Lens> getAllLenses() {
        if (db == null) {
            db = getReadableDatabase();
        }
        List<Lens> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + DbConfig.TABLE_LENS, null);
        if (c != null && c.moveToFirst()) {
            do {
                Lens lens = getLens(c.getInt(c.getColumnIndex(DbConfig.ID)));
                list.add(lens);
            } while (c.moveToNext());
        }
        return list;
    }

    public void deleteLens(int id) {
        if (db == null) {
            db = getReadableDatabase();
        }
        db.delete(DbConfig.TABLE_LENS, "id=" + id, null);
    }


    /******************************************
     * SINGLE ROW 360
     ************************************/


    public void addSingleRow(SingleRow singleRow) {
        if (db == null) {
            db = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        values.put(DbConfig.SINGLE_ROW_NAME, singleRow.getSingleRowName());
        values.put(DbConfig.NUMBER_OF_SHOOTING_POSITION, singleRow.getNumber_of_shooting_position());
        values.put(DbConfig.CONTINUOUS_ROTATION, singleRow.getContinuous_rotation());
        values.put(DbConfig.NUMBER_OF_BRACKETED_SHOT, singleRow.getNum_of_bracketed_shot());
        values.put(DbConfig.BRACKETING_STYLE, singleRow.getBracketed_style());
        values.put(DbConfig.AFTER_SHOT_DELAY, singleRow.getAfter_shot_delay());
        values.put(DbConfig.STARTUP_DELAY, singleRow.getStartup_delay());
        values.put(DbConfig.FOCUS_DELAY, singleRow.getFocus_delay());
        values.put(DbConfig.BEFORE_SHOT_DELAY, singleRow.getBefore_shot_delay());
        values.put(DbConfig.DIRECTION, singleRow.getDirection());
        values.put(DbConfig.SPEED, singleRow.getSpeed());
        values.put(DbConfig.ACCELERATION, singleRow.getAcceleration());
        values.put(DbConfig.MAX_FRAME_RATE, singleRow.getMax_frame_rate());
        values.put(DbConfig.NUM_OF_PANORAMAS, singleRow.getNum_of_panoramas());
        values.put(DbConfig.DELAY_BETWEEN_PANORAMAS, singleRow.getDelay_between_panoramas());
        values.put(DbConfig.SHUTTER_SIGNAL_LENGTH, singleRow.getShutter_signal_length());
        values.put(DbConfig.FOCUS_SIGNAL_LENGTH, singleRow.getFocus_signal_length());
        values.put(DbConfig.CAMERA_WAKEUP, singleRow.getCamera_wakeup());
        values.put(DbConfig.CAMERA_WAKEUP_SIGNAL_LENGTH, singleRow.getCamera_wakeup_signal_length());
        values.put(DbConfig.CAMERA_WAKEUP_DELAY, singleRow.getCamera_wakeup_delay());
        values.put(DbConfig.TILT_ARM_ELEVATION, singleRow.getTilt_arm_elevation());
        values.put(DbConfig.CONTINOUS_ROTATION_SHUTTER, singleRow.getContinuosRotationShutter());
        values.put(DbConfig.RETURN_TO_START, singleRow.getReturn_to_start());
        values.put(DbConfig.PANORAMA_WIDTH, singleRow.getPanorama_width());

        db.insert(DbConfig.TABLE_SINGLE_ROW, null, values);
    }

    public void updateSingleRow(SingleRow singleRow) {
        if (db == null) {
            db = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        values.put(DbConfig.SINGLE_ROW_NAME, singleRow.getSingleRowName());
        values.put(DbConfig.NUMBER_OF_SHOOTING_POSITION, singleRow.getNumber_of_shooting_position());
        values.put(DbConfig.CONTINUOUS_ROTATION, singleRow.getContinuous_rotation());
        values.put(DbConfig.NUMBER_OF_BRACKETED_SHOT, singleRow.getNum_of_bracketed_shot());
        values.put(DbConfig.BRACKETING_STYLE, singleRow.getBracketed_style());
        values.put(DbConfig.AFTER_SHOT_DELAY, singleRow.getAfter_shot_delay());
        values.put(DbConfig.STARTUP_DELAY, singleRow.getStartup_delay());
        values.put(DbConfig.FOCUS_DELAY, singleRow.getFocus_delay());
        values.put(DbConfig.BEFORE_SHOT_DELAY, singleRow.getBefore_shot_delay());
        values.put(DbConfig.DIRECTION, singleRow.getDirection());
        values.put(DbConfig.SPEED, singleRow.getSpeed());
        values.put(DbConfig.ACCELERATION, singleRow.getAcceleration());
        values.put(DbConfig.MAX_FRAME_RATE, singleRow.getMax_frame_rate());
        values.put(DbConfig.NUM_OF_PANORAMAS, singleRow.getNum_of_panoramas());
        values.put(DbConfig.DELAY_BETWEEN_PANORAMAS, singleRow.getDelay_between_panoramas());
        values.put(DbConfig.SHUTTER_SIGNAL_LENGTH, singleRow.getShutter_signal_length());
        values.put(DbConfig.FOCUS_SIGNAL_LENGTH, singleRow.getFocus_signal_length());
        values.put(DbConfig.CAMERA_WAKEUP, singleRow.getCamera_wakeup());
        values.put(DbConfig.CAMERA_WAKEUP_SIGNAL_LENGTH, singleRow.getCamera_wakeup_signal_length());
        values.put(DbConfig.CAMERA_WAKEUP_DELAY, singleRow.getCamera_wakeup_delay());
        values.put(DbConfig.TILT_ARM_ELEVATION, singleRow.getTilt_arm_elevation());
        values.put(DbConfig.CONTINOUS_ROTATION_SHUTTER, singleRow.getContinuosRotationShutter());
        values.put(DbConfig.RETURN_TO_START, singleRow.getReturn_to_start());
        values.put(DbConfig.PANORAMA_WIDTH, singleRow.getPanorama_width());
        db.update(DbConfig.TABLE_SINGLE_ROW, values, "id=" + singleRow.getId(), null);
    }


    public SingleRow getSingleRow(int id) {
        SingleRow singleRow = null;
        if (db == null) {
            db = getReadableDatabase();
        }
        String sql = "select * from " + DbConfig.TABLE_SINGLE_ROW + " where id=" + id;
        Cursor c = db.rawQuery(sql, null);
        if (c != null && c.moveToFirst()) {
            singleRow = new SingleRow();
            singleRow.setId(c.getInt(c.getColumnIndex(DbConfig.ID)));
            singleRow.setSigleRowName(c.getString(c.getColumnIndex(DbConfig.SINGLE_ROW_NAME)));
            singleRow.setNumber_of_shooting_position(c.getInt(c.getColumnIndex(DbConfig.NUMBER_OF_SHOOTING_POSITION)));
            singleRow.setContinuous_rotation(c.getInt(c.getColumnIndex(DbConfig.CONTINUOUS_ROTATION)));
            singleRow.setNum_of_bracketed_shot(c.getInt(c.getColumnIndex(DbConfig.NUMBER_OF_BRACKETED_SHOT)));
            singleRow.setBracketed_style(c.getString(c.getColumnIndex(DbConfig.BRACKETING_STYLE)));
            singleRow.setAfter_shot_delay(c.getInt(c.getColumnIndex(DbConfig.AFTER_SHOT_DELAY)));
            singleRow.setStartup_delay(c.getInt(c.getColumnIndex(DbConfig.STARTUP_DELAY)));
            singleRow.setFocus_delay(c.getInt(c.getColumnIndex(DbConfig.FOCUS_DELAY)));
            singleRow.setBefore_shot_delay(c.getInt(c.getColumnIndex(DbConfig.BEFORE_SHOT_DELAY)));
            singleRow.setDirection(c.getString(c.getColumnIndex(DbConfig.DIRECTION)));
            singleRow.setSpeed(c.getInt(c.getColumnIndex(DbConfig.SPEED)));
            singleRow.setAcceleration(c.getInt(c.getColumnIndex(DbConfig.ACCELERATION)));
            singleRow.setMax_frame_rate(c.getFloat(c.getColumnIndex(DbConfig.MAX_FRAME_RATE)));
            singleRow.setNum_of_panoramas(c.getInt(c.getColumnIndex(DbConfig.NUM_OF_PANORAMAS)));
            singleRow.setDelay_between_panoramas(c.getInt(c.getColumnIndex(DbConfig.DELAY_BETWEEN_PANORAMAS)));
            singleRow.setShutter_signal_length(c.getInt(c.getColumnIndex(DbConfig.SHUTTER_SIGNAL_LENGTH)));
            singleRow.setFocus_signal_length(c.getInt(c.getColumnIndex(DbConfig.FOCUS_SIGNAL_LENGTH)));
            singleRow.setCamera_wakeup(c.getInt(c.getColumnIndex(DbConfig.CAMERA_WAKEUP)));
            singleRow.setCamera_wakeup_signal_length(c.getInt(c.getColumnIndex(DbConfig.CAMERA_WAKEUP_SIGNAL_LENGTH)));
            singleRow.setCamera_wakeup_delay(c.getInt(c.getColumnIndex(DbConfig.CAMERA_WAKEUP_DELAY)));
            singleRow.setTilt_arm_elevation(c.getFloat(c.getColumnIndex(DbConfig.TILT_ARM_ELEVATION)));
            singleRow.setReturn_to_start(c.getString(c.getColumnIndex(DbConfig.RETURN_TO_START)));
            singleRow.setContinuosRotationShutter(c.getString(c.getColumnIndex(DbConfig.CONTINOUS_ROTATION_SHUTTER)));
            singleRow.setPanorama_width(c.getInt(c.getColumnIndex(DbConfig.PANORAMA_WIDTH)));
            singleRow.setCreated_at(c.getString(c.getColumnIndex(DbConfig.CREATED_AT)));

            Log.d("DEBUG",c.getString(c.getColumnIndex(DbConfig.CREATED_AT)));
        }
        return singleRow;
    }

    public List<SingleRow> getAllSingleRow() {
        if (db == null) {
            db = getReadableDatabase();
        }
        List<SingleRow> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + DbConfig.TABLE_SINGLE_ROW, null);
        if (c != null && c.moveToFirst()) {
            do {
                SingleRow singleRow = getSingleRow(c.getInt(c.getColumnIndex(DbConfig.ID)));
                list.add(singleRow);
            } while (c.moveToNext());
        }
        return list;
    }

    public void deleteSingleRow(int id) {
        if (db == null) {
            db = getReadableDatabase();
        }
        db.delete(DbConfig.TABLE_SINGLE_ROW, "id=" + id, null);
    }


    /******************************************
     * MULTI ROW 360
     ************************************/


    public void addMultiRow(MultiRow multiRow) {
        if (db == null) {
            db = getWritableDatabase();
        }
        ContentValues values = new ContentValues();

        values.put(DbConfig.MULTI_ROW_NAME, multiRow.getMultiRowName());
        values.put(DbConfig.NUMBER_OF_ROWS, multiRow.getNum_of_rows());
        values.put(DbConfig.ELEVATIOn, multiRow.getElevation());
        values.put(DbConfig.POSITION, multiRow.getPosition());
        values.put(DbConfig.CONTINUOUS_ROTATION, multiRow.getContinuous_rotation());
        values.put(DbConfig.NUMBER_OF_BRACKETED_SHOT, multiRow.getNum_of_bracketed_shot());
        values.put(DbConfig.BRACKETING_STYLE, multiRow.getBracketed_style());
        values.put(DbConfig.AFTER_SHOT_DELAY, multiRow.getAfter_shot_delay());
        values.put(DbConfig.STARTUP_DELAY, multiRow.getStartup_delay());
        values.put(DbConfig.FOCUS_DELAY, multiRow.getFocus_delay());
        values.put(DbConfig.BEFORE_SHOT_DELAY, multiRow.getBefore_shot_delay());
        values.put(DbConfig.DIRECTION, multiRow.getDirection());
        values.put(DbConfig.SPEED, multiRow.getSpeed());
        values.put(DbConfig.ACCELERATION, multiRow.getAcceleration());
        values.put(DbConfig.MAX_FRAME_RATE, multiRow.getMax_frame_rate());
        values.put(DbConfig.NUM_OF_PANORAMAS, multiRow.getNum_of_panoramas());
        values.put(DbConfig.DELAY_BETWEEN_PANORAMAS, multiRow.getDelay_between_panoramas());
        values.put(DbConfig.SHUTTER_SIGNAL_LENGTH, multiRow.getShutter_signal_length());
        values.put(DbConfig.FOCUS_SIGNAL_LENGTH, multiRow.getFocus_signal_length());
        values.put(DbConfig.CAMERA_WAKEUP, multiRow.getCamera_wakeup());
        values.put(DbConfig.CAMERA_WAKEUP_SIGNAL_LENGTH, multiRow.getCamera_wakeup_signal_length());
        values.put(DbConfig.CAMERA_WAKEUP_DELAY, multiRow.getCamera_wakeup_delay());
        values.put(DbConfig.PANORAMA_WIDTH, multiRow.getPanorama_width());
        values.put(DbConfig.RETURN_TO_START, multiRow.getReturn_to_start());
        values.put(DbConfig.CONTINOUS_ROTATION_SHUTTER, multiRow.getContinuosRotationShutter());
        db.insert(DbConfig.TABLE_MULTI_ROW, null, values);
    }

    public void updateMultiRow(MultiRow multiRow) {
        if (db == null) {
            db = getWritableDatabase();
        }
        ContentValues values = new ContentValues();

        values.put(DbConfig.MULTI_ROW_NAME, multiRow.getMultiRowName());
        values.put(DbConfig.NUMBER_OF_ROWS, multiRow.getNum_of_rows());
        values.put(DbConfig.ELEVATIOn, multiRow.getElevation());
        values.put(DbConfig.POSITION, multiRow.getPosition());
        values.put(DbConfig.CONTINUOUS_ROTATION, multiRow.getContinuous_rotation());
        values.put(DbConfig.NUMBER_OF_BRACKETED_SHOT, multiRow.getNum_of_bracketed_shot());
        values.put(DbConfig.BRACKETING_STYLE, multiRow.getBracketed_style());
        values.put(DbConfig.AFTER_SHOT_DELAY, multiRow.getAfter_shot_delay());
        values.put(DbConfig.STARTUP_DELAY, multiRow.getStartup_delay());
        values.put(DbConfig.FOCUS_DELAY, multiRow.getFocus_delay());
        values.put(DbConfig.BEFORE_SHOT_DELAY, multiRow.getBefore_shot_delay());
        values.put(DbConfig.DIRECTION, multiRow.getDirection());
        values.put(DbConfig.SPEED, multiRow.getSpeed());
        values.put(DbConfig.ACCELERATION, multiRow.getAcceleration());
        values.put(DbConfig.MAX_FRAME_RATE, multiRow.getMax_frame_rate());
        values.put(DbConfig.NUM_OF_PANORAMAS, multiRow.getNum_of_panoramas());
        values.put(DbConfig.DELAY_BETWEEN_PANORAMAS, multiRow.getDelay_between_panoramas());
        values.put(DbConfig.SHUTTER_SIGNAL_LENGTH, multiRow.getShutter_signal_length());
        values.put(DbConfig.FOCUS_SIGNAL_LENGTH, multiRow.getFocus_signal_length());
        values.put(DbConfig.CAMERA_WAKEUP, multiRow.getCamera_wakeup());
        values.put(DbConfig.CAMERA_WAKEUP_SIGNAL_LENGTH, multiRow.getCamera_wakeup_signal_length());
        values.put(DbConfig.CAMERA_WAKEUP_DELAY, multiRow.getCamera_wakeup_delay());
        values.put(DbConfig.PANORAMA_WIDTH, multiRow.getPanorama_width());
        values.put(DbConfig.RETURN_TO_START, multiRow.getReturn_to_start());
        values.put(DbConfig.CONTINOUS_ROTATION_SHUTTER, multiRow.getContinuosRotationShutter());
        db.update(DbConfig.TABLE_MULTI_ROW, values, "id=" + multiRow.getId(), null);
    }


    public MultiRow getMultiRow(int id) {
        MultiRow multiRow = null;
        if (db == null) {
            db = getReadableDatabase();
        }
        String sql = "select * from " + DbConfig.TABLE_MULTI_ROW + " where id=" + id;
        Cursor c = db.rawQuery(sql, null);
        if (c != null && c.moveToFirst()) {
            multiRow = new MultiRow();
            multiRow.setId(c.getInt(c.getColumnIndex(DbConfig.ID)));
            multiRow.setMultiRowName(c.getString(c.getColumnIndex(DbConfig.MULTI_ROW_NAME)));
            multiRow.setNum_of_rows(c.getInt(c.getColumnIndex(DbConfig.NUMBER_OF_ROWS)));
            multiRow.setElevation(c.getString(c.getColumnIndex(DbConfig.ELEVATIOn)));
            multiRow.setPosition(c.getString(c.getColumnIndex(DbConfig.POSITION)));
            multiRow.setContinuous_rotation(c.getInt(c.getColumnIndex(DbConfig.CONTINUOUS_ROTATION)));
            multiRow.setNum_of_bracketed_shot(c.getInt(c.getColumnIndex(DbConfig.NUMBER_OF_BRACKETED_SHOT)));
            multiRow.setBracketed_style(c.getString(c.getColumnIndex(DbConfig.BRACKETING_STYLE)));
            multiRow.setAfter_shot_delay(c.getInt(c.getColumnIndex(DbConfig.AFTER_SHOT_DELAY)));
            multiRow.setStartup_delay(c.getInt(c.getColumnIndex(DbConfig.STARTUP_DELAY)));
            multiRow.setFocus_delay(c.getInt(c.getColumnIndex(DbConfig.FOCUS_DELAY)));
            multiRow.setBefore_shot_delay(c.getInt(c.getColumnIndex(DbConfig.BEFORE_SHOT_DELAY)));
            multiRow.setDirection(c.getString(c.getColumnIndex(DbConfig.DIRECTION)));
            multiRow.setSpeed(c.getInt(c.getColumnIndex(DbConfig.SPEED)));
            multiRow.setAcceleration(c.getInt(c.getColumnIndex(DbConfig.ACCELERATION)));
            multiRow.setMax_frame_rate(c.getFloat(c.getColumnIndex(DbConfig.MAX_FRAME_RATE)));
            multiRow.setNum_of_panoramas(c.getInt(c.getColumnIndex(DbConfig.NUM_OF_PANORAMAS)));
            multiRow.setDelay_between_panoramas(c.getInt(c.getColumnIndex(DbConfig.DELAY_BETWEEN_PANORAMAS)));
            multiRow.setShutter_signal_length(c.getInt(c.getColumnIndex(DbConfig.SHUTTER_SIGNAL_LENGTH)));
            multiRow.setFocus_signal_length(c.getInt(c.getColumnIndex(DbConfig.FOCUS_SIGNAL_LENGTH)));
            multiRow.setCamera_wakeup(c.getInt(c.getColumnIndex(DbConfig.CAMERA_WAKEUP)));
            multiRow.setCamera_wakeup_signal_length(c.getInt(c.getColumnIndex(DbConfig.CAMERA_WAKEUP_SIGNAL_LENGTH)));
            multiRow.setCamera_wakeup_delay(c.getInt(c.getColumnIndex(DbConfig.CAMERA_WAKEUP_DELAY)));
            multiRow.setReturn_to_start(c.getString(c.getColumnIndex(DbConfig.RETURN_TO_START)));
            multiRow.setPanorama_width(c.getInt(c.getColumnIndex(DbConfig.PANORAMA_WIDTH)));
            multiRow.setContinuosRotationShutter(c.getString(c.getColumnIndex(DbConfig.CONTINOUS_ROTATION_SHUTTER)));
            multiRow.setCreated_at(c.getString(c.getColumnIndex(DbConfig.CREATED_AT)));

            Log.d("DEBUG",c.getString(c.getColumnIndex(DbConfig.CREATED_AT)));
        }
        return multiRow;
    }

    public List<MultiRow> getAllMultiRow() {
        if (db == null) {
            db = getReadableDatabase();
        }
        List<MultiRow> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + DbConfig.TABLE_MULTI_ROW, null);
        if (c != null && c.moveToFirst()) {
            do {
                MultiRow multiRow = getMultiRow(c.getInt(c.getColumnIndex(DbConfig.ID)));
                list.add(multiRow);
            } while (c.moveToNext());
        }
        return list;
    }

    public void deleteMultiRow(int id) {
        if (db == null) {
            db = getReadableDatabase();
        }
        db.delete(DbConfig.TABLE_MULTI_ROW, "id=" + id, null);
    }


    /******************************************
     * PARTIAL/GIGAPEXEL
     ************************************/


    public void addPartialGigapixel(Partial partial) {
        if (db == null) {
            db = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        values.put(DbConfig.PARTIAL_ROW_NAME, partial.getPartialName());
        values.put(DbConfig.PARTIAL_CAMERA_NAME, partial.getCameraName());
        values.put(DbConfig.PARTIAL_LENS_NAME, partial.getLensName());
        values.put(DbConfig.PARTIAL_OVERLAP, partial.getOverlap());
        values.put(DbConfig.CONTINUOUS_ROTATION, partial.getContinuous_rotation());
        values.put(DbConfig.NUMBER_OF_BRACKETED_SHOT, partial.getNum_of_bracketed_shot());
        values.put(DbConfig.BRACKETING_STYLE, partial.getBracketed_style());
        values.put(DbConfig.AFTER_SHOT_DELAY, partial.getAfter_shot_delay());
        values.put(DbConfig.STARTUP_DELAY, partial.getStartup_delay());
        values.put(DbConfig.FOCUS_DELAY, partial.getFocus_delay());
        values.put(DbConfig.BEFORE_SHOT_DELAY, partial.getBefore_shot_delay());
        values.put(DbConfig.DIRECTION, partial.getDirection());
        values.put(DbConfig.SPEED, partial.getSpeed());
        values.put(DbConfig.ACCELERATION, partial.getAcceleration());
        values.put(DbConfig.MAX_FRAME_RATE, partial.getMax_frame_rate());
        values.put(DbConfig.NUM_OF_PANORAMAS, partial.getNum_of_panoramas());
        values.put(DbConfig.DELAY_BETWEEN_PANORAMAS, partial.getDelay_between_panoramas());
        values.put(DbConfig.SHUTTER_SIGNAL_LENGTH, partial.getShutter_signal_length());
        values.put(DbConfig.FOCUS_SIGNAL_LENGTH, partial.getFocus_signal_length());
        values.put(DbConfig.CAMERA_WAKEUP, partial.getCamera_wakeup());
        values.put(DbConfig.CAMERA_WAKEUP_SIGNAL_LENGTH, partial.getCamera_wakeup_signal_length());
        values.put(DbConfig.CAMERA_WAKEUP_DELAY, partial.getCamera_wakeup_delay());

        values.put(DbConfig.RETURN_TO_START, partial.getReturn_to_start());
        values.put(DbConfig.CONTINOUS_ROTATION_SHUTTER, partial.getContinuosRotationShutter());
        db.insert(DbConfig.TABLE_PARTIAL_ROW, null, values);
    }

    public void updatePartialGigapixel(Partial partial) {
        if (db == null) {
            db = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        values.put(DbConfig.PARTIAL_ROW_NAME, partial.getPartialName());
        values.put(DbConfig.PARTIAL_CAMERA_NAME, partial.getCameraName());
        values.put(DbConfig.PARTIAL_LENS_NAME, partial.getLensName());
        values.put(DbConfig.PARTIAL_OVERLAP, partial.getOverlap());
        values.put(DbConfig.CONTINUOUS_ROTATION, partial.getContinuous_rotation());
        values.put(DbConfig.NUMBER_OF_BRACKETED_SHOT, partial.getNum_of_bracketed_shot());
        values.put(DbConfig.BRACKETING_STYLE, partial.getBracketed_style());
        values.put(DbConfig.AFTER_SHOT_DELAY, partial.getAfter_shot_delay());
        values.put(DbConfig.STARTUP_DELAY, partial.getStartup_delay());
        values.put(DbConfig.FOCUS_DELAY, partial.getFocus_delay());
        values.put(DbConfig.BEFORE_SHOT_DELAY, partial.getBefore_shot_delay());
        values.put(DbConfig.DIRECTION, partial.getDirection());
        values.put(DbConfig.SPEED, partial.getSpeed());
        values.put(DbConfig.ACCELERATION, partial.getAcceleration());
        values.put(DbConfig.MAX_FRAME_RATE, partial.getMax_frame_rate());
        values.put(DbConfig.NUM_OF_PANORAMAS, partial.getNum_of_panoramas());
        values.put(DbConfig.DELAY_BETWEEN_PANORAMAS, partial.getDelay_between_panoramas());
        values.put(DbConfig.SHUTTER_SIGNAL_LENGTH, partial.getShutter_signal_length());
        values.put(DbConfig.FOCUS_SIGNAL_LENGTH, partial.getFocus_signal_length());
        values.put(DbConfig.CAMERA_WAKEUP, partial.getCamera_wakeup());
        values.put(DbConfig.CAMERA_WAKEUP_SIGNAL_LENGTH, partial.getCamera_wakeup_signal_length());
        values.put(DbConfig.CAMERA_WAKEUP_DELAY, partial.getCamera_wakeup_delay());
        values.put(DbConfig.RETURN_TO_START, partial.getReturn_to_start());
        values.put(DbConfig.CONTINOUS_ROTATION_SHUTTER, partial.getContinuosRotationShutter());
        db.update(DbConfig.TABLE_PARTIAL_ROW, values, "id=" + partial.getId(), null);
    }


    public Partial getPartialGigapixel(int id) {
        Partial partial = null;
        if (db == null) {
            db = getReadableDatabase();
        }
        String sql = "select * from " + DbConfig.TABLE_PARTIAL_ROW + " where id=" + id;
        Cursor c = db.rawQuery(sql, null);
        if (c != null && c.moveToFirst()) {
            partial = new Partial();
            partial.setId(c.getInt(c.getColumnIndex(DbConfig.ID)));
            partial.setPartialName(c.getString(c.getColumnIndex(DbConfig.PARTIAL_ROW_NAME)));
            partial.setCameraName(c.getString(c.getColumnIndex(DbConfig.PARTIAL_CAMERA_NAME)));
            partial.setLensName(c.getString(c.getColumnIndex(DbConfig.PARTIAL_LENS_NAME)));
            partial.setOverlap(c.getInt(c.getColumnIndex(DbConfig.PARTIAL_OVERLAP)));
            partial.setContinuous_rotation(c.getInt(c.getColumnIndex(DbConfig.CONTINUOUS_ROTATION)));
            partial.setNum_of_bracketed_shot(c.getInt(c.getColumnIndex(DbConfig.NUMBER_OF_BRACKETED_SHOT)));
            partial.setBracketed_style(c.getString(c.getColumnIndex(DbConfig.BRACKETING_STYLE)));
            partial.setAfter_shot_delay(c.getInt(c.getColumnIndex(DbConfig.AFTER_SHOT_DELAY)));
            partial.setStartup_delay(c.getInt(c.getColumnIndex(DbConfig.STARTUP_DELAY)));
            partial.setFocus_delay(c.getInt(c.getColumnIndex(DbConfig.FOCUS_DELAY)));
            partial.setBefore_shot_delay(c.getInt(c.getColumnIndex(DbConfig.BEFORE_SHOT_DELAY)));
            partial.setDirection(c.getString(c.getColumnIndex(DbConfig.DIRECTION)));
            partial.setSpeed(c.getInt(c.getColumnIndex(DbConfig.SPEED)));
            partial.setAcceleration(c.getInt(c.getColumnIndex(DbConfig.ACCELERATION)));
            partial.setMax_frame_rate(c.getInt(c.getColumnIndex(DbConfig.MAX_FRAME_RATE)));
            partial.setNum_of_panoramas(c.getInt(c.getColumnIndex(DbConfig.NUM_OF_PANORAMAS)));
            partial.setDelay_between_panoramas(c.getInt(c.getColumnIndex(DbConfig.DELAY_BETWEEN_PANORAMAS)));
            partial.setShutter_signal_length(c.getInt(c.getColumnIndex(DbConfig.SHUTTER_SIGNAL_LENGTH)));
            partial.setFocus_signal_length(c.getInt(c.getColumnIndex(DbConfig.FOCUS_SIGNAL_LENGTH)));
            partial.setCamera_wakeup(c.getInt(c.getColumnIndex(DbConfig.CAMERA_WAKEUP)));
            partial.setCamera_wakeup_signal_length(c.getInt(c.getColumnIndex(DbConfig.CAMERA_WAKEUP_SIGNAL_LENGTH)));
            partial.setCamera_wakeup_delay(c.getInt(c.getColumnIndex(DbConfig.CAMERA_WAKEUP_DELAY)));
            partial.setReturn_to_start(c.getInt(c.getColumnIndex(DbConfig.RETURN_TO_START)));
            partial.setContinuosRotationShutter(c.getString(c.getColumnIndex(DbConfig.CONTINOUS_ROTATION_SHUTTER)));
        }
        return partial;
    }

    public List<Partial> getAllPartialGigapixel() {
        if (db == null) {
            db = getReadableDatabase();
        }
        List<Partial> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + DbConfig.TABLE_PARTIAL_ROW, null);
        if (c != null && c.moveToFirst()) {
            do {
                Partial partial = getPartialGigapixel(c.getInt(c.getColumnIndex(DbConfig.ID)));
                list.add(partial);
            } while (c.moveToNext());
        }
        return list;
    }

    public void deletePartialGigapixel(int id) {
        if (db == null) {
            db = getReadableDatabase();
        }
        db.delete(DbConfig.TABLE_PARTIAL_ROW, "id=" + id, null);
    }


    public void createTable(SQLiteDatabase db, String[] TableName, String[][] tableItem, String[][] tableType, String[][] tableProperty) {
        String[] keyWithType = new String[TableName.length];
        for (int k = 0; k < TableName.length; k++) {
            for (int l = 0; l < tableItem[k].length; l++) {
                if (l != 0) {
                    keyWithType[k] = keyWithType[k] + ", " + tableItem[k][l] + " " + tableType[k][l] + tableProperty[k][l];
                } else {
                    keyWithType[k] = tableItem[k][l] + " " + tableType[k][l] + tableProperty[k][l];
                }
            }
        }
        for (int i = 0; i < TableName.length; i++) {
            String createTableQuery = "CREATE TABLE " + TableName[i] + " (" + keyWithType[i] + ")";
            db.execSQL(createTableQuery);
        }
    }


}

