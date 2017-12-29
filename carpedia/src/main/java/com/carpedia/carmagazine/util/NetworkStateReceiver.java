package com.carpedia.carmagazine.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import timber.log.Timber;

/**
 * Created by d264 on 12/28/17.
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    public static final String ACTION_NETWORK_STATE_CHANGED = "ACTION_NETWORK_STATE_CHANGED";
    public static final String EXTRA_NETWORK_IS_AVAILABLE = "EXTRA_NETWORK_IS_AVAILABLE";
    public static final String EXTRA_NETWORK_TYPE = "EXTRA_NETWORK_TYPE";

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;

    public static boolean sIsConnected;
    public static int sConnectionType;
    private static boolean sPreviousState = true;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        sIsConnected = NetworkStateChecker.isNetworkReachable(context);
        sConnectionType = NetworkStateChecker.getConnectionType(context);

        Timber.d("network is reachable=" + sIsConnected);
        Timber.d("connection_type=" + sConnectionType);

        if (sPreviousState != sIsConnected) {
            sendBroadcast(context, sIsConnected, sConnectionType);
            sPreviousState = sIsConnected;
        }
    }

    private void sendBroadcast(Context context, boolean isConnected, int connectionType) {
        Intent localBroadcast = new Intent(ACTION_NETWORK_STATE_CHANGED);
        localBroadcast.putExtra(EXTRA_NETWORK_IS_AVAILABLE, isConnected);
        localBroadcast.putExtra(EXTRA_NETWORK_TYPE, connectionType);

        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(localBroadcast);
    }
}