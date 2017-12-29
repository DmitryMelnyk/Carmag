package com.carpedia.carmagazine.ui.user_list.view;

import android.arch.lifecycle.ViewModel;

import com.carpedia.carmagazine.di.FragmentScoped;
import com.carpedia.carmagazine.ui.user_list.repository.UserRepository;
import com.carpedia.carmagazine.ui.user_list.repository.UserRepositoryImpl;
import com.carpedia.carmagazine.ui.user_list.viewmodel.UserListViewModel;
import com.carpedia.carmagazine.ui.user_list.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by d264 on 12/26/17.
 */

@Module
public abstract class UserListModule {

//    @Binds
//    @IntoMap
//    @ViewModelKey(UserListViewModel.class)
//    abstract UserRepository bindUserRepository(UserRepositoryImpl userRepository);

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel.class)
    abstract ViewModel bindUserListViewModel(UserListViewModel viewModel);
}
