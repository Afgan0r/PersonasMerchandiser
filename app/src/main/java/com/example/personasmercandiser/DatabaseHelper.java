package com.example.personasmercandiser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.EnumSet;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SomeDB.db";
    private static final int DATABASE_VERSION = 1;

    public static final String LOGINTABLE_NAME = "Login";
    public static final String LOGINTABLE_COL1 = "_id";
    public static final String LOGINTABLE_COL2 = "email";
    public static final String LOGINTABLE_COL3 = "password";

    public static final String SHOPTTABLE_NAME = "Shop";
    public static final String SHOPTTABLE_COL1 = "_id";
    public static final String SHOPTTABLE_COL2 = "Name";
    public static final String SHOPTTABLE_COL3 = "Address";
    public static final String SHOPTTABLE_COL4 = "Longitude"; // Долгота
    public static final String SHOPTTABLE_COL5 = "Latitude"; // Широта



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO fix bug with "No such table Shop"
        db.execSQL("CREATE TABLE Shop (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Address TEXT," +
                "Longitute TEXT," +
                "Latitude TEXT);");

        db.execSQL("CREATE TABLE Login (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT," +
                "password TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS table1");
        onCreate(db);
    }

    public boolean fillTables() {
        //Login
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            this.getWritableDatabase();
        } catch (SQLiteException ex) {
            return true;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGINTABLE_COL2, "1");
        contentValues.put(LOGINTABLE_COL3, "1");
        db.insert(LOGINTABLE_NAME, null, contentValues);
        contentValues.clear();

        //Shop
        contentValues.put(SHOPTTABLE_COL2, "Мария-ра");
        contentValues.put(SHOPTTABLE_COL3, "ул. Петра Сухова, 14, Барнаул, Алтайский край, 656010");
        contentValues.put(SHOPTTABLE_COL4, "53.3784514");
        contentValues.put(SHOPTTABLE_COL5, "83.7325199");
        db.insert(SHOPTTABLE_NAME, null, contentValues);
        contentValues.clear();

        contentValues.put(SHOPTTABLE_COL2, "Хорошее настроение");
        contentValues.put(SHOPTTABLE_COL3, "ул. Смирнова, 46, Барнаул, Алтайский край, 656010");
        contentValues.put(SHOPTTABLE_COL4, "53.3703099");
        contentValues.put(SHOPTTABLE_COL5, "83.7354857");
        db.insert(SHOPTTABLE_NAME, null, contentValues);

        contentValues.clear();
        db.close();
        return false;
    }

    public ArrayList<String> getShops() {
        String[] columns = { SHOPTTABLE_COL2, SHOPTTABLE_COL3 };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(SHOPTTABLE_NAME, columns, null, null, null, null, null, null);
        cursor.close();

        ArrayList<String> shopsText = new ArrayList<>();
        while (cursor.moveToNext()) {
            shopsText.add("Название магазина - " + cursor.getString(0) + "\n" +
                    "Адресс - " + cursor.getString(1));
        }
        return shopsText;
    }

    public boolean checkLoginAndPass(String email, String pass) {
        String[] columns = {LOGINTABLE_COL1};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = LOGINTABLE_COL2 + "=?" + " and " + LOGINTABLE_COL3 + "=?";
        String[] selectionArgs = {email, pass};
        Cursor cursor = db.query(LOGINTABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}
