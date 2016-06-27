package com.kabir.githubapitask.main.view;

import android.support.annotation.Nullable;

import com.kabir.githubapitask.main.model.CommitItem;

import java.util.List;

/**
 * Created by Kabir on 27-06-2016.
 */
public interface CommitsView {

    boolean isViewDestroyed();

    void setContent(@Nullable List<CommitItem> commitItems);
}
