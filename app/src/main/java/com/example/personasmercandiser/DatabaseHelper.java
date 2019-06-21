package com.example.personasmercandiser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SomeDB.db";
    private static final int DATABASE_VERSION = 19;

    private static final String LOGIN_TABLE_NAME = "Login";
    private static final String LOGIN_TABLE_EMAIL = "email";
    private static final String LOGIN_TABLE_PASSWORD = "password";

    private static final String SHOP_TABLE_NAME = "Shop";
    private static final String SHOP_TABLE_SHOPNAME = "Name";
    private static final String SHOP_TABLE_ADDRESS = "Address";
    private static final String SHOP_TABLE_LONGITUDE = "Longitude"; // Долгота
    private static final String SHOP_TABLE_LATITUDE = "Latitude"; // Широта

    private static final String JOB_TABLE_NAME = "Job";
    private static final String JOB_TABLE_PERFORMER = "Performer";
    private static final String JOB_TABLE_SHOP = "Shop";
    private static final String JOB_TABLE_PHOTO = "Photo";
    private static final String JOB_TABLE_TIME = "Time";
    private static final String JOB_TABLE_DATE = "Date";

    private static final String PRODUCT_TABLE_NAME = "Product";
    private static final String PRODUCT_TABLE_NOMENCLATURE = "Nomenclature";
    private static final String PRODUCT_TABLE_COUNT = "Count";
    private static final String PRODUCT_TABLE_PRICE = "Price";
    private static final String PRODUCT_TABLE_JOB = "Job_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
        fillTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Login");
        db.execSQL("DROP TABLE IF EXISTS Shop");
        db.execSQL("DROP TABLE IF EXISTS Product");
        db.execSQL("DROP TABLE IF EXISTS Job");
        onCreate(db);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Shop (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Address TEXT," +
                "Longitude TEXT," +
                "Latitude TEXT);");

        db.execSQL("CREATE TABLE Login (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT," +
                "password TEXT);");

        db.execSQL("CREATE TABLE Job (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Shop INTEGER, " +
                "Performer INTEGER, " +
                "Photo TEXT, " +
                "Time TEXT, " +
                "Date TEXT, " +
                "FOREIGN KEY (Performer) REFERENCES Login(_id)," +
                "FOREIGN KEY (Shop) REFERENCES Shop(_id))");

        db.execSQL("CREATE TABLE Product (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nomenclature TEXT, " +
                "Count INTEGER, " +
                "Price REAL, " +
                "Job_id INTEGER," +
                "FOREIGN KEY (Job_id) REFERENCES Job(_id))");
    }

    private void fillTables(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGIN_TABLE_EMAIL, "1");
        contentValues.put(LOGIN_TABLE_PASSWORD, "1");
        db.insert(LOGIN_TABLE_NAME, null, contentValues);
        contentValues.clear();

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

    public void createJob(int shopId, int performerId, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JOB_TABLE_PERFORMER, performerId);
        contentValues.put(JOB_TABLE_SHOP, shopId);
        contentValues.put(JOB_TABLE_DATE, date);
        contentValues.put(JOB_TABLE_TIME, time);
        db.insert(JOB_TABLE_NAME, null, contentValues);
    }

    public void addProduct(int job_id, String nomenclature, int count, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_TABLE_NOMENCLATURE, nomenclature);
        contentValues.put(PRODUCT_TABLE_COUNT, count);
        contentValues.put(PRODUCT_TABLE_PRICE, price);
        contentValues.put(PRODUCT_TABLE_JOB, job_id);
        db.insert(PRODUCT_TABLE_NAME, null, contentValues);
    }

    public void addPhoto(int job_id, String photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JOB_TABLE_PHOTO, photo);
        String where = "_id=" + job_id;
        db.update(JOB_TABLE_NAME, contentValues, where, null);
    }

    public int getShopIdByInf(String shopInf) {
        // I get big String here with shop name and shop address
        // I pull address because it always unique
        int start = shopInf.indexOf("Адресс - ") + 9;
        int end = shopInf.length();
        char[] buf = new char[end - start];
        shopInf.getChars(start, end, buf, 0);
        String shopAdress = new String(buf);

        // Finding shop id by his address that i receive higher
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(SHOP_TABLE_NAME, new String[]{"_id"}, SHOP_TABLE_ADDRESS + "=?", new String[]{shopAdress}, null, null, null);
        cursor.moveToFirst();
        return Integer.parseInt(cursor.getString(0));
    }

    public int getPerformerIdByEmail(String email) {
        // I use email because its always unique
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(LOGIN_TABLE_NAME, new String[]{"_id"}, LOGIN_TABLE_EMAIL + "=?", new String[]{email}, null, null, null);
        cursor.moveToFirst();
        return Integer.parseInt(cursor.getString(0));
    }

    public int getJobId() {
        // When this method is called new job already created and started, and i just take last id
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(JOB_TABLE_NAME, new String[]{"_id"}, null, null, null, null, null, null);
        cursor.moveToLast();
        return Integer.parseInt(cursor.getString(0));
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

    public String[] getProductsName(int jobId) {    // Get products that created in job
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Nomenclature FROM Product WHERE Job_id=" + jobId, null);
        cursor.moveToFirst();
        String[] products = new String[cursor.getCount()];

        int i = 0;
        while (cursor.moveToNext()) {
            products[i] = cursor.getString(0);
            i++;
        }

        cursor.close();
        return products;
    }

    public String[] getProductInf(String productNomenclature) { // Product nomenclature unique in job, so i use it to fill product information
        SQLiteDatabase db = this.getReadableDatabase();
        String where = PRODUCT_TABLE_NOMENCLATURE + "=?";
        Cursor cursor = db.query(PRODUCT_TABLE_NAME, new String[]{PRODUCT_TABLE_COUNT, PRODUCT_TABLE_PRICE}, where, new String[]{productNomenclature}, null, null, null);

        cursor.moveToLast();
        String[] productInf = new String[2];
        productInf[0] = cursor.getString(0);
        productInf[1] = cursor.getString(1);

        cursor.close();
        return productInf;
    }

    public boolean checkLoginAndPass(String email, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = LOGIN_TABLE_EMAIL + "=?" + " and " + LOGIN_TABLE_PASSWORD + "=?";
        String[] selectionArgs = {email, pass};

        Cursor cursor = db.query(LOGIN_TABLE_NAME, null, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();

        cursor.close();
        return count > 0;
    }
}
