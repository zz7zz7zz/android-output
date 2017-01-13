package com.open.iandroidtsing.image;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.iandroidtsing.R;

import java.io.File;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        initView();
    }

    private void initView(){

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
        decodeFile_tv = (TextView) findViewById(R.id.decodeFile_tv);
        decodeFile_img = (ImageView) findViewById(R.id.decodeFile_img);
        decodeFile_img.setImageDrawable(new BitmapDrawable(getResources(),BitmapFactory.decodeFile(path)));

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                texta.setText("a mdpi getMeasuredWidth " + imga.getMeasuredWidth() + " getWidth " + imga.getWidth());
                textb.setText("b hdpi getMeasuredWidth " + imgb.getMeasuredWidth() + " getWidth " + imgb.getWidth());
                textc.setText("c xhdpi getMeasuredWidth " + imgc.getMeasuredWidth() + " getWidth " + imgc.getWidth());
                textd.setText("d xxhdpi getMeasuredWidth " + imgd.getMeasuredWidth() + " getWidth " + imgd.getWidth());
                texte.setText("e xxhdpi getMeasuredWidth " + imge.getMeasuredWidth() + " getWidth " + imge.getWidth());

                decodeResource_tv.setText("decodeResource_tv getMeasuredWidth " + decodeResource_img.getMeasuredWidth() + " getWidth " + decodeResource_img.getWidth());
                decodeFile_tv.setText("decodeFile_tv getMeasuredWidth " + decodeFile_img.getMeasuredWidth() + " getWidth " + decodeFile_img.getWidth());
            }
        },1000);
    }


}
