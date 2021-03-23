package com.open.test.thirdparty.rxjava.rxImpl2;

public class ObservableCreate<T> extends Observable<T> {

    ObservableOnSubscribe<T> source;

    public ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    public void subscribe(Observer observer) {
        super.subscribe(observer);
    }

    @Override
    protected void subscribeActual(Observer observer) {
        CreateEmitter<T> parent = new CreateEmitter<>(observer);
        observer.onSubscribe();
        source.subscribe(parent);
    }



    static final class CreateEmitter<T> implements Emitter<T>{

        Observer<T> observer;

        public CreateEmitter(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(T value) {
            observer.onNext(value);
        }

        @Override
        public void onError(Throwable throwable) {
            observer.onError(throwable);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    }
}
