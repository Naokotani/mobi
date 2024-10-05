package com.codelab.basics;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CharacterDBConnection extends SQLiteOpenHelper implements Repository<Character> {
    private Context appContext;
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "DB_Name.db";
    private static final String TABLE_NAME = "sample_table";
    private static final String CHARACTER_TABLE = "character_table";
    private static final String SQL_CREATE_CHARACTER_TABLE =
            "CREATE TABLE " +
                    CHARACTER_TABLE +
                    "(id INTEGER PRIMARY KEY NOT NULL," +
                    " name VARCHAR(256), race VARCHAR(256), gender VARCHAR(256)," +
                    " bio TEXT, health INTEGER, attack INTEGER, defense INTEGER, ki INTEGER)";
    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SQL_DELETE_CHARACTER_TABLE =
            "DROP TABLE IF EXISTS " + CHARACTER_TABLE;

    public CharacterDBConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public List<Character> findAll() {
        List<Character> characters = new ArrayList<>();
        String query = "SELECT  * FROM " + CHARACTER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Character character;
        if (cursor.moveToFirst()) {
            do {
                character = getRow(cursor);
                characters.add(character);
            } while (cursor.moveToNext());
        }

        db.close();
        return characters;
    }

    @Override
    public Character findEntryByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true,
                CHARACTER_TABLE,
                new String[]{"*"},
                "WHERE id = ?",
                new String[]{"" + id},
                null,
                null,
                null,
                "1" );

        cursor.moveToFirst();
        return getRow(cursor);
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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.v("DBClass", "getCount=" + cnt);
        return cnt;
    }

    @Override
    public void save(Character character) {
        SQLiteDatabase db = this.getWritableDatabase();
        createCharacterRow(db, character);
        db.close();
    }

    @Override
    public void buildDB(Context context) {
        if(!isTable()){
            createCharactersTable(appContext);
        }
    }

    @Override
    public int update(Character character) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public String getNameById(Long id) {
        return null;
    }

    private List<Character> getCharacters(String json) {
        List<Character> characters;
        characters = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(json);
            JSONArray charactersJson = object.getJSONArray("characters");

            for(int i = 0; i < charactersJson.length(); i++) {
                JSONObject character = charactersJson.getJSONObject(i);
                characters.add(parseJSONCharacter(character, i+1));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return characters;
    }

    private Character parseJSONCharacter(JSONObject character, int id) {
        try {
            return new Character(
                    id,
                    character.getString("name"),
                    character.getString("race"),
                    character.getString("gender"),
                    character.getString("bio"),
                    Integer.parseInt(character.getString("health").replaceAll(",","")),
                    Integer.parseInt(character.getString("attack").replaceAll(",","")),
                    Integer.parseInt(character.getString("defense").replaceAll(",","")),
                    Integer.parseInt(character.getString("kiRestoreSpeed").replaceAll(",",""))
            );
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static @NonNull String getJSONString(Context context) {
        AssetManager assetManager = context.getAssets();
        InputStream is;
        String json;

        try {
            is = assetManager.open("characters.json") ;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            json = stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public void onCreate(SQLiteDatabase db) {
        appContext = appContext.getApplicationContext();
        createCharactersTable(appContext);
    }


    private boolean isTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor c = db.query(true,
                "sqlite_master",
                new String[]{"name"},
                "type=? AND name=?",
                new String[]{"table", CHARACTER_TABLE},
                null, null, null,
                "1");
        return (c.getCount() > 0);
    }

    private void createCharacterRow(SQLiteDatabase db, Character character) {
        ContentValues values = new ContentValues();
        values.put("id", character.id);
        values.put("name", character.name);
        values.put("race", character.race);
        values.put("bio", character.bio);
        values.put("gender", character.gender);
        values.put("health", character.health);
        values.put("attack", character.attack);
        values.put("defense", character.defense);
        values.put("ki", character.kiRestoreSpeed);
        db.insert(CHARACTER_TABLE, null, values);
    }

    public void createCharactersTable(Context context) {
        final String json = getJSONString(context);
        final List<Character> characters = getCharacters(json);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_CREATE_CHARACTER_TABLE);
        characters.forEach(c -> createCharacterRow(db, c));
        db.close();
    }

    @Override
    public void deleteRepository() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DELETE_CHARACTER_TABLE);
        db.close();
    }

    private Character getRow(Cursor cursor) {
        return new Character(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5),
                cursor.getInt(6),
                cursor.getInt(7),
                cursor.getInt(8));
    }
}
