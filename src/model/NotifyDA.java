package model;

import com.nct.framework.dbconn.ClientManager;
import com.nct.framework.dbconn.ManagerIF;
import com.nct.framework.util.ConvertUtils;
import com.nct.framework.util.JSONUtil;
import ga.log4j.GA;
import grabman.entity.NotifyEnt;
import grabman.entity.RequestEnt;
import grabman.entity.SchoolEnt;
import grabman.entity.UserEnt;
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
public class NotifyDA {

    private static final Lock createLock_ = new ReentrantLock();
    private static final String configName = "grabman";
    private static final Map<String, NotifyDA> sInstance = new HashMap<>();

    public static NotifyDA getInstance() {
        if (sInstance.get(configName) == null) {
            try {
                createLock_.lock();
                sInstance.put(configName, new NotifyDA());
            } finally {
                createLock_.unlock();
            }
        }
        return sInstance.get(configName);
    }

    public NotifyDA() {
    }

   

    private NotifyEnt getByResultSet(ResultSet rs) throws SQLException {
        NotifyEnt item = new NotifyEnt();
        item.id = rs.getLong("id");
        item.message = rs.getString("message");
        item.status = rs.getShort("status");
        item.userCreated = rs.getLong("userCreated");
        item.dateCreated = rs.getLong("dateCreated");
        return item;
    }
    
    public List<NotifyEnt> getListNotify(int offset, int count) {
        List<NotifyEnt> result = new ArrayList<>();
        ManagerIF cm = ClientManager.getInstance(this.configName);
        Connection cnn = cm.borrowClient();
        try {
            String query = "SELECT * FROM `notify` WHERE True";
            //String count_query = "SELECT COUNT(`id`) AS Total FROM `notify` WHERE True";
            //String sub_query = "";
            query = query + " ORDER BY dateCreated DESC limit ?,? ";
            offset = (offset - 1) * count > 0 ? offset : 0;
            count = count > 0 ? count : 10;

            PreparedStatement stmt = cnn.prepareStatement(query);
            stmt.setFetchSize(-2147483648);
            stmt.setInt(1, offset);
            stmt.setInt(2, count);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result.add(getByResultSet(resultSet));
            }
            resultSet.close();
            stmt.close();
        } catch (Exception ex) {
            GA.jdbc.error("Exception", ex);
        } finally {
            cm.returnClient(cnn);
        }
        return result;
    }    
}
