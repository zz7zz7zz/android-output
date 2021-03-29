package com.open.test.thirdparty.rxjava;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.open.test.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends Activity {

    private String TAG = "RxJavaActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxjava);


        findViewById(R.id.thirdparty_rxjava).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo();
            }
        });
    }

    private void demo(){

        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.e(TAG,"subscribe " + Thread.currentThread().getName() );

                e.onNext("连载1"+ Thread.currentThread().getName());
                e.onNext("连载2"+ Thread.currentThread().getName());
                e.onNext("连载3"+ Thread.currentThread().getName());
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())//执行在io线程
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG,"onSubscribe " + Thread.currentThread().getName() );
                    }

                    @Override
                    public void onNext(String value) {
                        Log.e(TAG,"onNext " + value +" "+ Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"onError "+ Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG,"onComplete "+ Thread.currentThread().getName());
                    }
                });
    }

}
