package com.carpedia.carmagazine.ui.user_list.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.util.Log;

import com.carpedia.carmagazine.data.db.AppDatabase;
import com.carpedia.carmagazine.data.db.entity.UserEntity;
import com.carpedia.carmagazine.data.network.AppApiHelper;
import com.carpedia.carmagazine.model.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by d264 on 12/26/17.
 */

public class UserRepositoryImpl {

    private static UserRepositoryImpl sInstance;

    private final AppDatabase mDatabase;
    private final AppApiHelper mAppApiHelper;

    private LiveData<List<UserEntity>> defaultUser;
    private LiveData<List<UserEntity>> sortedByRatingUserDescOrder;
    private LiveData<List<UserEntity>> sortedByRatingcUserAsOrder;

    private final MediatorLiveData<List<? extends User>> mObservableUsers =
            new MediatorLiveData<>();

    Observer<List<UserEntity>> mObserver;

    @Inject
    public UserRepositoryImpl(final AppDatabase database,
                              final AppApiHelper appApiHelper) {
        Timber.i("Creating UserRepositoryImpl");
        mDatabase = database;
        mAppApiHelper = appApiHelper;

        Runnable task = () -> {
            Timber.e("clearing table!!!");
            mDatabase.userDao().clearTable();
            defaultUser = mDatabase.userDao().loadUsers();
            mObserver = userList -> {
                if (mDatabase.getDatabaseCreated().getValue() != null) {
                    mObservableUsers.postValue(userList);
                }
            };
            mObservableUsers.addSource(defaultUser, mObserver);
        };
        new Thread(task).start();

    }

    public static UserRepositoryImpl getInstance(final AppDatabase database,
                                                 final AppApiHelper appApiHelper) {
        if (sInstance == null) {
            synchronized (UserRepositoryImpl.class) {
                if (sInstance == null) {
                    sInstance = new UserRepositoryImpl(database, appApiHelper);
                }
            }
        }
        return sInstance;
    }

    public Single<Boolean> loadUsers() {
        // clearing previous data
//        clearTable();
        Log.e("!!!!", "loading data");
        return mAppApiHelper.getUserList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(userList -> {
                    saveUsers(userList);
                    return true; // result ok if no server errors
                });
    }

 public Single<Boolean> updateUsers() {
        // clearing previous data
        clearTable();
        return mAppApiHelper.getUserList()
                .subscribeOn(Schedulers.io())
                .map(userList -> {
                    saveUsers(userList);
                    return true; // result ok if no server errors
                })
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    /**
     * Get the list of alarms from the database and get notified when the data changes.
     */
    public LiveData<List<? extends User>> getUsers() {
        return mObservableUsers;
    }

    private void clearTable() {
        Timber.d("Sorting item_user by default");
        Runnable task = () -> mDatabase.userDao().clearTable();
        new Thread(task).start();
    }

    private void clearSources() {
        Timber.d("Clearing sources.");
        mObservableUsers.removeSource(defaultUser);
        mObservableUsers.removeSource(sortedByRatingUserDescOrder);
        mObservableUsers.removeSource(sortedByRatingcUserAsOrder);
    }

    public void sortUserByDefault() {
        Timber.d("Sorting item_user by default");
        clearSources();
        Runnable task = () -> {
            defaultUser = mDatabase.userDao().loadUsers();
            mObservableUsers.addSource(defaultUser, mObserver);
        };
        new Thread(task).start();
    }

    public void sortUserByRatingDesc() {
        Timber.e("Sorting item_user by rating. Order=desc");
        clearSources();
        Runnable task = () -> {
            sortedByRatingUserDescOrder = mDatabase.userDao().loadUsersByRatingDesc();
            mObservableUsers.addSource(sortedByRatingUserDescOrder, mObserver);
        };
        new Thread(task).start();
    }

    public void sortUserByRatingAsc() {
        Timber.e("Sorting item_user by rating. Order=asc");
        clearSources();
        Runnable task = () -> {
            sortedByRatingcUserAsOrder = mDatabase.userDao().loadUsersByRatingAsc();
            mObservableUsers.addSource(sortedByRatingcUserAsOrder, mObserver);
        };
        new Thread(task).start();
    }

    public void saveUsers(List<UserEntity> users) {
        Timber.e("Saving users. User's count=" + users.size());
        Log.e("!!!!", "Saving users. User's count=" + users.size());
        Runnable task = () -> mDatabase.userDao().insertAll(users);
        new Thread(task).start();
    }
}