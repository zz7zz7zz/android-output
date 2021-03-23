package com.open.test.thirdparty.rxjava.rxImpl2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ObservableSubscribeOn<T,U> extends AbstractObservableWithUpStream<T,T>{

    private ExecutorService executor = Executors.newCachedThreadPool();

    public ObservableSubscribeOn(ObservableSource<T> source) {
        super(source);
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        final SubscribeOnObserver<T> parent = new SubscribeOnObserver<>(observer);
        observer.onSubscribe();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                source.subscribe(parent);
            }
        });
    }

    static final class SubscribeOnObserver<T> implements Observer<T> {

        final Observer<T> actual;

        public SubscribeOnObserver(Observer<T> actual) {
            this.actual = actual;
        }

        @Override
        public void onSubscribe() {
//            actual.onSubscribe();
        }

        @Override
        public void onNext(T value) {
            actual.onNext(value);
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
}
