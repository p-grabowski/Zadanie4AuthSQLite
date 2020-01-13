package com.example.zadanie3auth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_name = "Base.db";
    public static final String Table_name = "users_table";
    public static final String COL_1 = "_id";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";
    public static final String COL_4 = "range";   //1 - admin, 0 - user


    public DataBaseHelper(Context context) {
        super(context, DB_name, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table_name + "( " +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(db);
    }

    public boolean login(String username, String password){
   /*     SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Table_name, new String[]{COL_1, COL_2, COL_3, COL_4},COL_2 + "=?",
                new String[]{username},null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            Auth user1 = new Auth(cursor.getInt(0), cursor.getString(1), cursor.getString(2),spr(cursor.getInt(3)));

            //Match both passwords check they are same or not
            if (password.equals(cursor.getString(2))) {
                return true;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return false;
*/
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Table_name+" where "+COL_2+" = '" + username + "' AND " +COL_3+" = '"+password+"';", null);
        if (res.getCount() > 0) return true;
        else return false;
    }

    public boolean checkIsAdmin (String username) {
        return false;
    }

    public boolean spr(int a){
        if(a==0)return false; else if(a==1) return true; else return false;
    }

    public boolean addUser(String username, int range) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, "secure");
        contentValues.put(COL_4, range);
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
        Cursor res = db.rawQuery("select * from "+Table_name+" WHERE "+ COL_1 +" = '"+ id +"';", null);
        return res;
    }

    public Integer deleteUser(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table_name, "_id = ?", new String[] { id });
    }

    public boolean checkUserIsExist(String username){  //sprawdz czy istnieje, jesli istenieje więcej niż 0 rekordów to true, mniej - false
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Table_name+" where "+COL_2+" ='" + username + "';", null);
        if (res.getCount() > 0) return true;
        else return false;
    }

    public boolean upadateUser(String id, String username, String password, String range){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, range);
        db.update(Table_name, contentValues, "_id = ?", new String[]{ id });
        return true;
    }
}