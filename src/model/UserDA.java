package model;

import com.nct.framework.util.ConvertUtils;
import ga.log4j.GA;
import grabman.entity.SchoolEnt;
import grabman.entity.UserEnt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import jdbc.DBCacheImpl;
import jdbc.DBInsert;
import jdbc.DBQuery;
import jdbc.DBUpdate;
import jdbc.TransactionFrame;

/**
 * Created by Y Sa on 12/15/16.
 */
public class UserDA {

    private static final Lock createLock_ = new ReentrantLock();
    private static final String configName = "grabman";
    private static final Map<String, UserDA> sInstance = new HashMap<>();

    public static UserDA getInstance() {
        if (sInstance.get(configName) == null) {
            try {
                createLock_.lock();
                sInstance.put(configName, new UserDA());
            } finally {
                createLock_.unlock();
            }
        }
        return sInstance.get(configName);
    }

    public UserDA() {
        
    }

    //<editor-fold defaultstate="collapsed" desc="User">
    public UserEnt insert(UserEnt ent) {
        TransactionFrame tf = new TransactionFrame(configName);
        DBInsert insert = DBCacheImpl.getInstance().insert("insert_user");
        try {
            insert.init(tf);

            if (!insert.isCached()) {
                insert.setTable("user");

                insert.addColumn("identityCard");
                insert.addColumn("userName");
                insert.addColumn("fullName");
                insert.addColumn("email");
                insert.addColumn("address");
                insert.addColumn("phone");
                insert.addColumn("schoolID");
                insert.addColumn("dateCreated");
                insert.addColumn("password");
                insert.addColumn("extProperties");
                insert.addColumn("avatarURL");
                insert.addColumn("type");
                insert.addPlaceHolder(12);
            }

            insert.getParameter().add(ent.identityCard);
            insert.getParameter().add(ent.userName);
            insert.getParameter().add(ent.fullName);
            insert.getParameter().add(ent.email);
            insert.getParameter().add(ent.address);
            insert.getParameter().add(ent.phone);
            insert.getParameter().add(ent.schoolId);
            insert.getParameter().add(System.currentTimeMillis());
            insert.getParameter().add(ent.password);
            insert.getParameter().add(ent.extProperties);
            insert.getParameter().add(ent.avatarUrl);
            insert.getParameter().add(ent.type);
            ent.userID = ConvertUtils.toInt(insert.executeInsert());
            return ent;
        } catch (Exception ex) {
            GA.app.error("Exception", ex);
        } finally {
            tf.releasePooledConnection();
        }
        return null;
    }

    public boolean update(UserEnt ent) {
        TransactionFrame tf = new TransactionFrame(configName);
        DBUpdate update = DBCacheImpl.getInstance().update("update_user");
        try {
            update.init(tf);

            if (!update.isCached()) {
                update.setTable("user");

                update.addColumn("identityCard");
                update.addColumn("fullName");
                update.addColumn("email");
                update.addColumn("address");
                update.addColumn("phone");
                update.addColumn("schoolID");
                update.addColumn("dateModified");
                update.addColumn("extProperties");
                update.addColumn("avatarURL");
                update.addColumn("type");
                update.addClause("userID", "=?");
            }

            update.getParameter().add(ent.identityCard);
            update.getParameter().add(ent.fullName);
            update.getParameter().add(ent.address);
            update.getParameter().add(ent.phone);
            update.getParameter().add(ent.schoolId);
            update.getParameter().add(System.currentTimeMillis());
            update.getParameter().add(ent.extProperties);
            update.getParameter().add(ent.avatarUrl);
            update.getParameter().add(ent.type);
            update.getParameter().add(ent.userID);
            return update.executeUpdate() > 0;

        } catch (Exception ex) {
            GA.app.error("Exception", ex);
        } finally {
            tf.releasePooledConnection();
        }
        return false;
    }

