package com.open.test.thirdparty.rxjava.rxImpl2;

import android.os.Looper;


public class ObservabeObserverOn<T,U> extends AbstractObservableWithUpStream<T,T>{


    public ObservabeObserverOn(ObservableSource<T> source) {
        super(source);
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        final ObserverOnObserver<T> parent = new ObserverOnObserver<>(observer);
        source.subscribe(parent);
    }

    static final class ObserverOnObserver<T> implements Observer<T> {

        final Observer<T> actual;
        private android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());

        public ObserverOnObserver(Observer<T> actual) {
            this.actual = actual;
        }

        @Override
        public void onSubscribe() {
            actual.onSubscribe();
        }

        @Override
        public void onNext(final T value) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    actual.onNext(value);
                }
            });
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
