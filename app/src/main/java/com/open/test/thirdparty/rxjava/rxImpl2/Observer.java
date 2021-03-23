package com.open.test.thirdparty.rxjava.rxImpl2;

/**
 * 观察者
 * @param <T>
 */
public interface Observer<T> {

    /**
     * 订阅成功
     */
    void onSubscribe();

    /**
     * 收到消息
     */
    void onNext(T value);

    /**
     * 出错的回调=
     */
    void onError(Throwable throwable);

    /**
     * 消息全部发送完成
     */
    void onComplete();

}
