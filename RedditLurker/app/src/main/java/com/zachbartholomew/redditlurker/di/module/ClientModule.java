package com.zachbartholomew.redditlurker.di.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zachbartholomew.redditlurker.BuildConfig;
import com.zachbartholomew.redditlurker.deserializer.RedditDateDeserializer;
import com.zachbartholomew.redditlurker.deserializer.RedditObjectJsonDeserializer;
import com.zachbartholomew.redditlurker.interfaces.RedditClient;
import com.zachbartholomew.redditlurker.interfaces.RedditObject;

import java.util.Date;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Client Module utilizing dagger2
 */

@Module
public class ClientModule {

    @Provides
    public RedditClient provideRedditClient() {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(RedditObject.class, new RedditObjectJsonDeserializer())
                .registerTypeAdapter(Date.class, new RedditDateDeserializer())
                .create();

        RxJava2CallAdapterFactory rxAdapter =
                RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(interceptor);
        }

        OkHttpClient client = okHttpClientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://reddit.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();

        return retrofit.create(RedditClient.class);
    }
}
