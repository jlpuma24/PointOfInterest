package com.wuelto.prueba.com.pointofinterest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wuelto.prueba.com.pointofinterest.utils.Constants;

/**
 * Created by grodriguez on 26/09/2015.
 * Createthe dataBaseTable and Update it
 */
public class SqliteDb extends SQLiteOpenHelper {

    public SqliteDb(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE" + Constants.TABLE_NAME_PLACES +
            " (" + Constants.ID_PLACES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Constants.NAME_PLACES + " TEXT, " +
            Constants.LON_PLACES + " TEXT, " +
            Constants.LAT_PLACES + " TEXT, " +
            Constants.DESC_PLACES + " TEXT, " +
            Constants.INDEX_PLACES + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onCreate(sqLiteDatabase);
    }


}
