package com.open.test.thirdparty.rxjava;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.open.test.R;
import com.open.test.thirdparty.rxjava.rxImpl2.Emitter;
import com.open.test.thirdparty.rxjava.rxImpl2.Function;
import com.open.test.thirdparty.rxjava.rxImpl2.Observable;
import com.open.test.thirdparty.rxjava.rxImpl2.ObservableOnSubscribe;
import com.open.test.thirdparty.rxjava.rxImpl2.Observer;

public class RxJavaActivity2 extends Activity {

    private String TAG = "RxJavaActivity2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxjava);


        findViewById(R.id.rxjava).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo1();
//                demo();
            }
        });
    }

    private void demo1(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(Emitter<String> emitter) {
                emitter.onNext("大家好");
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s + " apply 1 ";
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s + " apply 2 ";
            }
        })
                .subscribeOn()
                .observerOn().subscribe(new Observer() {
            @Override
            public void onSubscribe() {
                Log.e(TAG,"onSubscribe "+ Thread.currentThread().getName());
            }

            @Override
            public void onNext(Object value) {
                Log.e(TAG,"onNext " + value + " "+ Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG,"onError "+ Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"onComplete "+ Thread.currentThread().getName());
            }
        });
    }

//    private void demo(){
//        Observable.create(new Observable<String>() {
//            @Override
//            public void subscrible(Observer<String> observer) {
//                Log.e(TAG,"subscrible "+ Thread.currentThread().getName());
//
//                observer.onNext("A " + Thread.currentThread().getName());
//                observer.onNext("B "+ Thread.currentThread().getName());
//                observer.onComplete();
//            }
//        })
//        .map(new Function<String, Bitmap>() {
//            @Override
//            public Bitmap apply(String s) {
//                Log.e(TAG,"map.apply "+ Thread.currentThread().getName());
//                return null;
//            }
//        })
//        .subscribeOn()
//        .observerOn().subscrible(new Observer<Bitmap>() {
//            @Override
//            public void onSubscribe() {
//                Log.e(TAG,"onSubscribe "+ Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onNext(Bitmap value) {
//                Log.e(TAG,"onNext " + value + " "+ Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onError() {
//                Log.e(TAG,"onError " + Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG,"onComplete "+ Thread.currentThread().getName());
//            }
//        });
//    }


}
