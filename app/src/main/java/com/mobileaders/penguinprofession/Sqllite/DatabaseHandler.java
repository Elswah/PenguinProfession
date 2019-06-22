package com.mobileaders.penguinprofession.Sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eng ahmed ibrahim on 1/13/2017.
 */
public class DatabaseHandler  extends SQLiteOpenHelper {

    private final ArrayList<ModleData>list=new ArrayList<>();



    public DatabaseHandler (Context context) {

        super(context, Data.DB_NAME, null, Data.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + Data.TABLE_NAME + "("
                + Data.User_id + " TEXT," + Data.USER_NAME + " TEXT," + Data.USER_EMAIL + " TEXT," + Data.USER_PAYED + " TEXT," + Data.USER_DESCRIPTION+ " TEXT," +
                Data.USER_AGE + " TEXT," + Data.USER_HEADLINE + " TEXT," + Data.USER_PHONENUMBER + " TEXT," + Data.USER_WEBSITE + " TEXT,"
                + Data.USER_ADDRESS + " TEXT" + ")";
              db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_NAME);
        onCreate(db);

    }
    public  void addData(ModleData modle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Data.User_id,modle.getId());
        values.put(Data.USER_NAME,modle.getUserFullName());
        values.put(Data.USER_EMAIL,modle.getUserEmail());
        values.put(Data.USER_PAYED,modle.getUserPayed());
        values.put(Data.USER_DESCRIPTION,modle.getUserDescription());
        values.put(Data.USER_AGE,modle.getUserAge());
        values.put(Data.USER_HEADLINE,modle.getUserHeadLine());
        values.put(Data.USER_PHONENUMBER,modle.getUserPhoneNumber());
        values.put(Data.USER_WEBSITE,modle.getUserWebsite());
        values.put(Data.USER_ADDRESS,modle.getUserAddress());
        db.insert(Data.TABLE_NAME, null, values);
    }



    public List<ModleData> getAlLdata() {
        List<ModleData> dataList = new ArrayList<ModleData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Data.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModleData modleData = new ModleData();
                modleData.setId(cursor.getString(0));
                modleData.setUserFullName(cursor.getString(1));
                modleData.setUserEmail(cursor.getString(2));
                modleData.setUserPayed(cursor.getString(3));
                modleData.setUserDescription(cursor.getString(4));
                modleData.setUserAge(cursor.getString(5));
                modleData.setUserHeadLine(cursor.getString(6));
                modleData.setUserPhoneNumber(cursor.getString(7));
                modleData.setUserWebsite(cursor.getString(8));
                modleData.setUserAddress(cursor.getString(9));
                // Adding contact to list
                dataList.add(modleData);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dataList ;
    }






}
