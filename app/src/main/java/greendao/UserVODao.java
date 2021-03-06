package greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_VO".
*/
public class UserVODao extends AbstractDao<UserVO, Long> {

    public static final String TABLENAME = "USER_VO";

    /**
     * Properties of entity UserVO.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property AccountId = new Property(1, String.class, "accountId", false, "ACCOUNT_ID");
        public final static Property Login = new Property(2, String.class, "login", false, "LOGIN");
        public final static Property RoleId = new Property(3, String.class, "roleId", false, "ROLE_ID");
        public final static Property SchoolName = new Property(4, String.class, "schoolName", false, "SCHOOL_NAME");
        public final static Property Token = new Property(5, String.class, "token", false, "TOKEN");
        public final static Property UserId = new Property(6, String.class, "userId", false, "USER_ID");
        public final static Property UserName = new Property(7, String.class, "userName", false, "USER_NAME");
        public final static Property UserType = new Property(8, String.class, "userType", false, "USER_TYPE");
        public final static Property UserNumber = new Property(9, String.class, "userNumber", false, "USER_NUMBER");
        public final static Property Name = new Property(10, String.class, "name", false, "NAME");
        public final static Property UserFace = new Property(11, String.class, "userFace", false, "USER_FACE");
    }


    public UserVODao(DaoConfig config) {
        super(config);
    }
    
    public UserVODao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_VO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ACCOUNT_ID\" TEXT," + // 1: accountId
                "\"LOGIN\" TEXT," + // 2: login
                "\"ROLE_ID\" TEXT," + // 3: roleId
                "\"SCHOOL_NAME\" TEXT," + // 4: schoolName
                "\"TOKEN\" TEXT," + // 5: token
                "\"USER_ID\" TEXT," + // 6: userId
                "\"USER_NAME\" TEXT," + // 7: userName
                "\"USER_TYPE\" TEXT," + // 8: userType
                "\"USER_NUMBER\" TEXT," + // 9: userNumber
                "\"NAME\" TEXT," + // 10: name
                "\"USER_FACE\" TEXT);"); // 11: userFace
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_USER_VO_USER_ID ON \"USER_VO\"" +
                " (\"USER_ID\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_VO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserVO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String accountId = entity.getAccountId();
        if (accountId != null) {
            stmt.bindString(2, accountId);
        }
 
        String login = entity.getLogin();
        if (login != null) {
            stmt.bindString(3, login);
        }
 
        String roleId = entity.getRoleId();
        if (roleId != null) {
            stmt.bindString(4, roleId);
        }
 
        String schoolName = entity.getSchoolName();
        if (schoolName != null) {
            stmt.bindString(5, schoolName);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(6, token);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(7, userId);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(8, userName);
        }
 
        String userType = entity.getUserType();
        if (userType != null) {
            stmt.bindString(9, userType);
        }
 
        String userNumber = entity.getUserNumber();
        if (userNumber != null) {
            stmt.bindString(10, userNumber);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(11, name);
        }
 
        String userFace = entity.getUserFace();
        if (userFace != null) {
            stmt.bindString(12, userFace);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserVO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String accountId = entity.getAccountId();
        if (accountId != null) {
            stmt.bindString(2, accountId);
        }
 
        String login = entity.getLogin();
        if (login != null) {
            stmt.bindString(3, login);
        }
 
        String roleId = entity.getRoleId();
        if (roleId != null) {
            stmt.bindString(4, roleId);
        }
 
        String schoolName = entity.getSchoolName();
        if (schoolName != null) {
            stmt.bindString(5, schoolName);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(6, token);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(7, userId);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(8, userName);
        }
 
        String userType = entity.getUserType();
        if (userType != null) {
            stmt.bindString(9, userType);
        }
 
        String userNumber = entity.getUserNumber();
        if (userNumber != null) {
            stmt.bindString(10, userNumber);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(11, name);
        }
 
        String userFace = entity.getUserFace();
        if (userFace != null) {
            stmt.bindString(12, userFace);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserVO readEntity(Cursor cursor, int offset) {
        UserVO entity = new UserVO( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // accountId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // login
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // roleId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // schoolName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // token
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // userId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // userName
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // userType
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // userNumber
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // name
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11) // userFace
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserVO entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAccountId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLogin(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRoleId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSchoolName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setToken(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUserId(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUserName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUserType(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setUserNumber(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setUserFace(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserVO entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserVO entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserVO entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
