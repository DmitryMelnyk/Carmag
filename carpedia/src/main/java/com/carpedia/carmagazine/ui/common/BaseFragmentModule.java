package com.carpedia.carmagazine.ui.common;

import android.arch.lifecycle.ViewModelProvider;

import com.carpedia.carmagazine.ui.user_list.viewmodel.UserListViewModelFactory;

import dagger.Binds;
import dagger.Module;

/**
 * Created by d264 on 12/27/17.
 */

@Module
public abstract class BaseFragmentModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(UserListViewModelFactory factory);

}
