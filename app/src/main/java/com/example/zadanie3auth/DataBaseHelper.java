package com.example.zadanie3auth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_name = "Base.db";
    public static final String Table_name = "users_table";
    public static final String COL_1 = "_id";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";
    public static final String COL_4 = "range";         //1 - admin, 0 - user
    public static final String COL_5 = "name";
    public static final String COL_6 = "defaultPass";   //1 - wymaga zmiany, 0 - nie wymaga zmian


    public DataBaseHelper(Context context) {
        super(context, DB_name, null, 4);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table_name + "( " +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " INTEGER," +
                COL_5 + " TEXT," +
                COL_6 + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(db);
    }

    public boolean login(String username, String password){
        password = securityPassword(password, Global.salt);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Table_name, new String[]{COL_3},COL_2 + "=?",
                new String[]{username},null, null, null);
        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //Auth user1 = new Auth(cursor.getInt(0), cursor.getString(1), cursor.getString(2),spr(cursor.getInt(3)));
            if (password.equals(cursor.getString(0))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDefaultPass(Integer id){    //jezeli wymaga zmiany to zwraca true
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(Table_name, new String[]{COL_6},COL_1 + "=?",
                new String[]{id.toString()},null, null, null);
        if (res != null && res.moveToFirst()&& res.getCount()>0) {
            return spr(res.getInt(0));
        }
        return false;
    }

    public boolean checkIsAdmin (String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(Table_name, new String[]{COL_4},COL_2 + "=?",
                new String[]{username},null, null, null);
        if (res != null && res.moveToFirst()&& res.getCount()>0) {
            return spr(res.getInt(0));
            }
        return false;
        }

    public boolean spr(int a){
        if(a==0)return false; else if(a==1) return true; else return false;
    }

    public boolean addUser(String username, int range, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, securityPassword("secure", Global.salt));
        contentValues.put(COL_4, range);
        contentValues.put(COL_5, name);
        contentValues.put(COL_6, 1);
        long result = db.insert(Table_name, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor selectAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Table_name, null);
        return res;
    }

    public Cursor selectUserById(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(Table_name, new String[]{COL_1, COL_2, COL_4, COL_5, COL_3},COL_1 + "=?",
                new String[]{id.toString()},null, null, null);
        res.moveToFirst();
        return res;
    }

    public int selectIdByUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+COL_1+" from "+Table_name+" WHERE "+ COL_2 +" = '"+ username +"';", null);
        res.moveToFirst();
        return res.getInt(0);
    }

    public boolean deleteUser(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete(Table_name, "_id = ?", new String[] { id.toString() })>0){
            return true;
        }else return false;
    }

    public boolean checkUserIsExist(String username){  //sprawdz czy istnieje, jesli istanieje zwroc true, jesli nie to false
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(Table_name, new String[]{COL_1},COL_2 + "=?",
                new String[]{username},null, null, null);
        if (res.getCount() > 0) return true;
        else return false;
    }

    public boolean upadateUser(Integer id, String username, String password, int range, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, securityPassword(password, Global.salt));
        contentValues.put(COL_4, range);
        contentValues.put(COL_5, name);
        contentValues.put(COL_6, 0);
        db.update(Table_name, contentValues, COL_1 +"= ?", new String[]{id.toString()});
        return true;
    }
    public boolean upadateUserPass(Integer id,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_3, securityPassword(password, Global.salt));
        db.update(Table_name, contentValues, COL_1 +"= ?", new String[]{id.toString()});
        return true;
    }
    public boolean upadateUserPassDef(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6, 0);
        db.update(Table_name, contentValues, COL_1 +"= ?", new String[]{id.toString()});
        return true;
    }

    public String securityPassword(String password, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}