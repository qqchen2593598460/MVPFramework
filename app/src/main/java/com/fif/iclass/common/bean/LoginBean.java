package com.fif.iclass.common.bean;

/**
 * Created by chen on 2017-09-06.
 */

public class LoginBean {

    /**
     * data : {"name":"沈冬灵","userType":"4","login":"true","userName":"x800262","schoolName":"智慧校园","userId":"21b7d6e8a91240e983086c564b589970","userNumber":"1605060800262","token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTY1MjA4NzEsImlzcyI6IjIxYjdkNmU4YTkxMjQwZTk4MzA4NmM1NjRiNTg5OTcwIn0._3nvdJyqsV3JD22gTnqrAK7g-5MBIVytU9EBla1zZic"}
     * location :
     * message : 登录成功
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
         * name : 沈冬灵
         * userType : 4
         * login : true
         * userName : x800262
         * schoolName : 智慧校园
         * userId : 21b7d6e8a91240e983086c564b589970
         * userNumber : 1605060800262
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTY1MjA4NzEsImlzcyI6IjIxYjdkNmU4YTkxMjQwZTk4MzA4NmM1NjRiNTg5OTcwIn0._3nvdJyqsV3JD22gTnqrAK7g-5MBIVytU9EBla1zZic
         */

        private String name;
        private String userType;
        private String login;
        private String userName;
        private String schoolName;
        private String userId;
        private String userNumber;
        private String token;
        private String userFace;


        public String getUserFace() {
            return userFace;
        }

        public void setUserFace(String userFace) {
            this.userFace = userFace;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserNumber() {
            return userNumber;
        }

        public void setUserNumber(String userNumber) {
            this.userNumber = userNumber;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
