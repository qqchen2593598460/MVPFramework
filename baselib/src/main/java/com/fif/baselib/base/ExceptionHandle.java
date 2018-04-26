package com.fif.baselib.base;

import android.widget.Toast;

import com.fif.baselib.config.PageStateConfig;
import com.fif.baselib.widget.toasty.Toasty;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Created by chen on 2017-09-20. 异常处理类
 */

public class ExceptionHandle {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static void handleCancel(Object obj) {
        if (obj instanceof BaseActivity) { ((BaseActivity)obj).sendHandlerMeg(PageStateConfig.SUCCESS);}
        if (obj instanceof BaseFragment) {((BaseFragment)obj).sendHandlerMeg(PageStateConfig.SUCCESS);}
    }
    public static void handleException(Object obj, Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    //ex.code = httpException.code();
                    ex.message = "网络错误";
                    break;
            }
            //return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseThrowable(resultException, resultException.code);
            ex.message = resultException.message;
            //return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                /*|| e instanceof ParseException*/) {
            ex = new ResponseThrowable(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            //return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseThrowable(e, ERROR.NETWORD_ERROR);
            ex.message = "连接失败";
            //return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            //return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ResponseThrowable(e, ERROR.NO_NET);
            ex.message = "无网络";
        }else {
            ex = new ResponseThrowable(e, ERROR.UNKNOWN);
            ex.message = "未知错误";
            //return ex;
        }
        handleError(obj, ex);
    }

    /**
     * 处理异常细节
     */
     private static void handleError(Object obj, ResponseThrowable ex){
         if (ex != null) {
             if (obj instanceof BaseActivity) {
                 int statusCode = ex.code;
                 switch (statusCode){
                     case ExceptionHandle.ERROR.SSL_ERROR:
                         break;
                     case ExceptionHandle.ERROR.UNKNOWN:
                         break;
                     case ExceptionHandle.ERROR.PARSE_ERROR:
                         break;
                     case ExceptionHandle.ERROR.NETWORD_ERROR:
                         break;
                     case ExceptionHandle.ERROR.HTTP_ERROR:
                         ((BaseActivity)obj).sendHandlerMeg(PageStateConfig.NO_NET);
                         Toasty.error((BaseActivity)obj, "当前无网络，请检查网络连接", Toast.LENGTH_SHORT).show();
                         break;
                     case ExceptionHandle.ERROR.NO_NET:
                         ((BaseActivity)obj).sendHandlerMeg(PageStateConfig.NO_NET);
                         Toasty.error((BaseActivity)obj, "当前无网络，请检查网络连接", Toast.LENGTH_SHORT).show();
                         break;
                 }
             }
             if (obj instanceof BaseFragment) {
                 int statusCode = ex.code;
                 switch (statusCode){
                     case ExceptionHandle.ERROR.SSL_ERROR:
                         break;
                     case ExceptionHandle.ERROR.UNKNOWN:
                         break;
                     case ExceptionHandle.ERROR.PARSE_ERROR:
                         break;
                     case ExceptionHandle.ERROR.NETWORD_ERROR:
                         break;
                     case ExceptionHandle.ERROR.HTTP_ERROR:
                         ((BaseFragment)obj).sendHandlerMeg(PageStateConfig.NO_NET);
                         Toasty.error(((BaseFragment)obj).getContext(), "当前无网络，请检查网络连接", Toast.LENGTH_SHORT).show();
                         break;
                     case ERROR.NO_NET:
                         ((BaseFragment)obj).sendHandlerMeg(PageStateConfig.NO_NET);
                         Toasty.error(((BaseFragment)obj).getContext(), "当前无网络，请检查网络连接", Toast.LENGTH_SHORT).show();
                         break;
                 }
             }
         }

     }
    /**
     * 约定异常
     */
    class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;
        /**
         * 无网络
         */
        public static final int NO_NET = 1006;
    }

    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;

        public ResponseThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }
    }

    /**
     * ServerException发生后，将自动转换为ResponeThrowable返回
     */
    class ServerException extends RuntimeException {
        int code;
        String message;
    }
}
