package com.open.test.image;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.test.R;
import com.open.test.com.open.frame.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/7/18.
 */

public class ImageActivity extends Activity {

    private Handler mHandler = new Handler();

    private TextView texta;
    private TextView textb;
    private TextView textc;
    private TextView textd;
    private TextView texte;

    private ImageView imga;
    private ImageView imgb;
    private ImageView imgc;
    private ImageView imgd;
    private ImageView imge;

    private TextView  decodeResource_tv;
    private ImageView decodeResource_img;

    private TextView  decodeFile_tv;
    private ImageView decodeFile_img;

    private TextView  decodeFile_tv2;
    private ImageView decodeFile_img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        initView();
    }

    private void initView(){

        int densitya = getTargetDensityByResource(getResources(),R.mipmap.a);
        int densityb = getTargetDensityByResource(getResources(),R.mipmap.b);
        int densityc = getTargetDensityByResource(getResources(),R.mipmap.c);
        int densityd = getTargetDensityByResource(getResources(),R.mipmap.d);
        int densitye = getTargetDensityByResource(getResources(),R.mipmap.e);

        Log.v("ImageActivity" , "densitya: " + densitya);
        Log.v("ImageActivity" , "densityb: " + densityb);
        Log.v("ImageActivity" , "densityc: " + densityc);
        Log.v("ImageActivity" , "densityd: " + densityd);
        Log.v("ImageActivity" , "densitye: " + densitye);
        Log.v("ImageActivity" , "getDisplayMetrics density: " + getResources().getDisplayMetrics().densityDpi);

        Drawable a = ContextCompat.getDrawable(this, R.mipmap.a);
        Drawable b = ContextCompat.getDrawable(this, R.mipmap.b);
        Drawable c = ContextCompat.getDrawable(this, R.mipmap.c);
        Drawable d = ContextCompat.getDrawable(this, R.mipmap.d);
        Drawable e = ContextCompat.getDrawable(this, R.mipmap.e);

        Log.v("ImageActivity" , "drawable a : " + a.getIntrinsicWidth());
        Log.v("ImageActivity" , "drawable b : " + b.getIntrinsicWidth());
        Log.v("ImageActivity" , "drawable c : " + c.getIntrinsicWidth());
        Log.v("ImageActivity" , "drawable d : " + d.getIntrinsicWidth());
        Log.v("ImageActivity" , "drawable e : " + e.getIntrinsicWidth());

        texta = (TextView)findViewById(R.id.texta);
        textb = (TextView)findViewById(R.id.textb);
        textc = (TextView)findViewById(R.id.textc);
        textd = (TextView)findViewById(R.id.textd);
        texte = (TextView)findViewById(R.id.texte);

        imga = (ImageView) findViewById(R.id.imga);
        imgb = (ImageView) findViewById(R.id.imgb);
        imgc = (ImageView) findViewById(R.id.imgc);
        imgd = (ImageView) findViewById(R.id.imgd);
        imge = (ImageView) findViewById(R.id.imge);

        decodeResource_tv = (TextView) findViewById(R.id.decodeResource_tv);
        decodeResource_img = (ImageView) findViewById(R.id.decodeResource_img);
        decodeResource_img.setImageDrawable(new BitmapDrawable(getResources(),BitmapFactory.decodeResource(getResources(),R.mipmap.d)));

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "d.png";
        FileUtil.deleteFile(path);
        FileUtil.writeFile(path,getResources().openRawResource(R.mipmap.d));

        decodeFile_tv = (TextView) findViewById(R.id.decodeFile_tv);
        decodeFile_img = (ImageView) findViewById(R.id.decodeFile_img);
        decodeFile_img.setImageDrawable(new BitmapDrawable(getResources(),BitmapFactory.decodeFile(path)));

        path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "d.png";
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDensity = densityd;
        opts.inTargetDensity = getResources().getDisplayMetrics().densityDpi;
        decodeFile_tv2 = (TextView) findViewById(R.id.decodeFile_tv2);
        decodeFile_img2 = (ImageView) findViewById(R.id.decodeFile_img2);
        decodeFile_img2.setImageDrawable(new BitmapDrawable(getResources(),BitmapFactory.decodeFile(path,opts)));

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                texta.setText("a mdpi getMeasuredWidth " + imga.getMeasuredWidth() + " getWidth " + imga.getWidth());
                textb.setText("b hdpi getMeasuredWidth " + imgb.getMeasuredWidth() + " getWidth " + imgb.getWidth());
                textc.setText("c xhdpi getMeasuredWidth " + imgc.getMeasuredWidth() + " getWidth " + imgc.getWidth());
                textd.setText("d xxhdpi getMeasuredWidth " + imgd.getMeasuredWidth() + " getWidth " + imgd.getWidth());
                texte.setText("e xxxhdpi getMeasuredWidth " + imge.getMeasuredWidth() + " getWidth " + imge.getWidth());

                decodeResource_tv.setText("decodeResource_tv getMeasuredWidth " + decodeResource_img.getMeasuredWidth() + " getWidth " + decodeResource_img.getWidth());
                decodeFile_tv.setText("decodeFile_tv getMeasuredWidth " + decodeFile_img.getMeasuredWidth() + " getWidth " + decodeFile_img.getWidth());
                decodeFile_tv2.setText("decodeFile_tv2 getMeasuredWidth " + decodeFile_img2.getMeasuredWidth() + " getWidth " + decodeFile_img2.getWidth());
            }
        },1000);
    }


    private int getTargetDensityByResource(Resources resources, int id) {
        InputStream is = null;
        try {
            final TypedValue value = new TypedValue();
            is = resources.openRawResource(id, value);
            return value.density;
        } catch (Exception e) {
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                // Ignore
            }
        }
        return 0;
    }

}
