package com.example.personasmercandiser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SomeDB.db";
    public static final String LOGIN_TABLE_NAME = "Login";
    public static final String LOGIN_TABLE_EMAIL = "email";
    public static final String LOGIN_TABLE_PASSWORD = "password";
    public static final String SHOP_TABLE_NAME = "Shop";
    public static final String SHOP_TABLE_SHOPNAME = "Name";
    public static final String SHOP_TABLE_ADDRESS = "Address";
    public static final String SHOP_TABLE_LONGITUDE = "Longitude"; // Долгота
    public static final String SHOP_TABLE_LATITUDE = "Latitude"; // Широта
    private static final int DATABASE_VERSION = 11;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Login (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT," +
                "password TEXT);");

        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGIN_TABLE_EMAIL, "1");
        contentValues.put(LOGIN_TABLE_PASSWORD, "1");
        db.insert(LOGIN_TABLE_NAME, null, contentValues);
        contentValues.clear();

        db.execSQL("CREATE TABLE Shop (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Address TEXT," +
                "Longitude TEXT," +
                "Latitude TEXT);");

        contentValues.put(SHOP_TABLE_SHOPNAME, "Мария-ра");
        contentValues.put(SHOP_TABLE_ADDRESS, "ул. Петра Сухова, 14, Барнаул, Алтайский край");
        contentValues.put(SHOP_TABLE_LONGITUDE, "53.3784514");
        contentValues.put(SHOP_TABLE_LATITUDE, "83.7325199");
        db.insert(SHOP_TABLE_NAME, null, contentValues);
        contentValues.clear();

        contentValues.put(SHOP_TABLE_SHOPNAME, "Хорошее настроение");
        contentValues.put(SHOP_TABLE_ADDRESS, "ул. Смирнова, 46, Барнаул, Алтайский край");
        contentValues.put(SHOP_TABLE_LONGITUDE, "53.3703099");
        contentValues.put(SHOP_TABLE_LATITUDE, "83.7354857");
        db.insert(SHOP_TABLE_NAME, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Login");
        db.execSQL("DROP TABLE IF EXISTS Shop");
        onCreate(db);
    }

    public String[] getShops() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(SHOP_TABLE_NAME, new String[]{SHOP_TABLE_SHOPNAME, SHOP_TABLE_ADDRESS}, null, null, null, null, null);

        int rowCount = cursor.getCount();
        String[] shopsText = new String[rowCount];

        int i = 0;
        while (cursor.moveToNext()) {
            shopsText[i] = "Название магазина - " + cursor.getString(0) + "\n" +
                    "Адресс - " + cursor.getString(1);
            i++;
        }
        cursor.close();
        return shopsText;
    }

    public boolean checkLoginAndPass(String email, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = LOGIN_TABLE_EMAIL + "=?" + " and " + LOGIN_TABLE_PASSWORD + "=?";
        String[] selectionArgs = {email, pass};
        Cursor cursor = db.query(LOGIN_TABLE_NAME, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }
}
