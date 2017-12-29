package com.carpedia.carmagazine.ui.common;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import com.carpedia.carmagazine.R;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by d264 on 12/27/17.
 */

public class BaseActivity extends DaggerAppCompatActivity {

    @Inject
    public FragmentManager fragmentManager;

    protected void setFragment(@IdRes int container, Fragment fragment) {
        fragmentManager.beginTransaction()
                .add(container, fragment)
                .commit();
    }

    protected void addFragmentOnTop(@IdRes int container, Fragment fragment) {
        fragmentManager.beginTransaction()
                .add(container, fragment/*, tag*/)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
