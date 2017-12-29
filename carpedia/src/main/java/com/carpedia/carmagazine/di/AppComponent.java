package com.carpedia.carmagazine.di;

import android.app.Application;

import com.carpedia.carmagazine.app.App;
import com.carpedia.carmagazine.data.db.AppExecutors;
import com.carpedia.carmagazine.data.db.DbModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by d264 on 12/26/17.
 */

@Singleton
@Component(modules = {
        ActivityBindingModule.class,
        ApplicationModule.class,
        AndroidSupportInjectionModule.class,
        DbModule.class
})
public interface AppComponent extends AndroidInjector<App>{

    AppExecutors getAppExecutors();

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
