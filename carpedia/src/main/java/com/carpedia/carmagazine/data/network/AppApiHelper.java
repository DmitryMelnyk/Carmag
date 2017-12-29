package com.carpedia.carmagazine.data.network;

import com.carpedia.carmagazine.data.db.entity.UserEntity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by d264 on 12/26/17.
 */

public class AppApiHelper {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiEndpoint.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private ApiHelper apiHelper = retrofit.create(ApiHelper.class);

    @Inject
    public AppApiHelper() { }

    public Single<List<UserEntity>> getUserList() {
        return apiHelper.getUsersApiCall(ApiEndpoint.MOCKY_USERS);
    }

    public Single<List<UserEntity>> getUserList2() {
        return apiHelper.getUsersApiCall(ApiEndpoint.MOCKY_USERS);
    }
}
