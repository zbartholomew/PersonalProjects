package com.zachbartholomew.redditlurker.viewmodel;

import com.zachbartholomew.redditlurker.interfaces.RedditClient;
import com.zachbartholomew.redditlurker.interfaces.RedditObject;
import com.zachbartholomew.redditlurker.model.RedditLink;
import com.zachbartholomew.redditlurker.model.RedditListing;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;

/**
 * contains a single operation -
 */

public class FeedViewModel {

    private RedditClient redditClient;

    private int pageLimit;
    private String afterToken;
    private BehaviorSubject<ArrayList<PostViewModel>> postSubject
            = BehaviorSubject.createDefault(new ArrayList<PostViewModel>());
    private BehaviorSubject<Boolean> isLoadingSubject = BehaviorSubject.createDefault(false);


    @Inject
    public FeedViewModel(RedditClient redditClient) {
        this.redditClient = redditClient;
        this.pageLimit = 25;
    }

    public Observable<List<PostViewModel>> loadMorePosts() {

        // Quit if already loading
        if (isLoadingSubject.getValue()) {
            return Observable.empty();
        }

        isLoadingSubject.onNext(true);

        return redditClient
                .getTop(afterToken, pageLimit)
                // Safe to cast to RedditListing, as this is always returned from top posts
                .cast(RedditListing.class)
                // Store the after token, so we can use it to get the next page of posts is a subsequent load
                .doOnNext(new Consumer<RedditListing>() {
                    @Override
                    public void accept(@NonNull RedditListing listing) throws Exception {
                        afterToken = listing.getAfter();
                    }
                })
                // Flatten into observable of RedditLinks
                .map(new Function<RedditListing, List<RedditObject>>() {
                    @Override
                    public List<RedditObject> apply(@NonNull RedditListing redditListing) throws Exception {
                        return redditListing.getChildren();
                    }
                })
                .flatMapIterable(new Function<List<RedditObject>, Iterable<? extends RedditObject>>() {
                    @Override
                    public Iterable<? extends RedditObject> apply(@NonNull List<RedditObject> list) throws Exception {
                        return list;
                    }
                })
                .filter(new Predicate<RedditObject>() {
                    @Override
                    public boolean test(@NonNull RedditObject object) throws Exception {
                        return object instanceof RedditLink;
                    }
                })
                // Transform model to viewmodel
                .map(new Function<RedditObject, PostViewModel>() {
                    @Override
                    public PostViewModel apply(@NonNull RedditObject link) throws Exception {
                        return new PostViewModel((RedditLink) link);
                    }
                })
                // Merge viewmodels into a single list to be emitted
                .toList()
                // Concatenate the new posts to the current posts list, then emit it via the post subject
                .doOnEvent(new BiConsumer<List<PostViewModel>, Throwable>() {
                    @Override
                    public void accept(List<PostViewModel> list, Throwable throwable) throws Exception {
                        ArrayList<PostViewModel> fullList = new ArrayList<>(postSubject.getValue());
                        fullList.addAll(list);
                        postSubject.onNext(fullList);
                    }
                })
                // rxjava1 doOnTerminate would fire in cases of error and success.
                // rxjava2 we need to include both the error and success methods
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        isLoadingSubject.onNext(false);
                    }
                })
                .doOnSuccess(new Consumer<List<PostViewModel>>() {
                    @Override
                    public void accept(@NonNull List<PostViewModel> list) throws Exception {
                        isLoadingSubject.onNext(false);
                    }
                })
                .toObservable();
    }

    public Observable<ArrayList<PostViewModel>> postsObservable() {
        // rxjava1 asObservable() renamed to .hide() in rxjava2
        return postSubject.hide();
    }

    public Observable<Boolean> isLoadingObservable() {
        return isLoadingSubject.hide();
    }

}
