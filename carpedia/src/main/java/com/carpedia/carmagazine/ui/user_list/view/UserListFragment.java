package com.carpedia.carmagazine.ui.user_list.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.carpedia.carmagazine.R;
import com.carpedia.carmagazine.data.network.Result;
import com.carpedia.carmagazine.databinding.FragmentUserListBinding;
import com.carpedia.carmagazine.ui.MainActivity;
import com.carpedia.carmagazine.ui.common.BaseFragment;
import com.carpedia.carmagazine.ui.user_list.viewmodel.UserListViewModel;

import timber.log.Timber;

/**
 * Created by d264 on 12/26/17.
 */

public class UserListFragment extends BaseFragment {

    private FragmentUserListBinding mBinding;
    private UserListViewModel viewModel;
    private UserAdapter mUserAdapter;
    private static Result sPreviousConnectionStatus = Result.OK;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false);
        Timber.d("created!");

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(UserListViewModel.class);

        mBinding.setViewModel(viewModel);
        mUserAdapter = new UserAdapter(viewModel);
        mBinding.rvUserList.setAdapter(mUserAdapter);
        subscribeUi(viewModel);

        setHasOptionsMenu(true);
        Timber.d("onActivityCreated!");
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribeObservables();
    }

    /**
     * Reacts to user clicking and displays toast
     */
    private void subscribeObservables() {
        addDisposable(viewModel.getUserClickListener().subscribe(
                user -> ((MainActivity) getActivity()).transitToUser(user)
        ));

        addDisposable(viewModel.getToasts().subscribe(
                msg -> Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show()
        ));
    }

    private void subscribeUi(UserListViewModel viewModel) {
        mBinding.setIsConnected(true);
        viewModel.getUsers().observe(this, users -> {
            if (users != null) {
                mUserAdapter.setUserList(users);
                mBinding.rvUserList.scrollToPosition(0);
            }
        });

        viewModel.getConnectionStatusObservable().observe(this, connection -> {
            if (connection == sPreviousConnectionStatus && connection == Result.OK) return;

            sPreviousConnectionStatus = connection;
            if (connection == Result.OK) {
                mBinding.setIsConnected(true);
                createRefreshSnackbar();
            } else {
                mBinding.setIsConnected(false);
                Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getLoadingStatus().observe(this, isLoading ->
            mBinding.setIsLoading(isLoading));
}

    private void createRefreshSnackbar() {
        Snackbar.make(mBinding.getRoot(), R.string.sanck_network_is_evailable, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_refresh, view -> viewModel.onRefresh())
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_filter) {
            new MaterialDialog.Builder(getActivity())
                    .title(R.string.filter_title)
                    .items(R.array.filter_items)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            viewModel.sortUser(which);
                            return true;
                        }
                    })
                    .positiveText(R.string.choose)
                    .show();
        }
        return true;
    }
}
