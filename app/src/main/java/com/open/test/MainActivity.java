package com.open.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.open.test.aop.JavassistAddCodeActivity;
import com.open.test.aop.AsmFixActivity;
import com.open.test.fragment.FragmentPagerActivity;
import com.open.test.fragment.FragmentStatePagerActivity;
import com.open.test.aop.AspectJPermissionActivity;
import com.open.test.thirdparty.glide.GlideActivity;
import com.open.test.image.Image2Activity;
import com.open.test.image.ImageActivity;
import com.open.test.image.MatrixImageActivity;
import com.open.test.net.NetMainActivity;
import com.open.test.notification.NotificationActivity;
import com.open.test.notification.NotificationMonitorActivity;
import com.open.test.thirdparty.okhttp.OKHttpActivity;
import com.open.test.apm.ProfileActivity;
import com.open.test.thirdparty.rxjava.RxJavaActivity;
import com.open.test.service.ImplQueenService;
import com.open.test.service.TIntentService;
import com.open.test.sharedprefs.SharedPrefsActivity;
import com.open.test.textview.TextViewActivity;
import com.open.test.textview.TextViewLineCountActivity;
import com.open.test.thirdparty.rxjava.RxJavaActivity2;
import com.open.test.viewtouchevent.ViewTouchEventActivity;
import com.open.test.weakReference.WRA_Activity;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        Log.v("MainActivity","onCreate");

//        Debug.startMethodTracing("View.click1");
//        printThreadInfo();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("MainActivity","onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("MainActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("MainActivity","onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("MainActivity","onSaveInstanceState");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("MainActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Debug.stopMethodTracing();
        Log.v("MainActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("MainActivity","onDestroy");
    }


    private void initView(){

        findViewById(R.id.aop_asm).setOnClickListener(clickListener);
        findViewById(R.id.aop_javassist).setOnClickListener(clickListener);
        findViewById(R.id.aop_aspectJ).setOnClickListener(clickListener);

        findViewById(R.id.apm).setOnClickListener(clickListener);

        findViewById(R.id.thirdparty_okHttp).setOnClickListener(clickListener);
        findViewById(R.id.thirdparty_glide).setOnClickListener(clickListener);
        findViewById(R.id.thirdparty_rxjava).setOnClickListener(clickListener);
        findViewById(R.id.thirdparty_rxjava2).setOnClickListener(clickListener);

        findViewById(R.id.viewTouchEvent).setOnClickListener(clickListener);

        findViewById(R.id.net).setOnClickListener(clickListener);


        findViewById(R.id.weakreference).setOnClickListener(clickListener);

        findViewById(R.id.intentService).setOnClickListener(clickListener);
        findViewById(R.id.intentService2).setOnClickListener(clickListener);

        findViewById(R.id.shared_prefs).setOnClickListener(clickListener);

        findViewById(R.id.notification).setOnClickListener(clickListener);
        findViewById(R.id.notification_monitor).setOnClickListener(clickListener);

        findViewById(R.id.fragment_pager).setOnClickListener(clickListener);
        findViewById(R.id.fragment_statepager).setOnClickListener(clickListener);

        findViewById(R.id.image).setOnClickListener(clickListener);
        findViewById(R.id.image2).setOnClickListener(clickListener);
        findViewById(R.id.image3).setOnClickListener(clickListener);

        findViewById(R.id.textView).setOnClickListener(clickListener);
        findViewById(R.id.textView2).setOnClickListener(clickListener);
    }


    public static class News{
        public long id;
        public String content;
    }
    ArrayList<News> newsList = new ArrayList();
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.aop_javassist:
                    startActivity(new Intent(getApplicationContext(), JavassistAddCodeActivity.class));
                    break;

                case R.id.aop_asm:
                    startActivity(new Intent(getApplicationContext(), AsmFixActivity.class));
                    break;

                case R.id.aop_aspectJ:
                    startActivity(new Intent(getApplicationContext(), AspectJPermissionActivity.class));
                    break;

                case R.id.apm:
//                    Trace.beginSection("Section1");

