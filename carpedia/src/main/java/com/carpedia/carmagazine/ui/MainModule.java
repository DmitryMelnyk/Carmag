package com.carpedia.carmagazine.ui;

import android.support.v7.app.AppCompatActivity;

import com.carpedia.carmagazine.di.ActivityScoped;
import com.carpedia.carmagazine.di.FragmentScoped;
import com.carpedia.carmagazine.ui.common.BaseActivityModule;
import com.carpedia.carmagazine.ui.common.BaseFragmentModule;
import com.carpedia.carmagazine.ui.details.view.UserDetailsFragment;
import com.carpedia.carmagazine.ui.details.view.UserDetailsModule;
import com.carpedia.carmagazine.ui.user_list.view.UserListFragment;
import com.carpedia.carmagazine.ui.user_list.view.UserListModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by d264 on 12/26/17.
 */

@Module(includes = {
        BaseActivityModule.class,
        BaseFragmentModule.class
})
public abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = UserListModule.class)
    abstract UserListFragment userListFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = UserDetailsModule.class)
    abstract UserDetailsFragment userDetailsFragment();

    @ActivityScoped
    @Binds
    abstract AppCompatActivity bindAppCompatActivity(MainActivity activity);
}
