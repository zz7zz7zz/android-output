package com.open.test.thirdparty.okhttp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/p/308f3c54abdd")
    Call<ResponseBody> getNews();//登陆


}
