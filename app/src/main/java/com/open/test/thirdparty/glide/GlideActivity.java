package com.open.test.thirdparty.glide;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.open.test.R;

public class GlideActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glide);

//        loadImage((ImageView) findViewById(R.id.image));

        loadImage2((ImageView) findViewById(R.id.image2));
    }

    public void loadImage(ImageView imageView) {
        String url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn15%2F728%2Fw640h888%2F20180912%2F5a75-hiycyfx8205448.jpg&refer=http%3A%2F%2Fn.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1615277398&t=c1ef097c0e47adbb0be0671a3e22dc1c";
        Glide.with(this).load(url).into(imageView);
    }

    public void loadImage2(ImageView imageView) {

        String url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn15%2F728%2Fw640h888%2F20180912%2F5a75-hiycyfx8205448.jpg&refer=http%3A%2F%2Fn.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1615277398&t=c1ef097c0e47adbb0be0671a3e22dc1c";

        RequestOptions requestOptions = new RequestOptions()
                .error(R.mipmap.error)  //加载错误时显示该图
                .placeholder(R.mipmap.placeholder) //加载时显示该图
                .override(300,300) //指定大小  Target.SIZE_ORIGINAL代表原始大小
                .circleCrop() //圆形图片
//                .skipMemoryCache(true)//禁用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)//缓存方式
                ;//（模糊和变灰 需要glide-transformations库）

        Glide.with(this)
                .load(url)
                .apply(requestOptions)
                .into(imageView);


    }
}