    private UserEnt getByResultSet(ResultSet rs) throws SQLException {
        UserEnt item = new UserEnt();
        item.userID = rs.getLong("userID");
        item.identityCard = rs.getString("identityCard");
        item.userName = rs.getString("userName");
        item.fullName = rs.getString("fullName");
        item.email = rs.getString("email");
        item.address = rs.getString("address");
        item.phone = rs.getString("phone");
        item.schoolId = rs.getInt("schoolID");
        item.dateCreated = rs.getLong("dateCreated");
        item.dateModified = rs.getLong("dateModified");
        item.password = rs.getString("password");
        item.extProperties = rs.getInt("extProperties");
        item.avatarUrl = rs.getString("avatarUrl");
        item.type = rs.getShort("type");
        return item;
    }

    public List<UserEnt> getListUser(int index, int offset) {
        List<UserEnt> result = new ArrayList();
        TransactionFrame tf = new TransactionFrame(configName);
        DBQuery query = DBCacheImpl.getInstance().query("get_list_user");
        try {
            query.init(tf);

            if (!query.isCached()) {
                query.addTable("user");
                query.addColumn("userID");
                query.addColumn("identityCard");
                query.addColumn("userName");
                query.addColumn("fullName");
                query.addColumn("email");
                query.addColumn("address");
                query.addColumn("phone");
                query.addColumn("schoolID");
                query.addColumn("dateCreated");
                query.addColumn("dateModified");
                query.addColumn("password");
                query.addColumn("extProperties");
                query.addColumn("avatarUrl");
                query.addColumn("type");
                query.setQueryOrder("userID", "desc");
                query.setLimit("?,?");
            }
            query.getParameter().add(index * offset);
            query.getParameter().add(offset);

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    try {
                        result.add(getByResultSet(rs));
                    } catch (SQLException ex) {
                    }
                }
            }
        } catch (Exception ex) {
            GA.jdbc.error("Exception", ex);
        } finally {
            tf.releasePooledConnection();
        }
        return result;
    }
    
    public UserEnt getDetail(long userID) {
        UserEnt result = new UserEnt();
        TransactionFrame tf = new TransactionFrame(configName);
        DBQuery query = DBCacheImpl.getInstance().query("get_detail_user");
        try {
            query.init(tf);

            if (!query.isCached()) {
                query.addTable("user");
                query.addColumn("userID");
                query.addColumn("identityCard");
                query.addColumn("userName");
                query.addColumn("fullName");
                query.addColumn("email");
                query.addColumn("address");
                query.addColumn("phone");
                query.addColumn("schoolID");
                query.addColumn("dateCreated");
                query.addColumn("dateModified");
                query.addColumn("password");
                query.addColumn("extProperties");
                query.addColumn("avatarUrl");
                query.addColumn("type");
                query.addClause("userID = ?");
                query.setQueryOrder("userID", "desc");
            }
            query.getParameter().add(userID);

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    try {
                        result = getByResultSet(rs);
                        return result;
                    } catch (SQLException ex) {
                    }
                }
            }
        } catch (Exception ex) {
            GA.jdbc.error("Exception", ex);
        } finally {
            tf.releasePooledConnection();
        }
        return result;
    }
    
    public UserEnt checkLogin(String email, String pass) {
        UserEnt result = new UserEnt();
        TransactionFrame tf = new TransactionFrame(configName);
        DBQuery query = DBCacheImpl.getInstance().query("get_login_user");
        try {
            query.init(tf);

            if (!query.isCached()) {
                query.addTable("user");
                query.addColumn("userID");
                query.addColumn("identityCard");
                query.addColumn("userName");
                query.addColumn("fullName");
                query.addColumn("email");
                query.addColumn("address");
                query.addColumn("phone");
                query.addColumn("schoolID");
                query.addColumn("dateCreated");
                query.addColumn("dateModified");
                query.addColumn("password");
                query.addColumn("extProperties");
                query.addColumn("avatarUrl");
                query.addColumn("type");
                query.addClause("email = ?");
                query.addClause("password = ?");
                query.setQueryOrder("userID", "desc");
            }
            query.getParameter().add(email);
            query.getParameter().add(pass);
            

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    try {
                        result = getByResultSet(rs);
                        return result;
                    } catch (SQLException ex) {
                    }
                }
            }
        } catch (Exception ex) {
            GA.jdbc.error("Exception", ex);
        } finally {
            tf.releasePooledConnection();
        }
        return result;
    }
    
    
    
//</editor-fold>
}
