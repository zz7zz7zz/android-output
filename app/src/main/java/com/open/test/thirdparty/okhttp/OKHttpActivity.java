package com.open.test.thirdparty.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.open.test.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OKHttpActivity extends Activity {

    private String TAG = "OKHttpActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net_okhttp);

        ThreadLocal tl = new ThreadLocal();
        tl.set("String");
//        new Thread()

        findViewById(R.id.request_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doDNS();
//                doGet();
            }
        });

        findViewById(R.id.request_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doPost();

                //retrofit 使用
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://www.jianshu.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService service = retrofit.create(ApiService.class);
                Call<ResponseBody> call = service.getNews();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.v(TAG,"success  "+response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.v(TAG,"error "+call.toString());
                    }
                });

            }
        });
    }



//    private void doDNS(){
//        new Thread(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void run() {
//
////------------------------------------TestDNS-------------------------------------------
//                String hostname = "www.baidu.com";
//                try {
//                    InetAddress[] ret = InetAddress.getAllByName(hostname);
//                    for (int i =0;i<ret.length;i++){
//                        Log.v(TAG,ret[i].toString());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
////-------------------------------------------------------------------------------
//            }
//        }).start();
//    }
//
//
//    private void doGet(){
//        new Thread(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void run() {
//                String url = "https://wwww.baidu.com";
////                String ret = get(url);
////                Log.v(TAG,ret);
//
//                getAsyn(url);
//            }
//        }).start();
//    }
//
//
//
//    OkHttpClient client = new OkHttpClient();
////    OkHttpClient.Builder builder = new OkHttpClient.Builder()
////            .eventListenerFactory(HttpEventListener.FACTORY)
////            .build();
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    String get(String url)  {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try {
//            try (Response response = client.newCall(request).execute()) {
//                return response.body().string();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    void getAsyn(String url)  {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure( Call call,  IOException e) {
//                    Log.v(TAG, "onFailure: " + e.getMessage());
//                }
//
//                @Override
//                public void onResponse( Call call,  Response response) throws IOException {
//                    Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
//                    Headers headers = response.headers();
//                    for (int i = 0; i < headers.size(); i++) {
//                        Log.d(TAG, headers.name(i) + ":" + headers.value(i));
//                    }
//                    Log.d(TAG, "onResponse: " + response.body().string());
//                }
//            });
//
//    }
//
//
//    private void doPost(){
//        new Thread(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void run() {
//                String url = "http://wwww.baidu.com";
//                url = "http://47.115.124.195/ad/getAdConfig";
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("ver","9.9.9");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                String ret = post(url,jsonObject.toString());
//                Log.v(TAG,ret);
//            }
//        }).start();
//    }
//
//
//    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
////    OkHttpClient client = new OkHttpClient();
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    String post(String url, String json) {
//        RequestBody body = RequestBody.create(JSON, json);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//
////        RequestBody.create
//        try {
//            try (Response response = client.newCall(request).execute()) {
//                return response.body().string();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return  "";
//    }
}
