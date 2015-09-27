package com.wuelto.prueba.com.pointofinterest.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wuelto.prueba.com.pointofinterest.database.SqliteDb;
import com.wuelto.prueba.com.pointofinterest.entities.PlaceTable;
import com.wuelto.prueba.com.pointofinterest.model.Places;
import com.wuelto.prueba.com.pointofinterest.utils.Constants;

import java.util.ArrayList;

/**
 * Created by grodriguez on 26/09/2015.
 * Controller for DataBase
 */
public class DataBaseHelper {

    private SqliteDb mSqliteDb;
    private SQLiteDatabase mSqLiteDatabase;
    private Context mContext;
    private PlaceTable mPlaceTable;

    public DataBaseHelper(Context context) {
        this.mContext = context;
    }

    public void insert(String name, String lat, String lon, String description, String index) {

        mSqliteDb = new SqliteDb(mContext);
        mSqLiteDatabase = mSqliteDb.getWritableDatabase();
        mPlaceTable = new PlaceTable();
        mPlaceTable.insert(mSqLiteDatabase, name, lon, lat, description, index);
        mSqliteDb.close();
    }

    public ArrayList<Places> select() {
        mSqliteDb = new SqliteDb(mContext);
        mSqLiteDatabase = mSqliteDb.getReadableDatabase();
        mPlaceTable = new PlaceTable();
        Cursor cursor = mPlaceTable.select(mSqLiteDatabase);
        ArrayList<Places> placesArrayList = new ArrayList<Places>();

        try{
            if(cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    placesArrayList.add( new Places(cursor.getString(cursor.getColumnIndex(Constants.NAME_PLACES)),
                            cursor.getString(cursor.getColumnIndex(Constants.LON_PLACES)),
                            cursor.getString(cursor.getColumnIndex(Constants.LAT_PLACES)),
                            cursor.getString(cursor.getColumnIndex(Constants.DESC_PLACES)),
                            cursor.getString(cursor.getColumnIndex(Constants.INDEX_PLACES))));
                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch(NullPointerException e) {
            Log.v("DataBaseHelperException",e.getMessage());
            return null;
        }
        mSqliteDb.close();
        return placesArrayList;
    }

    public void delete() {
        mSqliteDb = new SqliteDb(mContext);
        mSqLiteDatabase = mSqliteDb.getWritableDatabase();
        mPlaceTable = new PlaceTable();
        mPlaceTable.delete(mSqLiteDatabase);
        mSqliteDb.close();

    }

}
