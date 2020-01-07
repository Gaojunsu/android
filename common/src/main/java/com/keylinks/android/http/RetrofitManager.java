package com.keylinks.android.http;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.keylinks.android.rxjava.bean.User;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 网络请求管理器
 */
public class RetrofitManager {
    public OkHttpClient.Builder httpClient;
    private static RetrofitManager instance;
    private static final String API_URL = "http://192.168.8.184:5000/api/v1.0/";
    private AndroidAPI service;

    private String csrf_token;

    public static RetrofitManager getInstance() {
        if (instance == null) {
            instance = new RetrofitManager();
        }
        return instance;
    }




    private RetrofitManager() {
        Gson gson = new Gson();
        httpClient = new OkHttpClient.Builder();
        this.csrf_token="XHSOI*Y9dfs9cshd9";
        httpClient.connectTimeout(1, TimeUnit.SECONDS);
        httpClient.readTimeout(1, TimeUnit.SECONDS);
        httpClient.writeTimeout(1, TimeUnit.HOURS);
        //增加cookie
        httpClient.cookieJar(new CookieJar() {


            //Cookie缓存区
            private final Map<String, List<Cookie>> cookiesMap = new HashMap<String, List<Cookie>>();
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                String host = url.host();
                List<Cookie> cookiesList = cookiesMap.get(host);
                if (cookiesList != null){
                    cookiesMap.remove(host);
                }

            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookiesList = cookiesMap.get(url.host());


                if (cookiesList != null){
                    return cookiesList;
                }

                ArrayList<Cookie> cookies = new ArrayList<>();

                Cookie cookie = new Cookie.Builder()
                        .hostOnlyDomain(url.host())
                        .name("csrf_token").value(csrf_token)
                        .build();
                cookies.add(cookie);

                //注：这里不能返回null，否则会报NULLException的错误。
                //原因：当Request 连接到网络的时候，OkHttp会调用loadForRequest()
                return cookies;
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        service = retrofit.create(AndroidAPI.class);



    }

    public interface AndroidAPI {
        @FormUrlEncoded
        @Headers("X-CSRFToken:XHSOI*Y9dfs9cshd9")
        @POST("login")
        Call<APIToken> login(@Field("username") String username, @Field("password") String password);

        @GET("index")
        Call<ResponseBody> index();

        @POST("users")
        Call<User> addUser(@Body User user);

        @GET("user/{user}/repos")
        Call<ResponseBody> getRepos();

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

    public Call<ResponseBody> index() {
        return service.index();
    }

    public Call<User> addUser(User user) {
        return service.addUser(user);
    }




    public String getCsrf_token() {
        return csrf_token;
    }

    public void setCsrf_token(String csrf_token) {
        this.csrf_token = csrf_token;
    }
}
