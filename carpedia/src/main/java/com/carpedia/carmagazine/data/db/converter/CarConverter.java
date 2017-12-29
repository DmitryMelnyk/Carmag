package com.carpedia.carmagazine.data.db.converter;

import android.arch.persistence.room.TypeConverter;

import com.carpedia.carmagazine.model.Car;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by d264 on 12/26/17.
 */

public class CarConverter {
    @TypeConverter
    public static List<Car> stringToCars(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Car>>() { }.getType();
        List<Car> cars = gson.fromJson(json, type);
        return cars;
    }

    @TypeConverter
    public static String carsToString(List<Car> cars) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Car>>() { }.getType();
        String json = gson.toJson(cars, type);
        return json;
    }
}
