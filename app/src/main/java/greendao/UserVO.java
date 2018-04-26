package greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by chen on 2017/5/17.UserVO Entity
 */
@Entity
public class UserVO {

    @Id(autoincrement = true)
    private Long id; // // internal unique ID for SQLite, do not care about this one 数据库需要

    private String accountId;

    private String login;

    private String roleId;

    private String schoolName;

    private String token;
    @Index(unique = true)
    private String userId; //用户id

    private String userName;

    private String userType;

    private String userNumber;

    private String name;

    private String userFace;


    @Generated(hash = 769280242)
    public UserVO() {
    }

    @Generated(hash = 137004551)
    public UserVO(Long id, String accountId, String login, String roleId, String schoolName,
            String token, String userId, String userName, String userType, String userNumber,
            String name, String userFace) {
        this.id = id;
        this.accountId = accountId;
        this.login = login;
        this.roleId = roleId;
        this.schoolName = schoolName;
        this.token = token;
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.userNumber = userNumber;
        this.name = name;
        this.userFace = userFace;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSchoolName() {
        return this.schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserNumber() {
        return this.userNumber;
    }

    public void setUserNumber(String usernumber) {
        this.userNumber = usernumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserFace() {
        return this.userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

}
