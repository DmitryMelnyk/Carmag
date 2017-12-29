package com.carpedia.carmagazine.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.carpedia.carmagazine.data.db.converter.CarConverter;
import com.carpedia.carmagazine.data.db.dao.UserDao;
import com.carpedia.carmagazine.data.db.entity.UserEntity;

import timber.log.Timber;

/**
 * Created by d264 on 12/26/17.
 */

@Database(entities = {UserEntity.class}, version = 1)
@TypeConverters(CarConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "basic-db";
    private static AppDatabase sInstance;

    public abstract UserDao userDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext, AppExecutors executors) {
        Timber.d("building db");
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIo().execute(() -> {
                            // Generate the data for pre-population
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}