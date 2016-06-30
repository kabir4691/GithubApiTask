package com.kabir.githubapitask.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.kabir.githubapitask.R;
import com.kabir.githubapitask.main.api.GithubApiProvider;
import com.kabir.githubapitask.main.model.CommitItem;
import com.kabir.githubapitask.main.presenter.CommitsPresenter;
import com.kabir.githubapitask.main.presenter.CommitsPresenterImpl;
import com.kabir.githubapitask.util.scheduler.RxSchedulersHook;
import com.kabir.githubapitask.util.image.provider.GlideImageProvider;
import com.kabir.githubapitask.util.log.AppLog;

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

        commitsAdapter = new CommitsAdapter(this, new GlideImageProvider(this));
        contentRecyclerView.setAdapter(commitsAdapter);

        commitsPresenter = new CommitsPresenterImpl(this, new GithubApiProvider(), new RxSchedulersHook(),
                                                    new AppLog());
        if (commitsPresenter.attachView(getIntent())) {
            commitsPresenter.loadData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (commitsPresenter != null) {
                    commitsPresenter.onQuerySubmit(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
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

    @Override
    public void scrollToTop() {
        if (commitsAdapter.getItemCount() > 0) {
            contentRecyclerView.smoothScrollToPosition(0);
        }
    }
}
