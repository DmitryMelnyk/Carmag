package com.carpedia.carmagazine.di;

import android.app.Application;
import android.content.Context;

import com.carpedia.carmagazine.data.db.AppExecutors;

import dagger.Binds;
import dagger.Module;

/**
 * Created by d264 on 12/26/17.
 */

@Module
public abstract class ApplicationModule {
    //expose Application as an injectable context
    @Binds
    abstract Context bindContext(Application application);
}
