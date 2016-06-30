package com.kabir.githubapitask.util.scheduler;


import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Kabir on 30-06-2016.
 */
public class TestRxSchedulersHook extends RxSchedulersHook {

    @Override
    public Scheduler getMainThreadScheduler() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.immediate();
    }
}
