package com.carpedia.carmagazine.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.carpedia.carmagazine.data.db.entity.UserEntity;

import java.util.List;

/**
 * Created by d264 on 12/26/17.
 */

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<UserEntity> users);

    @Query("SELECT * FROM user")
    LiveData<List<UserEntity>> loadUsers();

    @Query("SELECT * FROM user ORDER BY rating DESC")
    LiveData<List<UserEntity>> loadUsersByRatingDesc();

    @Query("SELECT * FROM user ORDER BY rating ASC")
    LiveData<List<UserEntity>> loadUsersByRatingAsc();

    @Query("DELETE FROM user")
    void clearTable();
}
