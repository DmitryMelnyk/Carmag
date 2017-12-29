package com.carpedia.carmagazine.ui.user_list.repository;

import android.arch.lifecycle.LiveData;

import com.carpedia.carmagazine.model.User;

/**
 * Created by d264 on 12/26/17.
 */

public interface UserRepository {

    LiveData<User> getUserList();
}
