package com.dynamicminds.ffwsafetyalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ProblemDAO extends SQLiteOpenHelper {

    public ProblemDAO(Context context){
        super(context, "safetyalert.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE problem (id integer primary key, type string, latitude float, longitude float)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS problem");
        onCreate(db);
    }

    public ArrayList<Problem> getAll(){
        ArrayList<Problem> problemList = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from problem", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            problemList.add(new Problem(res.getInt(res.getColumnIndex("id")),
                    res.getString(res.getColumnIndex("type")),
                    res.getFloat(res.getColumnIndex("longitude")),
                    res.getFloat(res.getColumnIndex("latitude"))
            ));

            res.moveToNext();
        }
        return problemList;
    }

    public boolean setTableData(ArrayList<Problem> problemList){
        SQLiteDatabase db = this.getWritableDatabase();

        for (Problem problem : problemList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put("id", problem.getProblemId());
            contentValues.put("type", problem.getType());
            contentValues.put("latitude", problem.getLatitude());
            contentValues.put("longitude", problem.getLongitude());

            db.insert("problem", null, contentValues);
        }
        return true;
    }
}
