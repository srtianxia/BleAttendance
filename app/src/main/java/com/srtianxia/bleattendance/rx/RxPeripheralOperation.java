package com.srtianxia.bleattendance.rx;

import rx.Observable;
import rx.subjects.ReplaySubject;

/**
 * Created by srtianxia on 2016/11/26.
 */

public abstract class RxPeripheralOperation<T> implements Runnable {
    private ReplaySubject<T> mReplaySubject = ReplaySubject.create();


    protected final void onNext(T next) {
        mReplaySubject.onNext(next);
    }


    protected final void onError(Throwable throwable) {
        mReplaySubject.onError(throwable);
    }


    protected final void onComleted() {
        mReplaySubject.onCompleted();
    }


    public Observable<T> asObservable() {
        return mReplaySubject;
    }


    @Override public void run() {
        protectedRun();
    }


    protected abstract void protectedRun();
}
