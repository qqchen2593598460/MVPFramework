package com.fif.iclass.common.http;

/**
 * Created by chen on 2017-08-30. 网络异常
 */

public class NetWorkException  extends Exception {

    private final int code;
    private String displayMessage;

    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = 1001;

    public NetWorkException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    public String getDisplayMessage() {
        return displayMessage;
    }
    public void setDisplayMessage(String msg) {
        this.displayMessage = msg + "(code:" + code + ")";
    }
}
