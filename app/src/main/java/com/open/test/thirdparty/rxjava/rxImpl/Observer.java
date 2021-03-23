package com.open.test.thirdparty.rxjava.rxImpl;


public interface Observer<T> {

    void onSubscribe();

    void onNext(T value);

    void onError();

    void onComplete();

}
