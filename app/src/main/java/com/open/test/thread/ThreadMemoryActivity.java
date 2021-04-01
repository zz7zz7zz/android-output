package com.open.test.thread.threadpool;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.open.test.R;

public class ThreadMemoryActivity extends Activity {

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                Thread.sleep(100000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net_okhttp);
        thread.setName("ThreadMemoryThread");
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.interrupt();
    }
}
