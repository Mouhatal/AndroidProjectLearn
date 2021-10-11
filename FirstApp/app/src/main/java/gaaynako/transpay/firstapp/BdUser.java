package gaaynako.transpay.firstapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BdUser extends SQLiteOpenHelper {
    public BdUser(@Nullable Context context) {
        super(context, "dbusers.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        "firstName VARCHAR(30), " +
                                        "lastName VARCHAR(30)," +
                                        " userName VARCHAR(30), " +
                                        "passWord VARCHAR(50) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DELETE FROM user;");
    }


    public boolean create(String firstName, String lastName,String userName, String passWord)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("firstName",firstName );
            cv.put("lastName",lastName);
            cv.put("userName",userName );
            cv.put("passWord",passWord);
            db.insert("user", null, cv);//insertion
            db.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(String firstName, String lastName,String userName, String passWord)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            //cv.put("login",login );
            cv.put("passWord",passWord);
            db.update("user",cv, "userName='"+userName+"'", null);
            db.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public boolean delete(String userName)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("user", "userName='"+userName+"'", null);
            db.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getUsers()
    {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor c = db.query("user", null, null, null, null, null, null);
            if(c!=null && c.getCount()>0)
            {
                c.moveToFirst();
                do {
                    @SuppressLint("Range") String firstName = c.getString(c.getColumnIndex("firstName"));
                    @SuppressLint("Range") String lastName = c.getString(c.getColumnIndex("lastName"));
                    @SuppressLint("Range") String userName = c.getString(c.getColumnIndex("userName"));
                    @SuppressLint("Range") String passWord = c.getString(c.getColumnIndex("passWord"));
                    list.add(firstName+"/"+lastName+"/"+userName+"/"+passWord);

                    c.moveToNext();
                }while (!c.isAfterLast());
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }



    public boolean checkUserExist(String userName, String passWord) throws SQLException
    {
        String[] columns = {"userName"};
        // db = openDatabase();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection =" userName=?  and passWord = ?";
        String[] selectionArgs = {userName, passWord};

        Cursor cursor = db.query("user", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        close();

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }

}
