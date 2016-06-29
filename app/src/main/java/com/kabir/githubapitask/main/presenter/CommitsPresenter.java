package com.kabir.githubapitask.main.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by Kabir on 27-06-2016.
 */
public interface CommitsPresenter {

    String TAG = "CommitsPresenter";

    boolean attachView(@NonNull Intent intent);
    void detachView();

    void loadData();

    void onQuerySubmit(@NonNull String query);
}
