package com.carpedia.carmagazine.ui.common;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.carpedia.carmagazine.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by d264 on 12/27/17.
 */

@Module
public abstract class BaseActivityModule {

    @Binds
    @ActivityScoped
    abstract Context activivtyContext(AppCompatActivity activity);

    @Provides
    @ActivityScoped
    static FragmentManager activityFragmentManager(AppCompatActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
