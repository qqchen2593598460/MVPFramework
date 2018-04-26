package com.fif.iclass.common.http;


import android.support.annotation.NonNull;
import com.fif.baselib.utils.LogUtils;
import com.fif.iclass.app.FIFApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * by chen
 */
public class Network {


    private static final Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static final CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static final long DEFAULT_TIMEOUT = 30;
    private static ObservableApi observableApi;
    public static String baseUrl = UrlConfig.BASE_API;

    public static ObservableApi getObservableApi() {
        //observableApi = getRetrofit(UrlConfig.BASE_API).create(ObservableApi.class);
        observableApi = getRetrofit(baseUrl).create(ObservableApi.class);
        return observableApi;
    }
    private static Retrofit getRetrofit(String baseUrl) {
         //retrofit底层用的okHttp,所以设置超时还需要okHttp
        //其中DEFAULT_TIMEOUT是我这边定义的一个常量
        //TimeUnit为java.util.concurrent包下的时间单位
        //TimeUnit.SECONDS这里为秒的单位
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new LogInterceptor())
                .build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }

    private static class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
/*            Request request = chain.request()
                    .newBuilder()
                    .header("Authorization", "Basic" + " " + FIFApplication.getInstance().getUserVO().getToken())
                    .build();*/
            Request.Builder request = chain.request()
                    .newBuilder();
            //去掉不需要token的请求，比如登录
            filterRequest(request);
            LogUtils.i("LogUtils--> ", "request:" + request.build().toString());
            okhttp3.Response response = chain.proceed(request.build());
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            LogUtils.i("LogUtils--> ", "response body:" + content);
            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(mediaType, content);
                return response.newBuilder()
                        .body(body)
                        .build();
            } else {
                return response;
            }
        }

        /**
         * 判断请求是否需要添加token
         */
        private void filterRequest(Request.Builder request) {
            //用户登录接口不传递token
            if (!request.build().url().toString().contains("mobile/user/login.do")) {
                request.header("Authorization", "Basic" + " " + FIFApplication.getInstance().getUserVO().getToken());
            }
        }
    }
}
