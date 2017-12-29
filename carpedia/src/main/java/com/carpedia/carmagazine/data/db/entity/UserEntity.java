package com.carpedia.carmagazine.data.db.entity;

/**
 * Created by d264 on 12/26/17.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.carpedia.carmagazine.data.db.converter.CarConverter;
import com.carpedia.carmagazine.model.Car;
import com.carpedia.carmagazine.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(indices = {@Index("rating")}, tableName = "user")
@TypeConverters(CarConverter.class)
public class UserEntity implements User {

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    @SerializedName("first_name")
    @Expose
    private String firstName;

    @ColumnInfo
    @SerializedName("last_name")
    @Expose
    private String lastName;

    @ColumnInfo
    @SerializedName("rating")
    @Expose
    private Integer rating;

    @ColumnInfo
    @SerializedName("email")
    @Expose
    private String email;

    @ColumnInfo
    @SerializedName("phone")
    @Expose
    private String phone;

    @ColumnInfo
    @SerializedName("avatar")
    @Expose
    private String avatar;

    @ColumnInfo
    @SerializedName("cars")
    @Expose
    private List<Car> cars = null;

    public UserEntity() {
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(phone);
        parcel.writeString(email);
        parcel.writeString(avatar);
        parcel.writeInt(rating);
        parcel.writeList(cars);
    }

    public static final Parcelable.Creator<UserEntity> CREATOR
            = new Parcelable.Creator<UserEntity>() {
        public UserEntity createFromParcel(Parcel in) {
            return new UserEntity(in);
        }

        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };

    private UserEntity(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        phone = in.readString();
        email = in.readString();
        avatar = in.readString();
        rating = in.readInt();
        cars = in.readArrayList(Car.class.getClassLoader());
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "user_id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", rating=" + rating +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", cars=" + cars +
                '}';
    }
}