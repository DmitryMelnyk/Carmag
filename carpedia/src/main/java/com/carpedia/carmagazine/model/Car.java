package com.carpedia.carmagazine.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by d264 on 12/26/17.
 */

public class Car implements Parcelable {

    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("year")
    @Expose
    private Integer year;

    @SerializedName("price")
    @Expose
    private Integer price;

    @SerializedName("img")
    @Expose
    private String img;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(model);
        parcel.writeInt(year);
        parcel.writeInt(price);
        parcel.writeString(img);
    }
}