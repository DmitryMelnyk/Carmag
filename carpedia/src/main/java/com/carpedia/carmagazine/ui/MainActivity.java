package com.carpedia.carmagazine.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.carpedia.carmagazine.R;
import com.carpedia.carmagazine.model.User;
import com.carpedia.carmagazine.ui.common.BaseActivity;
import com.carpedia.carmagazine.ui.details.view.UserDetailsFragment;
import com.carpedia.carmagazine.ui.user_list.view.UserListFragment;

public class MainActivity extends BaseActivity {

    private AppBarLayout mAppBar;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        if (savedInstanceState == null) {
            setFragment(R.id.container, new UserListFragment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        mMenu = menu;
        return true;
    }

    private void setToolbar() {
        mAppBar = findViewById(R.id.app_bar);
        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void transitToUser(User user) {
        UserDetailsFragment fragment = UserDetailsFragment.newInstance(user);
        addFragmentOnTop(R.id.container, fragment);
        getSupportActionBar().setTitle(user.getFirstName() + " " +user.getLastName());
        mAppBar.setElevation(0);
        mMenu.findItem(R.id.menu_filter).setVisible(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // restoring toolbar's previous state
        mAppBar.setElevation(8.0f);
        getSupportActionBar().setTitle(R.string.app_name);
        mMenu.findItem(R.id.menu_filter).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
