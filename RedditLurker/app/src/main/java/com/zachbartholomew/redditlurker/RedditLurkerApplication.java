package com.zachbartholomew.redditlurker;

import android.app.Application;

import com.zachbartholomew.redditlurker.di.ApplicationComponent;
import com.zachbartholomew.redditlurker.di.DaggerApplicationComponent;
import com.zachbartholomew.redditlurker.di.module.ApplicationModule;

import org.androidannotations.annotations.EApplication;

/**
 * Enhancing the application class
 */

@EApplication
public class RedditLurkerApplication extends Application {

    private ApplicationComponent applicationComponent;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent component() {
        return applicationComponent;
    }
}
