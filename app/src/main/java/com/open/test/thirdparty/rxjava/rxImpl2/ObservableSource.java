package com.open.test.thirdparty.rxjava.rxImpl2;

/**
 * 被观察者
 * @param <T>
 */
public interface ObservableSource<T> {

    void subscribe(Observer<T> observer);

}
