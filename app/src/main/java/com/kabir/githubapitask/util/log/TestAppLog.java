package com.kabir.githubapitask.util.log;

import android.support.annotation.NonNull;

/**
 * Created by Kabir on 30-06-2016.
 */
public class TestAppLog extends AppLog {

    @Override
    public void d(@NonNull String tag, @NonNull String msg) {

    }

    @Override
    public void e(@NonNull String tag, @NonNull String msg) {
        super.e(tag, msg);
    }
}
