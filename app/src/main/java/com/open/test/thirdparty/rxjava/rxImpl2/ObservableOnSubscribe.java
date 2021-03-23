package com.open.test.thirdparty.rxjava.rxImpl2;

public interface ObservableOnSubscribe<T> {


    void subscribe(Emitter<T> emitter);

}
