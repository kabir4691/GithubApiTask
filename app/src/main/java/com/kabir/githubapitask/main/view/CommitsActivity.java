package com.kabir.githubapitask.main.view;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kabir.githubapitask.R;
import com.kabir.githubapitask.main.api.GithubApiProvider;
import com.kabir.githubapitask.main.model.CommitItem;
import com.kabir.githubapitask.main.presenter.CommitsPresenter;
import com.kabir.githubapitask.main.presenter.CommitsPresenterImpl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kabir on 27-06-2016.
 */
public class CommitsActivity extends AppCompatActivity implements CommitsView {

    @Bind(R.id.content_recycler_view) RecyclerView contentRecyclerView;

    private CommitsAdapter commitsAdapter;

    private CommitsPresenter commitsPresenter;

    private boolean isViewDestroyed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isViewDestroyed = false;

        setContentView(R.layout.commits_activity);
        ButterKnife.bind(this);

        contentRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        commitsAdapter = new CommitsAdapter(this);
        contentRecyclerView.setAdapter(commitsAdapter);

        commitsPresenter = new CommitsPresenterImpl(this, new GithubApiProvider());
        if (commitsPresenter.attachView(getIntent().getExtras())) {
            commitsPresenter.loadData();
        }
    }

    @Override
    protected void onDestroy() {

        isViewDestroyed = true;

        if (commitsPresenter != null) {
            commitsPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public boolean isViewDestroyed() {
        return isViewDestroyed;
    }

    @Override
    public void setContent(@Nullable List<CommitItem> commitItems) {
        commitsAdapter.updateDataSet(commitItems);
    }
}
