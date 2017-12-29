package com.carpedia.carmagazine.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by d264 on 12/26/17.
 */

public interface User extends Parcelable {
    int getId();
    String getFirstName();
    String getLastName();
    String getPhone();
    String getEmail();
    String getAvatar();
    Integer getRating();
    List<Car> getCars();

    @Override
    int describeContents();

    @Override
    void writeToParcel(Parcel parcel, int i);
}
