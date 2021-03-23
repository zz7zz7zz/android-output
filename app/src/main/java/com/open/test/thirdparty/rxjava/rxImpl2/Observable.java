package com.open.test.thirdparty.rxjava.rxImpl2;


public abstract class Observable<T> implements ObservableSource{

    @Override
    public void subscribe(Observer observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<T> observer);


    public static <T> Observable<T> create(ObservableOnSubscribe<T> source){
        return new ObservableCreate<T>(source);
    }

    public <U> Observable<U> map(final Function<T,U> function){
        return new ObserverMap<>(this,function);
    }


    public <T>Observable<T> subscribeOn(){
        return new ObservableSubscribeOn<>(this);
    }

    public <T>Observable<T> observerOn(){
        return new ObservabeObserverOn<>(this);
    }
}
