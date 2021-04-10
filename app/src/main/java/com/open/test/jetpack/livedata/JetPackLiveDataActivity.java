package com.open.test.jetpack.livedata;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.open.test.R;
import com.open.test.jetpack.room.User;
import com.open.test.jetpack.room.UserDataBase;

import java.util.List;

public class JetPackLiveDataActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "JetPackLiveDataActivity";

    private MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jetpack_livedata);

        findViewById(R.id.livedata_sendMessage).setOnClickListener(this);
        mutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.v(TAG,"onChanged " + s);
            }
        });
    }

    int count = 1;
    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    Thread.sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }

                mutableLiveData.postValue("I am value " + count);
                count++;
            }
        }).start();

//        mutableLiveData.setValue("I am value " + count);
//        count++;
    }
}
