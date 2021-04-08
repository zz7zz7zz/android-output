package com.open.test.jetpack.room;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.open.test.R;

import java.util.List;

public class JetPackRoomActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "JetPackRoomActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jetpack_room);

        findViewById(R.id.room_insert).setOnClickListener(this);
        findViewById(R.id.room_delete).setOnClickListener(this);
        findViewById(R.id.room_query).setOnClickListener(this);
        findViewById(R.id.room_update).setOnClickListener(this);
    }

    static long id = System.currentTimeMillis();

    @Override
    public void onClick(View v) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (v.getId()){
                    case R.id.room_insert:
                        User user = new User(id,"name_"+id,"psd_"+id);
                        UserDataBase.getInstance(getApplicationContext()).userDao().insert(user);
                        id++;
                        break;

                    case R.id.room_delete:
                        user = new User(id,"name_"+id,"psd_"+id);
                        id--;
                        UserDataBase.getInstance(getApplicationContext()).userDao().delete(user);
                        break;

                    case R.id.room_query:
                        List<User> list= UserDataBase.getInstance(getApplicationContext()).userDao().query();
                        for (User user1:list){
                            Log.v(TAG,user1.toString());
                        }
                        break;

                    case R.id.room_update:
                        user = new User(id,"name_update_"+id,"psd_update_"+id);
                        id--;
                        UserDataBase.getInstance(getApplicationContext()).userDao().updateUser(user);
                        break;
                }
            }
        }).start();

    }
}
