package com.wuelto.prueba.com.pointofinterest;

import android.app.Application;

/**
 * Created by grodriguez on 26/09/2015.
 * Instants of the context to reduce the use of memory
 */
public class MyPlaces extends Application {

    private static MyPlaces ourInstance ;

    public static MyPlaces getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
    }
}
