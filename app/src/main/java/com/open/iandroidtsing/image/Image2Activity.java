package com.open.iandroidtsing.image;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.iandroidtsing.R;

/**
 * Created by Administrator on 2016/7/18.
 */

public class Image2Activity extends Activity {

    private final String TAG = "TextViewActivity";

    private final int [] TEST_IMAGE_ARRAY = {
            R.mipmap.img_1,
            R.mipmap.img_2,
            R.mipmap.img_3,
            R.mipmap.img_4,
            R.mipmap.img_5,
    };
    private int currint_index = -1;

    private Button  readImage;
    private TextView setImage_time;
    private ImageView showImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image2);
        initView();
    }

    private void initView(){
        readImage = (Button) findViewById(R.id.readImage);
        setImage_time = (TextView)findViewById(R.id.setImage_time);
        showImage = (ImageView) findViewById(R.id.showImage);

        readImage.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){

                case R.id.readImage:
                    if(currint_index >= TEST_IMAGE_ARRAY.length-1 || currint_index < 0){
                        currint_index = 0;
                    }else{
                        currint_index ++;
                    }

                    long start = System.currentTimeMillis();
                    Drawable drawable = getResources().getDrawable(TEST_IMAGE_ARRAY[currint_index]);
                    long end = System.currentTimeMillis();
                    Log.v(TAG,"resId "+TEST_IMAGE_ARRAY[currint_index]+" cost = " + (end - start));
                    showImage.setImageDrawable(drawable);

                    setImage_time.setText("resId "+TEST_IMAGE_ARRAY[currint_index]+" cost = " + (end - start));
                    break;
            }
        }
    };

}
