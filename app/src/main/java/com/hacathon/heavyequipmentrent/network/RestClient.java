package com.hacathon.heavyequipmentrent.network;

import android.content.SharedPreferences;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hacathon.heavyequipmentrent.BuildConfig;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.UserBean;
import com.hacathon.heavyequipmentrent.utilis.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmObject;
import okhttp3.CertificatePinner;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by adib on 14/03/2019.
 */

public class RestClient {
    //	private static final String BASE_URL = "your base url";
    private static boolean user_is_connected=false;
    private WebServicesAPI apiService;
    OkHttpClient httpClient = null;
    CertificatePinner certificatePinner = null;
    OkHttpClient.Builder httpClientBuilder  = null;

    public RestClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder();



        httpClientBuilder.cookieJar(CookieJar.NO_COOKIES);

        httpClientBuilder.connectTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS);


        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws java.io.IOException {


                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                RequestBody requestBody = original.body();

                if (requestBody != null) {
                    requestBody = processApplicationJsonRequestBody(requestBody);
                    requestBuilder.post(requestBody);
                }

                UserBean userBean = Realm.getDefaultInstance().where(UserBean.class).findFirst();
                if (userBean != null && userBean.isValid()){
                    final String basic = "Bearer " + userBean.getToken();
                    requestBuilder.addHeader("Authorization", basic);
                    requestBuilder.addHeader("Accept", "application/json");
                }


                Request request = requestBuilder.build();
                Response response = chain.proceed(request);


                return response;
            }
        });


        httpClientBuilder.addInterceptor(logging);

        httpClient =  httpClientBuilder.build();


        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BuildConfig.Base_Domain)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();

        apiService = restAdapter.create(WebServicesAPI.class);
    }



    private RequestBody processApplicationJsonRequestBody(RequestBody requestBody){
        String customReq = bodyToString(requestBody);
        if (customReq != null) {
            customReq = Utilities.replaceArabicNumbers(customReq);
            return RequestBody.create(requestBody.contentType(), customReq);
        }
        else {
            return requestBody;
        }


    }
    private String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return null;
        }
    }


    public WebServicesAPI getApiService() {
        return apiService;
    }


    /**
     * @return the user_is_connected
     */
    public static boolean isUser_is_connected() {
        return user_is_connected;
    }

    /**
     * @param user_is_connected the user_is_connected to set
     */
    public static void setUser_is_connected(boolean user_is_connected) {
        RestClient.user_is_connected = user_is_connected;
    }

}

