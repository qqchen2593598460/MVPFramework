package com.fif.iclass.common.bean;

import java.io.Serializable;

/**
 * Created by chen on 17/5/25. 下载更新模块
 */
public class UpdateBean implements Serializable {

    /**
     * data : {"latest_version":{"versionLog":"最新版本","id":"1","versionNumber":"1.0.1","versionUrl":"http://www.baidu.com"},"coerce_version":{"versionLog":"强制更新版本","id":"2","versionNumber":"1.0.2","versionUrl":"http://www.baidu.com"}}
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

    public static class DataBean implements Serializable{
        /**
         * latest_version : {"versionLog":"最新版本","id":"1","versionNumber":"1.0.1","versionUrl":"http://www.baidu.com"}
         * coerce_version : {"versionLog":"强制更新版本","id":"2","versionNumber":"1.0.2","versionUrl":"http://www.baidu.com"}
         */

        private LatestVersionBean latest_version;
        private CoerceVersionBean coerce_version;

        public LatestVersionBean getLatest_version() {
            return latest_version;
        }

        public void setLatest_version(LatestVersionBean latest_version) {
            this.latest_version = latest_version;
        }

        public CoerceVersionBean getCoerce_version() {
            return coerce_version;
        }

        public void setCoerce_version(CoerceVersionBean coerce_version) {
            this.coerce_version = coerce_version;
        }

        public static class LatestVersionBean implements Serializable {
            /**
             * versionLog : 最新版本
             * id : 1
             * versionNumber : 1.0.1
             * versionUrl : http://www.baidu.com
             */

            private String versionLog;
            private String id;
            private String versionNumber;
            private String versionUrl;
            private int versionCode;

            public int getVersionCode() {
                return versionCode;
            }

            public void setVersionCode(int versionCode) {
                this.versionCode = versionCode;
            }

            public String getVersionLog() {
                return versionLog;
            }

            public void setVersionLog(String versionLog) {
                this.versionLog = versionLog;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVersionNumber() {
                return versionNumber;
            }

            public void setVersionNumber(String versionNumber) {
                this.versionNumber = versionNumber;
            }

            public String getVersionUrl() {
                return versionUrl;
            }

            public void setVersionUrl(String versionUrl) {
                this.versionUrl = versionUrl;
            }
        }

        public static class CoerceVersionBean implements Serializable{
            /**
             * versionLog : 强制更新版本
             * id : 2
             * versionNumber : 1.0.2
             * versionUrl : http://www.baidu.com
             */

            private String versionLog;
            private String id;
            private String versionNumber;
            private String versionUrl;
            private int versionCode;

            public int getVersionCode() {
                return versionCode;
            }

            public void setVersionCode(int versionCode) {
                this.versionCode = versionCode;
            }

            public String getVersionLog() {
                return versionLog;
            }

            public void setVersionLog(String versionLog) {
                this.versionLog = versionLog;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVersionNumber() {
                return versionNumber;
            }

            public void setVersionNumber(String versionNumber) {
                this.versionNumber = versionNumber;
            }

            public String getVersionUrl() {
                return versionUrl;
            }

            public void setVersionUrl(String versionUrl) {
                this.versionUrl = versionUrl;
            }
        }
    }
}
