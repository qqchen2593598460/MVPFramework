package com.fif.iclass.common.bean;

/**
 * Created by chen on 2017-09-22.
 */

public class UserTypeBean {

    /**
     * data : {"schoolInfo":{"schoolLogo":"http://resource.xfcampus.com/portal/image/20170825211124_467.png","schoolName":"外研讯飞职教智慧校园演示"},"userInfo":{"classIdOrDepartmentId":"5f9f4c47575a4b7fa13059a5ebc5c29b","classNameOrDepartmentName":"2017-学工处-市场1班","userId":"00a060c50f02437b96042dbc9e78d4f1","userType":"4"}}
     * message : 查询成功!
     * status : success
     */

    private DataBean data;
    private String message;
    private String status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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
         * schoolInfo : {"schoolLogo":"http://resource.xfcampus.com/portal/image/20170825211124_467.png","schoolName":"外研讯飞职教智慧校园演示"}
         * userInfo : {"classIdOrDepartmentId":"5f9f4c47575a4b7fa13059a5ebc5c29b","classNameOrDepartmentName":"2017-学工处-市场1班","userId":"00a060c50f02437b96042dbc9e78d4f1","userType":"4"}
         */

        private SchoolInfoBean schoolInfo;
        private UserInfoBean userInfo;

        public SchoolInfoBean getSchoolInfo() {
            return schoolInfo;
        }

        public void setSchoolInfo(SchoolInfoBean schoolInfo) {
            this.schoolInfo = schoolInfo;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class SchoolInfoBean {
            /**
             * schoolLogo : http://resource.xfcampus.com/portal/image/20170825211124_467.png
             * schoolName : 外研讯飞职教智慧校园演示
             */

            private String schoolLogo;
            private String schoolName;

            public String getSchoolLogo() {
                return schoolLogo;
            }

            public void setSchoolLogo(String schoolLogo) {
                this.schoolLogo = schoolLogo;
            }

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }
        }

        public static class UserInfoBean {
            /**
             * classIdOrDepartmentId : 5f9f4c47575a4b7fa13059a5ebc5c29b
             * classNameOrDepartmentName : 2017-学工处-市场1班
             * userId : 00a060c50f02437b96042dbc9e78d4f1
             * userType : 4
             */

            private String classIdOrDepartmentId;
            private String classNameOrDepartmentName;
            private String userId;
            private String userType;

            public String getClassIdOrDepartmentId() {
                return classIdOrDepartmentId;
            }

            public void setClassIdOrDepartmentId(String classIdOrDepartmentId) {
                this.classIdOrDepartmentId = classIdOrDepartmentId;
            }

            public String getClassNameOrDepartmentName() {
                return classNameOrDepartmentName;
            }

            public void setClassNameOrDepartmentName(String classNameOrDepartmentName) {
                this.classNameOrDepartmentName = classNameOrDepartmentName;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }
        }
    }
}
