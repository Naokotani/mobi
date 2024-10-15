package com.example.m03_bounce;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DBClass extends SQLiteOpenHelper implements DB_Interface {
    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "Bounce_DB_Name.db";
    private static final String TABLE_NAME = "balls";
    private static final String xColumn = "x";  // Names of the columns
    private static final String yColumn = "y";
    private static final String dxColumn = "dx";
    private static final String dyColumn = "dy";
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "X FLOAT, Y FLOAT, DX FLOAT, DY FLOAT)";
    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
//        db.execSQL("INSERT INTO " + TABLE_NAME + " (X, Y, DX, DY)    VALUES" +
//                "(3.0, 22.1, 0.5, 0.7)," +
//                "(132.0, 22.3, 0.5, -1.7)," +
//                "(134.0, 122.5, 0.5, 2.7)," +
//                "(163.0, 122.7, 0.5, -6.7)," +
//                "(283.0, 222.9, 0.5, 4.7)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        Log.d("DBClass", "DB onUpgrade() to version " + DATABASE_VERSION);
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public int count() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        try(SQLiteDatabase db = this.getReadableDatabase()) {
            Cursor cursor = db.rawQuery(countQuery, null);
            int cnt = cursor.getCount();
            cursor.close();
            return cnt;
        }
    }

    @Override
    public long save(BallModel dataModel) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(xColumn, dataModel.getModelX());
            values.put(yColumn, dataModel.getModelY());
            values.put(dxColumn, dataModel.getModelDX());
            values.put(dyColumn, dataModel.getModelDY());
            return db.insert(TABLE_NAME,
                    null,
                    values);
        }
    }

    @Override
    public int update(BallModel dataModel) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    private void addDefaultRows() {
        int doCount = this.count();
        if (doCount > 20) {
            Log.v("DBClass", "already 20 rows in DB");

        } else {
            BallModel a = new BallModel(1, 20.0f,20.0f,-4.0f,4.0f);
            this.save(a);
            a = new BallModel(2, 30f,30f,3f,-3f);
            this.save(a);
            a = new BallModel(3, 40f,40f,1f,4f);
            this.save(a);
            a = new BallModel(4, 60f,60f,-4f,1f);
            this.save(a);

        }
    }

    @Override
    public List<Ball> findAll() {
        List<BallModel> ballModels = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        try(SQLiteDatabase db = this.getReadableDatabase()) {
            Cursor cursor = db.rawQuery(query, null);
            BallModel item;

            if (cursor.moveToFirst()) {
                do {
                    item = new BallModel(cursor.getInt(0), cursor.getFloat(1), cursor.getFloat(2), cursor.getFloat(3), cursor.getFloat(4) );
                    ballModels.add(item);
                } while (cursor.moveToNext());
            }

            return ballModels.stream().map(Ball::new).collect(Collectors.toList());
        }

    }

    @Override
    public String getNameById(Long id) {
        return null;
    }
}
