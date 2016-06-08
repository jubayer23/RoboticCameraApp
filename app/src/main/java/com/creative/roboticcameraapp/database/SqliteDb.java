package com.creative.roboticcameraapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.creative.roboticcameraapp.model.Camera;
import com.creative.roboticcameraapp.model.Lens;
import com.creative.roboticcameraapp.model.MultiRow;
import com.creative.roboticcameraapp.model.Partial;
import com.creative.roboticcameraapp.model.SingleRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fahad_000 on 7/8/2015.
 */
public class SqliteDb extends SQLiteOpenHelper {


    SQLiteDatabase db;

    public SqliteDb(Context context) {
        super(context, DbConfig.DB_NAME, null, DbConfig.DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, DbConfig.TABLE_NAMES, DbConfig.TABLE_ITEM, DbConfig.TABLE_TYPE, DbConfig.TABLE_PROPERTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int current_version) {

    }


    public void open() {
        db = this.getWritableDatabase();
    }

    public void executeQuery(String query) {
        db.execSQL(query);
    }




    /******************************************CAMERA************************************/


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


    /******************************************LENS************************************/

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


    /******************************************SINGLE ROW 360************************************/


    public void addSingleRow(SingleRow singleRow) {
        if (db == null) {
            db = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        values.put(DbConfig.SINGLE_ROW_NAME, singleRow.getSingleRowName());

        Log.d("DEBUG_db",singleRow.getSingleRowName());
        values.put(DbConfig.NUMBER_OF_SHOOTING_POSITION, singleRow.getNumber_of_shooting_position());
        values.put(DbConfig.NUMBER_OF_BRACKETED_SHOT, singleRow.getNumber_of_bracketed_shot());
        values.put(DbConfig.BRACKETING_STYLE, singleRow.getBracketed_style());
        db.insert(DbConfig.TABLE_SINGLE_ROW, null, values);
    }

    public void updateSingleRow(SingleRow singleRow) {
        if (db == null) {
            db = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        values.put(DbConfig.SINGLE_ROW_NAME, singleRow.getSingleRowName());
        values.put(DbConfig.NUMBER_OF_SHOOTING_POSITION, singleRow.getNumber_of_shooting_position());
        values.put(DbConfig.NUMBER_OF_BRACKETED_SHOT, singleRow.getNumber_of_bracketed_shot());
        values.put(DbConfig.BRACKETING_STYLE, singleRow.getBracketed_style());
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
            singleRow.setNumber_of_bracketed_shot(c.getInt(c.getColumnIndex(DbConfig.NUMBER_OF_BRACKETED_SHOT)));
            singleRow.setBracketed_style(c.getString(c.getColumnIndex(DbConfig.BRACKETING_STYLE)));
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



    /******************************************MULTI ROW 360************************************/


    public void addMultiRow(MultiRow multiRow) {
        if (db == null) {
            db = getWritableDatabase();
        }
        ContentValues values = new ContentValues();

        values.put(DbConfig.MULTI_ROW_NAME, multiRow.getMultiRowName());
        values.put(DbConfig.NUMBER_OF_ROWS, multiRow.getNum_of_rows());
        values.put(DbConfig.ELEVATIOn, multiRow.getElevation());
        values.put(DbConfig.POSITION, multiRow.getPosition());
        values.put(DbConfig.DIRECTION, multiRow.getDirection());
        values.put(DbConfig.NUMBER_OF_BRACKETED_SHOT, multiRow.getNum_of_bracketed_shot());
        values.put(DbConfig.BRACKETING_STYLE, multiRow.getBacketing_style());
        values.put(DbConfig.AFTER_SHOT_DELAY, multiRow.getAfter_shot_delay());
        values.put(DbConfig.STARTUP_DELAY, multiRow.getStartup_delay());
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
        values.put(DbConfig.DIRECTION, multiRow.getDirection());
        values.put(DbConfig.NUMBER_OF_BRACKETED_SHOT, multiRow.getNum_of_bracketed_shot());
        values.put(DbConfig.BRACKETING_STYLE, multiRow.getBacketing_style());
        values.put(DbConfig.AFTER_SHOT_DELAY, multiRow.getAfter_shot_delay());
        values.put(DbConfig.STARTUP_DELAY, multiRow.getStartup_delay());
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
            multiRow.setDirection(c.getString(c.getColumnIndex(DbConfig.DIRECTION)));
            multiRow.setNum_of_bracketed_shot(c.getInt(c.getColumnIndex(DbConfig.NUMBER_OF_BRACKETED_SHOT)));
            multiRow.setBacketing_style(c.getString(c.getColumnIndex(DbConfig.BRACKETING_STYLE)));
            multiRow.setAfter_shot_delay(c.getInt(c.getColumnIndex(DbConfig.AFTER_SHOT_DELAY)));
            multiRow.setStartup_delay(c.getInt(c.getColumnIndex(DbConfig.STARTUP_DELAY)));
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



    /******************************************PARTIAL/GIGAPEXEL************************************/


    public void addPartialGigapixel(Partial partial) {
        if (db == null) {
            db = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        values.put(DbConfig.PARTIAL_ROW_NAME, partial.getPartialName());
        values.put(DbConfig.PARTIAL_CAMERA_NAME, partial.getCameraName());
        values.put(DbConfig.PARTIAL_LENS_NAME, partial.getLensName());
        values.put(DbConfig.PARTIAL_OVERLAP, partial.getOverlap());
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

