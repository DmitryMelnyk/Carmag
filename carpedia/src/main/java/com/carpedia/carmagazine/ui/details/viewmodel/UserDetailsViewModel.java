package com.carpedia.carmagazine.ui.details.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.carpedia.carmagazine.model.User;
import com.carpedia.carmagazine.ui.details.ActionIntent;
import com.carpedia.carmagazine.ui.details.ActionIntentCall;
import com.carpedia.carmagazine.ui.details.ActionIntentSendEmail;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by d264 on 12/27/17.
 */

public class UserDetailsViewModel extends ViewModel {

    private final PublishSubject<ActionIntent> mActionObservable = PublishSubject.create();

    @Inject
    public UserDetailsViewModel() {

    }

    public Observable<ActionIntent> getActionObservable() {
        return mActionObservable.flatMap(Observable::just);
    }

    public void onMailClicked(User user) {
        String mail = user.getEmail();
        mActionObservable.onNext(new ActionIntentSendEmail(mail));
    }

    public void onPhoneClicked(User user) {
        String phone = user.getPhone();
        phone = phone.replaceAll("[-()]", "");
        mActionObservable.onNext(new ActionIntentCall(phone));
    }
}
