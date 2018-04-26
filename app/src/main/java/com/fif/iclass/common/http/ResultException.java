package com.fif.iclass.common.http;

/**
 * Created by chen on 2017-08-30. 服务器异常
 */

public class ResultException extends RuntimeException {

    private int errCode = 0;
    public ResultException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }
}
