package com.fypic.imageclassification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME= "sqllitetest";
    private static final int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlUsers = "CREATE TABLE user_admin(user_id INTEGER PRIMARY KEY AUTOINCREMENT, user_name VARCHAR NOT NULL, user_password VARCHAR NOT NULL);";
        String sqlObjects = "CREATE TABLE objects(object_id INTEGER PRIMARY KEY AUTOINCREMENT, material_id INTEGER NOT NULL, FOREIGN KEY(material_id) REFERENCES material(material_id));";
        String sqlMaterial = "CREATE TABLE material(material_id INTEGER PRIMARY KEY AUTOINCREMENT, material_name VARCHAR NOT NULL);";

        String addMaterialMetal = String.format("INSERT INTO material(material_name) VALUES (\"Metal\")");
        String addMaterialPaper = String.format("INSERT INTO material(material_name) VALUES (\"Paper\")");
        String addMaterialPlastic = String.format("INSERT INTO material(material_name) VALUES (\"Plastic\")");
        String addMaterialWaste = String.format("INSERT INTO material(material_name) VALUES (\"Waste\")");

        String account = String.format("INSERT INTO user_admin(user_name, user_password) VALUES (\"gf\",\"gf\")");

        sqLiteDatabase.execSQL(sqlUsers);
        sqLiteDatabase.execSQL(sqlObjects);
        sqLiteDatabase.execSQL(sqlMaterial);
        sqLiteDatabase.execSQL(addMaterialPaper);
        sqLiteDatabase.execSQL(addMaterialMetal);
        sqLiteDatabase.execSQL(addMaterialPlastic);
        sqLiteDatabase.execSQL(addMaterialWaste);
        sqLiteDatabase.execSQL(account);

    }

    public boolean verifyUser(String user, String pass)
    {
        String sql = "Select count(*) from user_admin where user_name='" + user + "' and user_password='" + pass + "'";
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        statement.close();

        if (l == 1) {
            return true;

        } else {
            return false;
        }
    }


    public boolean addObjects(String material) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String selectQuery = String.format("SELECT material_id FROM material WHERE material_name=\"%s\"", material);
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("material_id"));

        contentValues.put("material_id", id);
        db.insert("objects", null, contentValues);
        db.close();
        return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + "objects";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + "objects";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getPaperData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM objects WHERE material_id = 1;";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getMetalData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + "objects" + " WHERE " + "material_id" +
                " = " + " 2";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getPlasticData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + "objects" + " WHERE " + "material_id" +
                " = " + " 3";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getWasteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + "objects" + " WHERE " + "material_id" +
                " = " + " 4";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int getMatAllCount() {
        String selectQuery = String.format("SELECT * FROM objects");
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);
        c.moveToFirst();
        int total = c.getCount();
        c.close();
        return total;
    }

    public int getMatMetalCount() {
        String selectQuery = String.format("SELECT * FROM objects WHERE material_id = 2");
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);
        c.moveToFirst();
        int total = c.getCount();
        c.close();
        return total;
    }

    public int getMatPlasticCount() {
        String selectQuery = String.format("SELECT * FROM objects WHERE material_id = 3");
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);
        c.moveToFirst();
        int total = c.getCount();
        c.close();
        return total;
    }

    public int getMatPaperCount() {
        String selectQuery = String.format("SELECT * FROM objects WHERE material_id = 1");
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);
        c.moveToFirst();
        int total = c.getCount();
        c.close();
        return total;
    }

    public int getMatWasteCount() {
        String selectQuery = String.format("SELECT * FROM objects WHERE material_id = 4");
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);
        c.moveToFirst();
        int total = c.getCount();
        c.close();
        return total;
    }

    public void updateObjectMaterial(String newMaterial, int id, String oldMaterial){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = String.format("SELECT material_id FROM material WHERE material_name=\"%s\"", newMaterial);
        String selectQuery2 = String.format("SELECT material_id FROM material WHERE material_name=\"%s\"", oldMaterial);
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursornew = database.rawQuery(selectQuery, null);
        cursornew.moveToFirst();
        int matidnew = cursornew.getInt(cursornew.getColumnIndex("material_id"));

        Cursor cursorold = database.rawQuery(selectQuery2, null);
        cursorold.moveToFirst();
        int matidold = cursorold.getInt(cursorold.getColumnIndex("material_id"));

        String query = "UPDATE " + "objects" + " SET " + "material_id" +
                " = '" + matidnew + "' WHERE " + "object_id" + " = '" + id + "'" +
                " AND " + "material_id" + " = '" + matidold + "'";
        db.execSQL(query);
    }

    public void deleteObject(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + "objects" + " WHERE "
                + "object_id" + " = '" + id + "'";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlUsers = "DROP TABLE IF EXISTS user_admin";
        String sqlObjects = "DROP TABLE IF EXISTS objects";
        String sqlMaterial = "DROP TABLE IF EXISTS material";

        sqLiteDatabase.execSQL(sqlUsers);
        sqLiteDatabase.execSQL(sqlObjects);
        sqLiteDatabase.execSQL(sqlMaterial);

        onCreate(sqLiteDatabase);
    }
}
