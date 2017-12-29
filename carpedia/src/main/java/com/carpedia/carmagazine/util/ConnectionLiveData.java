package com.carpedia.carmagazine.util;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.carpedia.carmagazine.data.network.Result;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by d264 on 12/28/17.
 */

public class ConnectionLiveData extends LiveData<Result> {

    private final Context mContext;

    private static boolean sIsConnectedPreviousState;

    private final BroadcastReceiver mConnectionChangingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Timber.d("received broadcast for internet connection changed!");
            boolean isConnectedNewState = intent.getBooleanExtra(
                    NetworkStateReceiver.EXTRA_NETWORK_IS_AVAILABLE, false);

            if (sIsConnectedPreviousState != isConnectedNewState) {
                setValue(isConnectedNewState ? Result.OK : Result.ERROR);
                sIsConnectedPreviousState = isConnectedNewState;
            }
        }
    };

    @Inject
    public ConnectionLiveData(Context context) {
        mContext = context;
    }

    @Override
    protected void onActive() {
        super.onActive();
        IntentFilter connectionStatusFilter = new IntentFilter();
        connectionStatusFilter.addAction(NetworkStateReceiver.ACTION_NETWORK_STATE_CHANGED);
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(mConnectionChangingReceiver, connectionStatusFilter);

        sIsConnectedPreviousState = NetworkStateChecker.isNetworkReachable(mContext);
        setValue(sIsConnectedPreviousState ? Result.OK : Result.ERROR);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        LocalBroadcastManager.getInstance(mContext)
                .unregisterReceiver(mConnectionChangingReceiver);
    }
}
