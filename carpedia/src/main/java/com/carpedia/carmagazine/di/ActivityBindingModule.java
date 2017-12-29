package com.carpedia.carmagazine.di;

import com.carpedia.carmagazine.ui.MainActivity;
import com.carpedia.carmagazine.ui.MainModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by d264 on 12/26/17.
 */

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();
}
