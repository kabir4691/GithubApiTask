package com.kabir.githubapitask.main.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kabir.githubapitask.main.api.GithubApi;
import com.kabir.githubapitask.main.model.CommitItem;
import com.kabir.githubapitask.main.view.CommitsView;
import com.kabir.githubapitask.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Kabir on 27-06-2016.
 */
public class CommitsPresenterImpl implements CommitsPresenter {

    private CommitsView commitsView;

    private GithubApi githubApi;
    private CompositeSubscription compositeSubscription;

    private List<CommitItem> commitItemList;

    public CommitsPresenterImpl(@NonNull CommitsView commitsView, @NonNull GithubApi githubApi) {
        this.commitsView = commitsView;
        this.githubApi = githubApi;
    }

    @Override
    public boolean attachView(@NonNull Intent intent) {

        compositeSubscription = new CompositeSubscription();
        return true;
    }

    @Override
    public void detachView() {

        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
        githubApi.cancelGetCommitsCalls();
    }

    @Override
    public void loadData() {

        Observable<List<CommitItem>> observable = githubApi.getCommits(1, 25);
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<CommitItem>>() {
                    @Override
                    public void call(List<CommitItem> commitItems) {
                        if (!commitsView.isViewDestroyed()) {

                            commitItemList = commitItems;
                            commitsView.setContent(commitItems);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void onQuerySubmit(@NonNull String query) {

        Log.d("QUERY", query);

        if (ListUtils.isNullOrEmpty(commitItemList)) {
            Log.e(TAG, "list is null or empty");
            return;
        }

        if (query.isEmpty()) {
            Log.e(TAG, "query is empty");
            return;
        }

        String queryLowerCase = query.toLowerCase();

        List<CommitItem> searchList = new ArrayList<>();
        for (CommitItem commitItem : commitItemList) {

            String authorLowerCase = commitItem.getAuthor().toLowerCase();
            String messageLowerCase = commitItem.getMessage().toLowerCase();

            if (authorLowerCase.contains(queryLowerCase) || messageLowerCase.contains(queryLowerCase)) {
                searchList.add(commitItem);
            }
        }

        commitsView.setContent(searchList);
        commitsView.scrollToTop();
    }
}
