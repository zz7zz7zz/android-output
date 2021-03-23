package com.open.test.thirdparty.rxjava.rxImpl;

import android.os.Looper;

public abstract class Observable<T> {

    public abstract void subscribe(Observer<T> observer);

    public static <T> Observable<T> create(Observable<T> observable){
        return observable;
    }


    public <R> Observable<R> map(final Function<T,R> function){
        return new Observable<R>() {
            @Override
            public void subscribe(final Observer<R> observer) {
                Observer<T> _Observer = new Observer<T>(){

                    @Override
                    public void onSubscribe() {
                        observer.onSubscribe();
                    }

                    @Override
                    public void onNext(T t) {
                        R r = function.apply(t);
                        observer.onNext(r);
                    }

                    @Override
                    public void onError() {
                        observer.onError();
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                };

                Observable.this.subscribe(_Observer);
            }
        };
    }

    public Observable<T> subscribeOn(){
        return new Observable<T>() {
            @Override
            public void subscribe(final Observer<T> observer) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Observer<T> _Observer = new Observer<T>(){

                            @Override
                            public void onSubscribe() {
                                observer.onSubscribe();
                            }

                            @Override
                            public void onNext(T value) {
                                observer.onNext(value);
                            }

                            @Override
                            public void onError() {
                                observer.onError();
                            }

                            @Override
                            public void onComplete() {
                                observer.onComplete();
                            }
                        };

                        Observable.this.subscribe(_Observer);
                    }
                }).start();

            }
        };
    }

    final android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
    public Observable<T> observerOn(){
        return new Observable<T>() {
            @Override
            public void subscribe(final Observer<T> observer) {


                        Observer<T> _Observer = new Observer<T>(){

                            @Override
                            public void onSubscribe() {
                                observer.onSubscribe();
                            }

                            @Override
                            public void onNext(T value) {
                                observer.onNext(value);
                            }

                            @Override
                            public void onError() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        observer.onError();
                                    }
                                });
                            }

                            @Override
                            public void onComplete() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        observer.onComplete();
                                    }
                                });
                            }
                        };

                        Observable.this.subscribe(_Observer);
            }

        };
    }

    public Observable<T> flatMap(){
        return new Observable<T>() {
            @Override
            public void subscribe(final Observer<T> observer) {
                Observer<T> _Observer = new Observer<T>(){

                    @Override
                    public void onSubscribe() {
                        observer.onSubscribe();
                    }

                    @Override
                    public void onNext(T value) {
                        observer.onNext(value);
                    }

                    @Override
                    public void onError() {
                        observer.onError();
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                };

                Observable.this.subscribe(_Observer);
            }
        };
    }


}
