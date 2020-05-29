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
        String sqlUsers = "CREATE TABLE user_admin(id INTEGER PRIMARY KEY AUTOINCREMENT, user_name VARCHAR, user_password VARCHAR);";
        String sqlObjects = "CREATE TABLE objects(id INTEGER PRIMARY KEY AUTOINCREMENT, object_material VARCHAR, user_id INTEGER, FOREIGN KEY(user_id) REFERENCES user_admin(id));";
        String sqlMaterial = "CREATE TABLE material(id INTEGER PRIMARY KEY AUTOINCREMENT, material_name VARCHAR, material_count INTEGER);";
        String sqlInformation = "CREATE TABLE information(id INTEGER PRIMARY KEY AUTOINCREMENT);";
        String sqlBin = "CREATE TABLE bin(id INTEGER PRIMARY KEY AUTOINCREMENT, bin_name VARCHAR);";
        String sqlOmb = "CREATE TABLE object_material_bin(id INTEGER PRIMARY KEY AUTOINCREMENT, object_id INTEGER, material_id INTEGER, bin_id INTEGER, info_id INTEGER, FOREIGN KEY(object_id) REFERENCES objects(id), FOREIGN KEY(material_id) REFERENCES material(id), FOREIGN KEY(bin_id) REFERENCES bin(id), FOREIGN KEY(info_id) REFERENCES information(id))";

        String addMaterialMetal = String.format("INSERT INTO material(material_name,material_count) VALUES (\"Metal\",0)");
        String addMaterialPaper = String.format("INSERT INTO material(material_name,material_count) VALUES (\"Paper\",0)");
        String addMaterialPlastic = String.format("INSERT INTO material(material_name,material_count) VALUES (\"Plastic\",0)");
        String addMaterialWaste = String.format("INSERT INTO material(material_name,material_count) VALUES (\"Waste\",0)");

        String addBinMetal = String.format("INSERT INTO bin(bin_name) VALUES (\"Metal\")");
        String addBinPaper = String.format("INSERT INTO bin(bin_name) VALUES (\"Paper\")");
        String addBinPlastic = String.format("INSERT INTO bin(bin_name) VALUES (\"Plastic\")");
        String addBinWaste = String.format("INSERT INTO bin(bin_name) VALUES (\"Waste\")");

        String account = String.format("INSERT INTO user_admin(user_name, user_password) VALUES (\"gf\",\"gf\")");
        sqLiteDatabase.execSQL(sqlUsers);
        sqLiteDatabase.execSQL(sqlObjects);
        sqLiteDatabase.execSQL(sqlMaterial);
        sqLiteDatabase.execSQL(sqlInformation);
        sqLiteDatabase.execSQL(sqlBin);
        sqLiteDatabase.execSQL(sqlOmb);
        sqLiteDatabase.execSQL(addMaterialPaper);
        sqLiteDatabase.execSQL(addMaterialMetal);
        sqLiteDatabase.execSQL(addMaterialPlastic);
        sqLiteDatabase.execSQL(addMaterialWaste);
        sqLiteDatabase.execSQL(addBinPaper);
        sqLiteDatabase.execSQL(addBinMetal);
        sqLiteDatabase.execSQL(addBinPlastic);
        sqLiteDatabase.execSQL(addBinWaste);
        sqLiteDatabase.execSQL(account);

    }

    public boolean verifyUser(String user, String pass)
    {
        /*String selectQuery = String.format("SELECT * FROM user_admin WHERE user_name=\"%s\" AND user_password=\"%s\";",user,pass);
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);
        c.moveToFirst();
        int total = c.getCount();
        c.close();

        if (total == 1) {
            return true;

        } else {
            return false;
        }*/

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
        /*String sqlString = String.format("INSERT INTO objects(object_material) VALUES(\"" + material + "\");");
        db.execSQL(sqlString);
        db.close();
        return true;*/
        ContentValues contentValues = new ContentValues();
        if (ActLActivity.getLoginStatus() == true) {
            contentValues.put("object_material", material);
            contentValues.put("user_id", 1);
        }

        else {
            contentValues.put("object_material", material);
        }
        db.insert("objects", null, contentValues);
        db.close();
        return true;
    }

    public boolean addMaterial(String material) {
        SQLiteDatabase db = getWritableDatabase();
        /*String sqlString = String.format("INSERT INTO material(material_name) VALUES(\"" + material + "\");");
        db.execSQL(sqlString);
        db.close();
        return true;*/
        ContentValues contentValues = new ContentValues();
        contentValues.put("material_name", material);
        db.insert("material", null, contentValues);
        db.close();
        return true;
    }

    public boolean addBin(String bin) {
        SQLiteDatabase db = getWritableDatabase();
        /*String sqlString = String.format("INSERT INTO bin(bin_name) VALUES(\"" + bin + "\");");
        db.execSQL(sqlString);
        db.close();
        return true;*/

        ContentValues contentValues = new ContentValues();
        contentValues.put("bin_name", bin);
        db.insert("bin", null, contentValues);
        db.close();
        return true;
    }

    public String getUserNo(String user) {
        String selectQuery = String.format("SELECT id FROM user_admin WHERE user_name=\"%s\"", user);
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);
        c.moveToFirst();
        int total = c.getCount();
        String userid = "";
        if(c.getCount() > 0){
            userid = (c.getString(0)); // first element is index zero
        }
        c.close();

        return userid;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + "objects";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + "id" + " FROM " + "objects" +
                " WHERE " + "object_material" + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateObjectMaterial(String newMaterial, int id, String oldMaterial){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + "objects" + " SET " + "object_material" +
                " = '" + newMaterial + "' WHERE " + "id" + " = '" + id + "'" +
                " AND " + "object_material" + " = '" + oldMaterial + "'";
        db.execSQL(query);
    }

    public void deleteObject(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + "objects" + " WHERE "
                + "id" + " = '" + id + "'" +
                " AND " + "object_material" + " = '" + name + "'";
        db.execSQL(query);
    }

    public void updateMatCount(String material) {
        String selectQuery = String.format("SELECT * FROM objects WHERE object_material=\"%s\";",material);
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);
        c.moveToFirst();
        int total = c.getCount();
        c.close();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + "material" + " SET " + "material_count" +
                " = '" + (total+1-1) + "' WHERE " + "material_name" + " = '" + material + "'";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlUsers = "DROP TABLE IF EXISTS user_admin";
        String sqlObjects = "DROP TABLE IF EXISTS objects";
        String sqlMaterial = "DROP TABLE IF EXISTS material";
        String sqlInformation = "DROP TABLE IF EXISTS information";
        String sqlBin = "DROP TABLE IF EXISTS bin";
        String sqlOmb = "DROP TABLE IF EXISTS object_material_bin";

        sqLiteDatabase.execSQL(sqlUsers);
        sqLiteDatabase.execSQL(sqlObjects);
        sqLiteDatabase.execSQL(sqlMaterial);
        sqLiteDatabase.execSQL(sqlInformation);
        sqLiteDatabase.execSQL(sqlBin);
        sqLiteDatabase.execSQL(sqlOmb);

        onCreate(sqLiteDatabase);
    }
}
