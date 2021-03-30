package com.open.test.arch.mvc.controller;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.open.test.R;
import com.open.test.arch.mvc.model.MvcModel;

public class MvcActivity extends Activity {

    MvcModel model = new MvcModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arch_mvc);

        updateView();
    }

    private void updateView(){
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
                            ((TextView)(findViewById(R.id.arch_name))).setText(model.getName());
                            ((TextView)(findViewById(R.id.arch_psd))).setText(model.getPsd());
                        }
                    });
                }

            }
        }).start();
    }

}
