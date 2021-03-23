package com.open.test.thirdparty.rxjava.rxImpl2;

public abstract class AbstractObservableWithUpStream<T,R> extends Observable<R> {

    public final ObservableSource<T> source;

    public AbstractObservableWithUpStream(ObservableSource<T> source) {
        this.source = source;
    }
}
