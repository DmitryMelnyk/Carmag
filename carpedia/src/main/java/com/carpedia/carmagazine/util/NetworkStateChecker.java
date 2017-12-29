package com.carpedia.carmagazine.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by d264 on 12/28/17.
 */

public class NetworkStateChecker {

    public static boolean isNetworkReachable(Context context) {
        final ConnectivityManager cManager =
                (ConnectivityManager) context.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo current = cManager.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.getState() == NetworkInfo.State.CONNECTED
                && ((current.getType() == cManager.TYPE_MOBILE
                || current.getType() == cManager.TYPE_WIFI)));
    }

    public static int getConnectionType(Context context) {
        final ConnectivityManager cManager =
                (ConnectivityManager) context.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo current = cManager.getActiveNetworkInfo();
        if (current == null) {
            return 0;
        }

        if (current.getType() == cManager.TYPE_WIFI) {
            return NetworkStateReceiver.TYPE_WIFI;
        }

        if (current.getType() == cManager.TYPE_MOBILE){
            return NetworkStateReceiver.TYPE_MOBILE;
        } else {
            return -1;
        }
    }
}