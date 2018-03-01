package com.open.test.image;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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

public class MatrixImageActivity extends Activity {

    private ImageView  matrix_image_1;
    private ImageView matrix_image_2;
    private ImageView matrix_image_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image3);
        initView();
    }

    private void initView(){
        matrix_image_1 = (ImageView) findViewById(R.id.matrix_image_1);
        matrix_image_2 = (ImageView) findViewById(R.id.matrix_image_2);
        matrix_image_3 = (ImageView) findViewById(R.id.matrix_image_3);

        Matrix matrix = new Matrix();
        matrix.setScale(1, 1);
        matrix_image_1.setImageMatrix(matrix);


        matrix = new Matrix();
        matrix.setScale(2, 2);
        matrix_image_2.setImageMatrix(matrix);

        matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        matrix_image_3.setImageMatrix(matrix);

    }




}
