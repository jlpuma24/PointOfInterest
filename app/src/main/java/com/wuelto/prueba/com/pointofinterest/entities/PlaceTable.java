package com.wuelto.prueba.com.pointofinterest.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wuelto.prueba.com.pointofinterest.utils.Constants;

/**
 * Created by grodriguez on 26/09/2015.
 * Table methods
 */
public class PlaceTable {

    private ContentValues mContentValues;

    public PlaceTable() {

    }

    public void insert(SQLiteDatabase sqLiteDatabase, String name, String lon, String lat, String description, String index) {

        mContentValues = new ContentValues();
        mContentValues.put(Constants.NAME_PLACES,name);
        mContentValues.put(Constants.LON_PLACES,lon);
        mContentValues.put(Constants.LAT_PLACES,lat);
        mContentValues.put(Constants.DESC_PLACES,description);
        mContentValues.put(Constants.INDEX_PLACES,index);

        sqLiteDatabase.insert(Constants.TABLE_NAME_PLACES,null,mContentValues);
    }

    public Cursor select(SQLiteDatabase sqLiteDatabase) {

        return sqLiteDatabase.rawQuery("SELECT * FROM " + Constants.TABLE_NAME_PLACES, null);
    }

    public void delete(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.delete(Constants.TABLE_NAME_PLACES,null,null);
    }
}
