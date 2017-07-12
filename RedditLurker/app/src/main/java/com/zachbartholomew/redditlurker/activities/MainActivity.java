package com.zachbartholomew.redditlurker.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.zachbartholomew.redditlurker.R;
import com.zachbartholomew.redditlurker.RedditLurkerApplication;
import com.zachbartholomew.redditlurker.view.PostAdapter;
import com.zachbartholomew.redditlurker.viewmodel.FeedViewModel;
import com.zachbartholomew.redditlurker.viewmodel.PostViewModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @ViewById(R.id.postList)
    RecyclerView postList;

    @OptionsMenuItem(R.id.progress)
    MenuItem loadingMenuItem;

    @Inject
    FeedViewModel mViewModel;

    private PostAdapter mPostAdapter;
    private LinearLayoutManager postListLayoutManager;

    /**
     * Hold active loading observable subscriptions, so that they can be disposed from when the activity is destroyed
     */
    private CompositeDisposable subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RedditLurkerApplication) getApplication()).component().inject(this);

        subscriptions = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        subscriptions.dispose();
    }

    @AfterViews
    void init() {
        initViews();
        initBindings();

        // Initial page load
        loadNextPage();
    }

    private void initViews() {
        postListLayoutManager = new LinearLayoutManager(this);
        postList.setLayoutManager(postListLayoutManager);

        mPostAdapter = new PostAdapter();
        postList.setAdapter(mPostAdapter);
    }

    private void initBindings() {
        // Observable that emits when the RecyclerView is scrolled to the bottom
        Observable<Void> infiniteScrollObservable =
                Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Void> subscriber) throws Exception {

                postList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        int totalItemCount = postListLayoutManager.getItemCount();
                        int visibleItemCount = postListLayoutManager.getChildCount();
                        int firstVisibleItem = postListLayoutManager.findFirstVisibleItemPosition();
                        Log.d(TAG, "onScrolled: total item count: " + totalItemCount +
                        "\nvisibleItemCount " + visibleItemCount +
                                "\nfirstVisibleItem " + firstVisibleItem);

                        if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                            loadNextPage();
                        }
                    }
                });
            }
        });

        subscriptions.addAll(
                // Bind list of posts to the RecyclerView
                mViewModel
                        .postsObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<PostViewModel>>() {
                            @Override
                            public void accept(@NonNull List<PostViewModel> items) throws Exception {
                                mPostAdapter.setItems(items);
                            }
                        }),

                // Bind loading status to show/hide loading spinner
                mViewModel
                        .isLoadingObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(@NonNull Boolean isLoading) throws Exception {
                                MainActivity.this.setIsLoading(isLoading);
                            }
                        }),

                // Trigger next page load when RecyclerView is scrolled to the bottom
                infiniteScrollObservable.subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(@NonNull Void x) throws Exception {
                        MainActivity.this.loadNextPage();
                    }
                })
        );
    }

    private void loadNextPage() {
        subscriptions.add(mViewModel.loadMorePosts().subscribe()
        );
    }

    private void setIsLoading(boolean isLoading) {
        if (loadingMenuItem != null) {
            loadingMenuItem.setVisible(isLoading);
        }
    }
}
