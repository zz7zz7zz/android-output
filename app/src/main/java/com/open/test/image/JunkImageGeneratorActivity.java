package com.open.test.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.open.test.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by Administrator on 2012/1/31.
 */

public class JunkImageGeneratorActivity extends Activity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image4);
        initView();
    }
    private void initView(){
        findViewById(R.id.junk_image_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNum = ((EditText)findViewById(R.id.junk_image_et)).getText().toString();
                int num = 0;
                try{
                    num = Integer.valueOf(sNum);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(num>0 && num<=1000){
                    loop2generateJunkImage(num);
                }else{
                    Toast.makeText(JunkImageGeneratorActivity.this,"图片张数需>0 && <=1000",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loop2generateJunkImage(int num){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i<num;i++){
                    generateJunkImage(i);
                }
            }
        }).start();
    }

    private void generateJunkImage(int index){
        int wh = new Random().nextInt(32)+1;
        int color = Color.argb(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255));
        Bitmap bitmap = Bitmap.createBitmap(wh, wh, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(color);

        try {
            String fileName = createRootPath(this) + "/junkimage/" + index + ".jpg";
//            File file = new File(fileName);
//            if (!file.exists()) {
//                file.getParentFile().mkdirs();
//                file.createNewFile();
//            }
//            FileOutputStream fos = new FileOutputStream(file);

            File file = new File(fileName);
            file.delete();
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.junk_image_tv)).setText(String.format("已生成%d张图片",index+1));
            }
        },100);
    }

    public static String createRootPath(Context context) {
        String cacheRootPath = "";
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/cache
            cacheRootPath = context.getExternalCacheDir().getPath();
        } else {
            // /data/data/<application package>/cache
            cacheRootPath = context.getCacheDir().getPath();
        }
        return cacheRootPath;
    }

    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

}
