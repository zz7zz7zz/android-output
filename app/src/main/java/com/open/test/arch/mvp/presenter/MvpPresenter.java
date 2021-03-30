package com.open.test.arch.mvp.presenter;

import android.os.Handler;
import android.os.Looper;

import com.open.test.arch.mvp.model.MvpModel;
import com.open.test.arch.mvp.view.IMVPView;


public class MvpPresenter {

    IMVPView view;

    MvpModel model;

    public MvpPresenter(IMVPView view, MvpModel model) {
        this.view = view;
        this.model = model;
    }

    public void onUpdate(){
        new Thread(new Runnable() {

            int count = 0;
            @Override
            public void run() {

                while (true){

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    count++;
                    model.setName("Yang " + count);
                    model.setPsd("Psd " + count);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            view.onUpdateView(model);
                        }
                    });
                }

            }
        }).start();

    }

}