//                    int i = 10000;
//                    while(i>0){
//                        i--;
//                        Log.v("MainActivity", "i " + i);
//                    }
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

//                    Trace.endSection();

//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }


//                    Trace.beginSection("MainActivity");
//                    for (int  i = 0;i<500;i++){
//                        News news = new News();
//                        news.id = i;
//                        int j = new Random().nextInt(100);
//                        for (;j>=0;j--){
//                            news.content += "con = " + j;
//                        }
//                        newsList.add(news);
//                    };
//                    Trace.endSection();

                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    break;

                case R.id.thirdparty_okHttp:
                    startActivity(new Intent(getApplicationContext(), OKHttpActivity.class));
                    break;

                case R.id.thirdparty_glide:
                    startActivity(new Intent(getApplicationContext(), GlideActivity.class));
                    break;

                case R.id.thirdparty_rxjava:
                    startActivity(new Intent(getApplicationContext(), RxJavaActivity.class));
                    break;

                case R.id.thirdparty_rxjava2:
                    startActivity(new Intent(getApplicationContext(), RxJavaActivity2.class));
                    break;

                case R.id.viewTouchEvent:
                    startActivity(new Intent(getApplicationContext(), ViewTouchEventActivity.class));
                    break;

                case R.id.net:
                    startActivity(new Intent(getApplicationContext(), NetMainActivity.class));
                    break;

                case R.id.weakreference:
                    startActivity(new Intent(getApplicationContext(),WRA_Activity.class));
                    break;

                case R.id.intentService:
                    TIntentService.turnOnPush(getApplicationContext());
                    TIntentService.loadConfig(getApplicationContext());
                    break;

                case R.id.intentService2:
                    ImplQueenService.turnOnPush(getApplicationContext());
                    ImplQueenService.loadConfig(getApplicationContext());
                    break;

                case R.id.notification:
                    startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                    break;
                case R.id.notification_monitor:
                    startActivity(new Intent(getApplicationContext(),NotificationMonitorActivity.class));
                    break;


                case R.id.shared_prefs:
                    startActivity(new Intent(getApplicationContext(),SharedPrefsActivity.class));
                    break;

                case R.id.fragment_pager:
                    startActivity(new Intent(getApplicationContext(),FragmentPagerActivity.class));
                    break;
                case R.id.fragment_statepager:
                    startActivity(new Intent(getApplicationContext(),FragmentStatePagerActivity.class));
                    break;

                case R.id.image:
                    startActivity(new Intent(getApplicationContext(),ImageActivity.class));
                    break;

                case R.id.image2:
                    startActivity(new Intent(getApplicationContext(),Image2Activity.class));
                    break;

                case R.id.image3:
                    startActivity(new Intent(getApplicationContext(),MatrixImageActivity.class));
                    break;

                case R.id.textView:
                    startActivity(new Intent(getApplicationContext(),TextViewActivity.class));
                    break;

                case R.id.textView2:
                    startActivity(new Intent(getApplicationContext(),TextViewLineCountActivity.class));
                    break;
            }

        }
    };

    void printThreadInfo(){
        Log.e("albertThreadDebug","all start==============================================");
        Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> entry : threadMap.entrySet()) {
            Thread thread = entry.getKey();
            StackTraceElement[] stackElements = entry.getValue();
            Log.e("albertThreadDebug","name:"+thread.getName()+" id:"+thread.getId()+" thread:"+thread.getPriority()+" begin==========");
            for (int i = 0; i < stackElements.length; i++) {
                StringBuilder stringBuilder = new StringBuilder("    ");
                stringBuilder.append(stackElements[i].getClassName()+".")
                        .append(stackElements[i].getMethodName()+"(")
                        .append(stackElements[i].getFileName()+":")
                        .append(stackElements[i].getLineNumber()+")");
                Log.e("albertThreadDebug",stringBuilder.toString());
            }
            Log.e("albertThreadDebug","name:"+thread.getName()+" id:"+thread.getId()+" thread:"+thread.getPriority()+" end==========");
        }
        Log.e("albertThreadDebug","all end==============================================");
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
//        {
//            android.os.Process.killProcess(android.os.Process.myPid());
////			System.exit(0);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
