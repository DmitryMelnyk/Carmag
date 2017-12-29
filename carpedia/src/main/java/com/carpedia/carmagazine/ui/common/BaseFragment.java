package com.carpedia.carmagazine.ui.common;

import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by d264 on 12/27/17.
 */

public class BaseFragment extends DaggerFragment {

    private CompositeDisposable mCompositeDisposable;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onResume() {
        super.onResume();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
        super.onStop();
    }

    protected void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }
}
