package com.fif.iclass.common.http;

import android.util.ArrayMap;

import com.fif.iclass.common.bean.AppInfoBean;
import com.fif.iclass.common.bean.LoginBean;
import com.fif.iclass.common.bean.UpdateBean;
import com.fif.iclass.common.bean.UserPortraitBean;
import com.fif.iclass.common.bean.UserTypeBean;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * by chen
 */
public interface ObservableApi {


    @GET(UrlConfig.APP_LOGIN)
    Observable<LoginBean> login(@QueryMap ArrayMap<String, String> loginMap);

    @GET(UrlConfig.GET_APP_VERSION)
    Observable<UpdateBean> checkUpdate(@QueryMap ArrayMap<String, String> map);
}
