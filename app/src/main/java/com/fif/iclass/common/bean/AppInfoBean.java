package com.fif.iclass.common.bean;

import java.util.List;

/**
 * Created by chen on 2017-06-28.
 */

public class AppInfoBean {

    /**
     * data : {"appList":[{"appCode":"105","appName":"公告","appPicture":"http://resource.xfcampus.com/ydqx/image/appLogo_1510624797166.png","appType":"2","appUrl":"","display":"1","id":"5b23b7a78ae5483bb9cbbee948e2bca1"},{"appCode":"101","appName":"教师考勤","appPicture":"http://resource.xfcampus.com/ydqx/image/appLogo_1510567285985.png","appType":"2","appUrl":"","display":"1","id":"4b5ad57079b34cb7a6011a2fcd025db7"},{"appCode":"100","appName":"学生考勤","appPicture":"http://resource.xfcampus.com/ydqx/image/appLogo_1510567301856.png","appType":"2","appUrl":"","display":"1","id":"bafbdeef4e704d2080b270ce1562b465"}]}
     * location :
     * message :
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
        private List<AppListBean> appList;

        public List<AppListBean> getAppList() {
            return appList;
        }

        public void setAppList(List<AppListBean> appList) {
            this.appList = appList;
        }

        public static class AppListBean {
            /**
             * appCode : 105
             * appName : 公告
             * appPicture : http://resource.xfcampus.com/ydqx/image/appLogo_1510624797166.png
             * appType : 2
             * appUrl :
             * display : 1
             * id : 5b23b7a78ae5483bb9cbbee948e2bca1
             */

            private String appCode;
            private String appName;
            private String appPicture;
            private String appType;
            private String appUrl;
            private String display;
            private String id;

            public AppListBean(String appCode, String appName, String appPicture, String appType, String appUrl, String display, String id) {
                this.appCode = appCode;
                this.appName = appName;
                this.appPicture = appPicture;
                this.appType = appType;
                this.appUrl = appUrl;
                this.display = display;
                this.id = id;
            }

            public String getAppCode() {
                return appCode;
            }

            public void setAppCode(String appCode) {
                this.appCode = appCode;
            }

            public String getAppName() {
                return appName;
            }

            public void setAppName(String appName) {
                this.appName = appName;
            }

            public String getAppPicture() {
                return appPicture;
            }

            public void setAppPicture(String appPicture) {
                this.appPicture = appPicture;
            }

            public String getAppType() {
                return appType;
            }

            public void setAppType(String appType) {
                this.appType = appType;
            }

            public String getAppUrl() {
                return appUrl;
            }

            public void setAppUrl(String appUrl) {
                this.appUrl = appUrl;
            }

            public String getDisplay() {
                return display;
            }

            public void setDisplay(String display) {
                this.display = display;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
