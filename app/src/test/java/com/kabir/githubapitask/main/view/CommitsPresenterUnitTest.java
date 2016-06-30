package com.kabir.githubapitask.main.view;

import android.content.Context;
import android.content.Intent;
import android.test.suitebuilder.annotation.SmallTest;

import com.kabir.githubapitask.main.api.GithubApi;
import com.kabir.githubapitask.main.model.CommitItem;
import com.kabir.githubapitask.main.presenter.CommitsPresenter;
import com.kabir.githubapitask.main.presenter.CommitsPresenterImpl;
import com.kabir.githubapitask.util.log.AppLog;
import com.kabir.githubapitask.util.log.TestAppLog;
import com.kabir.githubapitask.util.scheduler.RxSchedulersHook;
import com.kabir.githubapitask.util.scheduler.TestRxSchedulersHook;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Kabir on 30-06-2016.
 */
@RunWith(MockitoJUnitRunner.class)
@SmallTest
public class CommitsPresenterUnitTest {

    @Mock private CommitsView commitsView;
    @Mock private GithubApi githubApi;

    @Mock
    private Context context;

    @Mock
    private CommitItem mockCommitItem;

    @Mock
    private Intent intent;

    private CommitsPresenter commitsPresenter;

    private List<CommitItem> commitItemList;

    private RxSchedulersHook schedulersHook = new TestRxSchedulersHook();
    private AppLog appLog = new TestAppLog();

    private String SEARCH_STRING = "john";

    @Before
    public void setup() {

        when(mockCommitItem.getAvatar())
                .thenReturn("http://imgfave-herokuapp-com.global.ssl.fastly.net/image_cache/1377833758489983.jpg");
        when(mockCommitItem.getAuthor()).thenReturn("Shakespeare");
        when(mockCommitItem.getSha()).thenReturn("Shalini");
        when(mockCommitItem.getMessage()).thenReturn("To commit or not to commit. That's the question.");
        when(mockCommitItem.getShaDisplayText()).thenReturn("Commit: Shalini");

        commitItemList = new ArrayList<>();
        commitItemList.add(mockCommitItem);

        commitsPresenter = new CommitsPresenterImpl(commitsView, githubApi, schedulersHook, appLog);
        commitsPresenter.attachView(intent);
    }

    @Test
    public void checkIfCommitsShown() {

        when(githubApi.getCommits(anyInt(), anyInt())).thenReturn(Observable.just(commitItemList));
        commitsPresenter.loadData();
        verify(commitsView, atLeastOnce()).setContent(commitItemList);
    }

    @Test
    public void checkIfSearchResultsShown() {

        when(githubApi.getCommits(anyInt(), anyInt())).thenReturn(Observable.just(commitItemList));
        commitsPresenter.loadData();
        commitsPresenter.onQuerySubmit(SEARCH_STRING);
        verify(commitsView, atLeastOnce()).setContent(anyListOf(CommitItem.class));
    }
}
