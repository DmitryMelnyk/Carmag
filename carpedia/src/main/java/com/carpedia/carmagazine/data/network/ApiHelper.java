package com.carpedia.carmagazine.data.network;

import com.carpedia.carmagazine.data.db.entity.UserEntity;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by d264 on 12/26/17.
 */

public interface ApiHelper {

    @GET("/v2/{address}?mocky-delay=2ms") // delay 2 seconds
    Single<List<UserEntity>> getUsersApiCall(@Path("address") String address);
}
