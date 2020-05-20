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

        String addMaterialColumn = String.format("INSERT INTO material(material_name) VALUES(\"cardboard\"),(\"glass\"),(\"metal\"),(\"paper\"),(\"plastic\"),(\"waste\");");
        String addBinColumn = String.format("INSERT INTO bin(bin_name) VALUES(\"cardboard\"),(\"glass\"),(\"metal\"),(\"paper\"),(\"plastic\"),(\"waste\");");
        String account = String.format("INSERT INTO user_admin(user_name, user_password) VALUES (\"gf\",\"gf\")");
        sqLiteDatabase.execSQL(sqlUsers);
        sqLiteDatabase.execSQL(sqlObjects);
        sqLiteDatabase.execSQL(sqlMaterial);
        sqLiteDatabase.execSQL(sqlInformation);
        sqLiteDatabase.execSQL(sqlBin);
        sqLiteDatabase.execSQL(sqlOmb);
        sqLiteDatabase.execSQL(addMaterialColumn);
        sqLiteDatabase.execSQL(addBinColumn);
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
