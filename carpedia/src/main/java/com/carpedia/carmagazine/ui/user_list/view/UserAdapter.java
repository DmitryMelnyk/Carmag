package com.carpedia.carmagazine.ui.user_list.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.carpedia.carmagazine.R;
import com.carpedia.carmagazine.databinding.ItemUserBinding;
import com.carpedia.carmagazine.model.User;
import com.carpedia.carmagazine.ui.user_list.viewmodel.UserListViewModel;

import java.util.List;

/**
 * Created by d264 on 12/26/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    @Nullable
    private final UserListViewModel mViewModel;

    List<? extends User> mUserList;

    public UserAdapter(@Nullable UserListViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void setUserList(final List<? extends User> userList) {
        if (mUserList == null) {
            mUserList = userList;
            notifyItemRangeInserted(0, userList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mUserList.size();
                }

                @Override
                public int getNewListSize() {
                    return userList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mUserList.get(oldItemPosition).getId()
                            == userList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    User newAlarm = userList.get(newItemPosition);
                    User oldAlarm = mUserList.get(oldItemPosition);
                    return newAlarm.equals(oldAlarm);
                }
            });

            mUserList = userList;
            result.dispatchUpdatesTo(this);
            mViewModel.readyToDisplayUser();
        }
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemUserBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_user, parent, false);
        binding.setViewModel(mViewModel);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.binding.setUser(mUserList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.ratingBar.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return mUserList == null ? 0 : mUserList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        final ItemUserBinding binding;

        public UserViewHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}