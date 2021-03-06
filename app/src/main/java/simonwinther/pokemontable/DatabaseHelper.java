package simonwinther.pokemontable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by simonwinther on 2018-02-06.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "pokemons";
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "name";

    public DatabaseHelper (Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT)";
        db.execSQL(createTable);
        db.execSQL("Create table user(name text primary key, password text )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public boolean insert (String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        long ins = db.insert("user", null, contentValues);
        if (ins==-1) return false;
        else return true;
    }

    public Boolean checkName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where name=?", new String[]{name});
        if (cursor.getCount()>0) return false;
        else return true;
    }

    public Boolean namePlusPassword (String name, String password) {
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where name=? and password=?" , new String[]{name,password});
        if (cursor.getCount()>0)  return true;
        else return false;

    }

    public boolean addData(String pokemonName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, pokemonName);

        Log.d(TAG, "addData: adding " + pokemonName + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_ID + " FROM " + TABLE_NAME +
                " WHERE " + KEY_NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updatedName(String newName, int id, String oldName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + KEY_NAME +
                " = '" + newName + "' WHERE " + KEY_ID + " = '" + id + "'" +
                " AND " + KEY_NAME + " = '" + oldName + "'";

        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void deleteName(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " DELETE FROM " + TABLE_NAME + " WHERE "
                + KEY_ID + " = '" + id + "'" +
                " AND " + KEY_NAME + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }



































}
