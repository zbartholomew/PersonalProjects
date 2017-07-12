package com.zachbartholomew.redditlurker.di.module;

import com.zachbartholomew.redditlurker.RedditLurkerApplication;

import dagger.Module;

/**
 * Application Module utilizing dagger2
 */

@Module
public class ApplicationModule {

    private RedditLurkerApplication application;

    public ApplicationModule(RedditLurkerApplication application) {
        this.application = application;
    }
}
