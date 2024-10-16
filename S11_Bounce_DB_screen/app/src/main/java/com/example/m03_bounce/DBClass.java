package com.example.m03_bounce;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
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
    private static final String colorColumn = "color";
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY, " +
                    "x FLOAT, y FLOAT, dx FLOAT, dy FLOAT, color INTEGER)";
    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
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
            values.put(colorColumn, dataModel.color);
            values.put(xColumn, dataModel.x);
            values.put(yColumn, dataModel.y);
            values.put(dxColumn, dataModel.dx);
            values.put(dyColumn, dataModel.dy);
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
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            return db.delete("balls", "id = ?", new String[]{id.toString()});
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
                    item = new BallModel(cursor.getLong(0), cursor.getFloat(1), cursor.getFloat(2), cursor.getFloat(3), cursor.getFloat(4), cursor.getInt(5));
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
