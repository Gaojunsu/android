package com.keylinks.android.http;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * 网络请求管理器
 */
public class RetrofitManager {

    private static RetrofitManager instance;
    private static final String API_URL = "http://0.0.0.0:5000/api/v1.0/";
    private AndroidAPI service;

    public static RetrofitManager getInstance() {
        if (instance == null) {
            instance = new RetrofitManager();
        }
        return instance;
    }

    private RetrofitManager() {
        Gson gson = new Gson();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.HOURS);
        httpClient.readTimeout(1,TimeUnit.HOURS);
        httpClient.writeTimeout(1,TimeUnit.HOURS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        service = retrofit.create(AndroidAPI.class);
    }

    public interface  AndroidAPI{
        @FormUrlEncoded
        @POST("login")
        Call<APIToken> login(@Field("username") String username, @Field("password") String password);



        class APIToken {

            @SerializedName("token")
            public String token;
            @SerializedName("user_email")
            public String user_email;

        }


    }




    // API to the app
    public Call<AndroidAPI.APIToken> login(String username, String password) {
        return service.login(username, password);
    }


}
