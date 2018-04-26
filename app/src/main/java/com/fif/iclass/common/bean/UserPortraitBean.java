package com.fif.iclass.common.bean;

/**
 * Created by 用户头像 on 2017/11/20.
 */

public class UserPortraitBean {

    /**
     * data : {"url":"http://resource.xfcampus.com/ab/cd/20170725121647_715.png"}
     * location :
     * message : 上传成功
     * status : success
     */

    private DataBean data;
    private String location;
    private String message;
    private String status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * url : http://resource.xfcampus.com/ab/cd/20170725121647_715.png
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
