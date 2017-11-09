package model;

import com.nct.framework.dbconn.ClientManager;
import com.nct.framework.dbconn.ManagerIF;
import com.nct.framework.util.ConvertUtils;
import ga.log4j.GA;
import grabman.entity.DeviceEnt;
import grabman.entity.NotifyEnt;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class DeviceDA {

    private static final Lock createLock_ = new ReentrantLock();
    private static final String configName = "grabman";
    private static final Map<String, DeviceDA> sInstance = new HashMap<>();

    public static DeviceDA getInstance() {
        if (sInstance.get(configName) == null) {
            try {
                createLock_.lock();
                sInstance.put(configName, new DeviceDA());
            } finally {
                createLock_.unlock();
            }
        }
        return sInstance.get(configName);
    }

    public DeviceDA() {
    }

    public boolean insert(DeviceEnt ent) {
        TransactionFrame tf = new TransactionFrame(configName);
        DBInsert insert = DBCacheImpl.getInstance().insert("insert_device");
        try {
            insert.init(tf);

            if (!insert.isCached()) {
                insert.setTable("device");
                insert.addColumn("UserId");
                insert.addColumn("KeyPush");
                insert.addColumn("DeviceId");
                insert.addColumn("DeviceName");
                insert.addColumn("OsName");
                insert.addColumn("OsVersion");
                insert.addColumn("AppKey");
                insert.addColumn("AppVersion");
                insert.addColumn("Status");
                insert.addColumn("DateCreated");
                insert.addColumn("LastActiveDated");
                insert.addColumn("LastReceived");
                insert.addPlaceHolder(12);
            }

            insert.getParameter().add(ent.userId);
            insert.getParameter().add(ent.keyPush);
            insert.getParameter().add(ent.deviceId);
            insert.getParameter().add(ent.deviceName);
            insert.getParameter().add(ent.osName);
            insert.getParameter().add(ent.osVersion);
            insert.getParameter().add(ent.appKey);
            insert.getParameter().add(ent.appVersion);
            insert.getParameter().add((short)1);
            insert.getParameter().add(System.currentTimeMillis());
            insert.getParameter().add(0);
            insert.getParameter().add(0);
            ent.id = ConvertUtils.toInt(insert.executeInsert());

            return ent.id > 0;

        } catch (Exception ex) {
            GA.app.error("Exception", ex);
        } finally {
            tf.releasePooledConnection();
        }
        return false;
    }

    public boolean update(DeviceEnt ent) {
        TransactionFrame tf = new TransactionFrame(configName);
        DBUpdate update = DBCacheImpl.getInstance().update("update_device");
        try {
            update.init(tf);

            if (!update.isCached()) {
                update.setTable("device");

                update.addColumn("KeyPush");
                update.addColumn("LastActiveDated");
                update.addColumn("LastReceived");
                update.addClause("UserId", "=?");
                update.addClause("DeviceId", "=?");
            }

            update.getParameter().add(ent.keyPush);
            update.getParameter().add(System.currentTimeMillis());
            update.getParameter().add(System.currentTimeMillis());
            update.getParameter().add(ent.userId);
            update.getParameter().add(ent.deviceId);
            return update.executeUpdate() > 0;

        } catch (Exception ex) {
            GA.app.error("Exception", ex);
        } finally {
            tf.releasePooledConnection();
        }
        return false;
    }

    public DeviceEnt checkExist(long userID, String deviceID) {
        DeviceEnt result = new DeviceEnt();
        TransactionFrame tf = new TransactionFrame(configName);
        DBQuery query = DBCacheImpl.getInstance().query("check_device");
        try {
            query.init(tf);
            if (!query.isCached()) {
                query.addTable("device");
                query.addColumn("Id");
                query.addColumn("UserId");
                query.addColumn("KeyPush");
                query.addColumn("DeviceId");
                query.addColumn("DeviceName");
                query.addColumn("OsName");
                query.addColumn("OsVersion");
                query.addColumn("AppKey");
                query.addColumn("AppVersion");
                query.addColumn("Status");
                query.addColumn("DateCreated");
                query.addColumn("LastActiveDated");
                query.addColumn("LastReceived");
                query.addClause("UserId = ?");
                query.addClause("DeviceId = ?");
            }
            query.getParameter().add(userID);
            query.getParameter().add(deviceID);

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    try {
                        result = getByResultSet(rs);
                    } catch (SQLException ex) {
                        
                    }
                }
            }
        } catch (Exception ex) {
            GA.jdbc.error("Exception", ex);
        } finally {
            tf.releasePooledConnection();
        }
        return null;
    }

    private DeviceEnt getByResultSet(ResultSet rs) throws SQLException {
        DeviceEnt item = new DeviceEnt();
        item.id = rs.getLong("Id");
        item.userId = rs.getLong("UserId");
        item.keyPush = rs.getString("KeyPush");
        item.deviceId = rs.getString("DeviceId");
        item.deviceName = rs.getString("DeviceName");
        item.osName = rs.getString("OsName");
        item.osVersion = rs.getString("OsVersion");
        item.appKey = rs.getString("AppKey");
        item.appVersion = rs.getString("AppVersion");
        item.status = rs.getShort("Status");
        item.dateCreated = rs.getLong("DateCreated");
        item.lastActiveDated = rs.getLong("LastActiveDated");
        item.lastReceived = rs.getLong("LastReceived");
        return item;
    }
}
