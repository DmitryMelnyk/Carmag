package com.carpedia.carmagazine.ui.details.view;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.carpedia.carmagazine.ui.details.viewmodel.UserDetailsViewModel;
import com.carpedia.carmagazine.ui.user_list.viewmodel.UserListViewModel;
import com.carpedia.carmagazine.ui.user_list.viewmodel.UserListViewModelFactory;
import com.carpedia.carmagazine.ui.user_list.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by d264 on 12/26/17.
 */

@Module
public abstract class UserDetailsModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailsViewModel.class)
    abstract ViewModel bindUserDetailsViewModel(UserDetailsViewModel viewModel);
}
