package com.wuelto.prueba.com.pointofinterest.helpers;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

import com.wuelto.prueba.com.pointofinterest.MyPlaces;

/**
 * Created by grodriguez on 26/09/2015.
 * Internet Connectivity Checking
 */
public class ConnectionManager {

    @Nullable
    public static Boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) MyPlaces.getInstance().
                getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
