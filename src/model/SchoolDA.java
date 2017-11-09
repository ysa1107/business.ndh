package model;

import com.nct.framework.util.ConvertUtils;
import ga.log4j.GA;
import grabman.entity.SchoolEnt;
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
public class SchoolDA {

    private static final Lock createLock_ = new ReentrantLock();
    private static final String configName = "grabman";
    private static final Map<String, SchoolDA> sInstance = new HashMap<>();

    public static SchoolDA getInstance() {
        if (sInstance.get(configName) == null) {
            try {
                createLock_.lock();
                sInstance.put(configName, new SchoolDA());
            } finally {
                createLock_.unlock();
            }
        }
        return sInstance.get(configName);
    }

    public SchoolDA() {
        
    }

    //<editor-fold defaultstate="collapsed" desc="School">
    public boolean insert(SchoolEnt ent) {
        TransactionFrame tf = new TransactionFrame(configName);
        DBInsert insert = DBCacheImpl.getInstance().insert("insert_school");
        try {
            insert.init(tf);

            if (!insert.isCached()) {
                insert.setTable("school");

                insert.addColumn("schoolID");
                insert.addColumn("name");
                insert.addColumn("phone");
                insert.addColumn("address");
                insert.addPlaceHolder(4);
            }

            insert.getParameter().add(ent.schoolID);
            insert.getParameter().add(ent.name);
            insert.getParameter().add(ent.phone);
            insert.getParameter().add(ent.address);
            ent.schoolID = ConvertUtils.toInt(insert.executeInsert());

            return ent.schoolID > 0;

        } catch (Exception ex) {
            GA.app.error("Exception", ex);
        }finally {
            tf.releasePooledConnection();
        }
        return false;
    }
    
    public boolean update(SchoolEnt ent) {
        TransactionFrame tf = new TransactionFrame(configName);
        DBUpdate update = DBCacheImpl.getInstance().update("update_school");
        try {
            update.init(tf);

            if (!update.isCached()) {
                update.setTable("school");

                update.addColumn("name");
                update.addColumn("phone");
                update.addColumn("address");                
                update.addClause("schoolID", "=?");
            }

            update.getParameter().add(ent.schoolID);
            update.getParameter().add(ent.name);
            update.getParameter().add(ent.address);
            update.getParameter().add(ent.schoolID);

            return update.executeUpdate() > 0;

        } catch (Exception ex) {
            GA.app.error("Exception", ex);
        }finally {
            tf.releasePooledConnection();
        }
        return false;
    }
    
    public List<SchoolEnt> getListSchool(int index, int offset) {
        List<SchoolEnt> result = new ArrayList();
        TransactionFrame tf = new TransactionFrame(configName);
        DBQuery query = DBCacheImpl.getInstance().query("get_list_school");
        try {
            query.init(tf);

            if (!query.isCached()) {
                query.addTable("school");
                query.addColumn("schoolID");
                query.addColumn("name");
                query.addColumn("phone");
                query.addColumn("address");
                query.setQueryOrder("schoolID", "desc");
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
    
    public SchoolEnt getDetail(long schoolID) {
        SchoolEnt result = new SchoolEnt();
        TransactionFrame tf = new TransactionFrame(configName);
        DBQuery query = DBCacheImpl.getInstance().query("get_detail_school");
        try {
            query.init(tf);

            if (!query.isCached()) {
                query.addTable("school");
                query.addColumn("schoolID");
                query.addColumn("name");
                query.addColumn("phone");
                query.addColumn("address");
                query.addClause("schoolID = ?");
                query.setQueryOrder("schoolID", "desc");
            }
            query.getParameter().add(schoolID);

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
    private SchoolEnt getByResultSet(ResultSet rs) throws SQLException {
        SchoolEnt item = new SchoolEnt();
        item.schoolID = rs.getLong("schoolID");
        item.name = rs.getString("name");
        item.phone = rs.getString("phone");
        item.address = rs.getString("address");
        return item;
    }
//</editor-fold>
}
