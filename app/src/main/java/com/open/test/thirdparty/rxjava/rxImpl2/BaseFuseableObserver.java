package com.open.test.thirdparty.rxjava.rxImpl2;

public abstract class BaseFuseableObserver<T,R> implements Observer<T> {

    final Observer<R> actual;

    public BaseFuseableObserver(Observer<R> actual) {
        this.actual = actual;
    }

    @Override
    public void onSubscribe() {
        actual.onSubscribe();
    }

    @Override
    public void onError(Throwable throwable) {
        actual.onError(throwable);
    }

    @Override
    public void onComplete() {
        actual.onComplete();
    }
}
