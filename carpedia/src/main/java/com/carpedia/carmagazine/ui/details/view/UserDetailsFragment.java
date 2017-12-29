package com.carpedia.carmagazine.ui.details.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.carpedia.carmagazine.BR;
import com.carpedia.carmagazine.R;
import com.carpedia.carmagazine.databinding.FragmentUserDetailsBinding;
import com.carpedia.carmagazine.model.Car;
import com.carpedia.carmagazine.model.User;
import com.carpedia.carmagazine.ui.common.BaseFragment;
import com.carpedia.carmagazine.ui.details.ActionIntentCall;
import com.carpedia.carmagazine.ui.details.ActionIntentSendEmail;
import com.carpedia.carmagazine.ui.details.viewmodel.UserDetailsViewModel;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import timber.log.Timber;

/**
 * Created by d264 on 12/26/17.
 */

public class UserDetailsFragment extends BaseFragment {

    private static final String ARG_USER = "ARG_USER";
    private User mUser;

    FragmentUserDetailsBinding mBinder;
    private UserDetailsViewModel mViewModel;
    private ImageView imageView;

    public static UserDetailsFragment newInstance(User user) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        UserDetailsFragment fragment = new UserDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false);
        Timber.d("created!");
        imageView = mBinder.getRoot().findViewById(R.id.avatar);
        return mBinder.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(UserDetailsViewModel.class);

        mUser = getArguments().getParcelable(ARG_USER);
        mBinder.setUser(mUser);
        mBinder.setViewModel(mViewModel);
        initializeCarScroller();

        Glide.with(this)
                .load(mUser.getAvatar())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        scheduleStartPostponedTransition(imageView);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //Image successfully loaded into image view
                        scheduleStartPostponedTransition(imageView);
                        return false;
                    }
                })
                .into(imageView);
    }

    private void scheduleStartPostponedTransition(final ImageView imageView) {
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }
        });
    }

    private void initializeCarScroller() {
        if (mUser.getCars().isEmpty()) return;
        updateCar(0);
        CarAdapter carAdapter = new CarAdapter(mUser.getCars());
        mBinder.picker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build());
        mBinder.picker.setAdapter(carAdapter);
        mBinder.picker.scrollToPosition(0);
        mBinder.picker.setSlideOnFling(true);
        mBinder.picker.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                /* NOP */
            }

            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                updateCar(adapterPosition);
            }

            @Override
            public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {
                updateCar(newPosition);
            }
        });
    }

    private void updateCar(int newPosition) {
        Car currentCar = mUser.getCars().get(newPosition);
        mBinder.setCar(currentCar);
        mBinder.notifyPropertyChanged(BR.car);
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribeObservables(mViewModel);
    }

    private void subscribeObservables(UserDetailsViewModel viewModel) {
        addDisposable(viewModel.getActionObservable().subscribe(actionIntent -> {
            if (actionIntent instanceof ActionIntentCall) {
                String phoneNumber = ((ActionIntentCall) actionIntent).getPhoneNumber();
                makeCall(phoneNumber);
            } else if (actionIntent instanceof ActionIntentSendEmail) {
                String email = ((ActionIntentSendEmail) actionIntent).getEmail();
                sendEmail(email);
            }
        }));
    }

    private void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("text/plain");
        emailIntent.setData(Uri.parse("mailto:" + email));
        startActivity(emailIntent);
    }

    private void makeCall(String phone) {
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(call);
    }
}
