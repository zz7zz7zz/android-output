package com.open.test.thirdparty.rxjava.rxImpl2;

public class ObserverMap<T,U> extends AbstractObservableWithUpStream<T,U>{


    final Function<T,U> function;

    public ObserverMap(ObservableSource<T> source, Function<T, U> function) {
        super(source);
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer<U> observer) {
        source.subscribe(new MapObserver<>(observer,function));
    }

    static final class MapObserver<T,U> extends BaseFuseableObserver<T,U> {

        final Function<T,U> function;

        public MapObserver(Observer<U> actual, Function<T, U> function) {
            super(actual);
            this.function = function;
        }

        @Override
        public void onNext(T t) {
            U u = function.apply(t);
            actual.onNext(u);

        }
    }
}
