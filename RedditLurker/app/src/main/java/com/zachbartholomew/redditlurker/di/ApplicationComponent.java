package com.zachbartholomew.redditlurker.di;

import com.zachbartholomew.redditlurker.activities.MainActivity;
import com.zachbartholomew.redditlurker.di.module.ApplicationModule;
import com.zachbartholomew.redditlurker.di.module.ClientModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Application Component utilizing dagger2
 */

@Singleton
@Component(modules = {ApplicationModule.class, ClientModule.class})
public interface ApplicationComponent {
    void inject(MainActivity activity);
}
