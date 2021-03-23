package com.open.test.thirdparty.rxjava.rxImpl2;

public interface Emitter<T> {

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
