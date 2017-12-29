package com.carpedia.carmagazine.ui.user_list.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.carpedia.carmagazine.data.network.Result;
import com.carpedia.carmagazine.model.User;
import com.carpedia.carmagazine.ui.user_list.repository.UserRepositoryImpl;
import com.carpedia.carmagazine.util.ConnectionLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by d264 on 12/26/17.
 */

public class UserListViewModel extends AndroidViewModel {

    // MediatorLiveData observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<? extends User>> mObservableUsers;

    // For toast messages
    private final PublishSubject<String> mMsgSource = PublishSubject.create();

    // Observe connection status
    private final PublishSubject<Result> mConnectionStatus = PublishSubject.create();

    // Informs about click on user
    private final PublishSubject<User> mUserClickSource = PublishSubject.create();

    // For observing connectivity changes
    private ConnectionLiveData mConnectionStatusObservable;

    private final MediatorLiveData<Boolean> mIsLoading = new MediatorLiveData<>();

    private UserRepositoryImpl mRepository;

    @Inject
    public UserListViewModel(@NonNull Application application, UserRepositoryImpl repository) {
        super(application);
        this.mRepository = repository;
        // starts loading users from the server and writing to db
        loadData();

        mObservableUsers = new MediatorLiveData<>();
        // loads data from db and start observing it
        LiveData<List<? extends User>> source = repository.getUsers();
        mObservableUsers.addSource(source, mObservableUsers::setValue);
        mConnectionStatusObservable = new ConnectionLiveData(application);
    }

    @Override
    protected void onCleared() {
        mMsgSource.onComplete();
        mUserClickSource.onComplete();
        mConnectionStatus.onComplete();
        super.onCleared();
    }

    private void loadData() {
        mIsLoading.setValue(true);
        mRepository.loadUsers().subscribe(
                resultOk -> { /* NOP */ },
                throwable -> mMsgSource.onNext("Oups! Connection's problems."));
    }

    public MediatorLiveData<Boolean> getLoadingStatus() {
        return mIsLoading;
    }

    public LiveData<List<? extends User>> getUsers() {
        return mObservableUsers;
    }

    public ConnectionLiveData getConnectionStatusObservable() {
        return mConnectionStatusObservable;
    }

    public Observable<String> getToasts() {
        return mMsgSource.flatMap(Observable::just);
    }

    public Observable<User> getUserClickListener() {
        return mUserClickSource.flatMap(Observable::just);
    }

    public void onUserClicked(User user) {
        Timber.d("open item_user clicked!" + user);
        mUserClickSource.onNext(user);
    }

    public void sortUser(int position) {
        mIsLoading.setValue(true);
        switch (position) {
            case 0:
                mRepository.sortUserByRatingDesc();
                break;
            case 1:
                mRepository.sortUserByRatingAsc();
                break;
            case 2:
                mRepository.sortUserByDefault();
                break;
        }
    }

    public void onRefresh() {
        mIsLoading.setValue(true);
        mRepository.updateUsers().subscribe(
                users -> { /* NOP */ },
                throwable -> mMsgSource.onNext("Server problems!"));
    }

    /**
     * UserAdapter uses this method to inform about finishing check preparation
     */
    public void readyToDisplayUser() {
        mIsLoading.setValue(false);
    }
}
