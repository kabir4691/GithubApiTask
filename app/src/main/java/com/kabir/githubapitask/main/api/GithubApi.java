package com.kabir.githubapitask.main.api;

import com.kabir.githubapitask.main.model.CommitItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Kabir on 27-06-2016.
 */
public interface GithubApi {

    String GET_COMMITS_API_TAG = "get_commits_api_tag";

    Observable<List<CommitItem>> getCommits(int page, int perPage);

    void cancelGetCommitsCalls();
}