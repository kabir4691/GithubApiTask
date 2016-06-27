package com.kabir.githubapitask.main.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Kabir on 27-06-2016.
 */
public interface CommitsPresenter {

    boolean attachView(@Nullable Bundle extras);
    void detachView();

    void loadData();
}
