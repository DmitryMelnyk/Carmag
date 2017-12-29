package com.carpedia.carmagazine.data.db;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by d264 on 12/26/17.
 */

@Module
public class DbModule {

    @Provides
    AppDatabase providesAppDatabase(Context context, AppExecutors executors) {
        return AppDatabase.getInstance(context, executors);
    }
}
