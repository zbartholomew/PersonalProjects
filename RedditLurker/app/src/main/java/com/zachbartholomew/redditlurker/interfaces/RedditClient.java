package com.zachbartholomew.redditlurker.interfaces;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit is used for making the Reddit API calls. The client interface is as follows
 *
 * The after query parameter is used for pagination, where a previously returned page token can be
 * used to get the next page of results after that response. The limit query parameter can be used
 * to limit the number of results returned.
 */

public interface RedditClient {

    @GET("/top.json")
    Observable<RedditObject> getTop(@Query("after") String after,
                                    @Query("limit") int limit);
}
