package com.carpedia.carmagazine.app;

import com.carpedia.carmagazine.data.db.AppDatabase;
import com.carpedia.carmagazine.di.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

/**
 * Created by d264 on 12/26/17.
 */


public class App extends DaggerApplication {

    @Inject
    AppDatabase database;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return element.getLineNumber() + ": " + element;
            }
        });

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
