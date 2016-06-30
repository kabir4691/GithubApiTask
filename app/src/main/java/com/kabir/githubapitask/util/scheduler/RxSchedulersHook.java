package com.kabir.githubapitask.util.scheduler;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kabir on 30-06-2016.
 */
public class RxSchedulersHook {

    public Scheduler getMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler getIOScheduler() {
        return Schedulers.io();
    }

    public Scheduler getComputationScheduler() {
        return Schedulers.computation();
    }
}
