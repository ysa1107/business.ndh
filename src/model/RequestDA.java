package model;

import com.nct.framework.dbconn.ClientManager;
import com.nct.framework.dbconn.ManagerIF;
import com.nct.framework.util.ConvertUtils;
import com.nct.framework.util.JSONUtil;
import ga.log4j.GA;
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
public class RequestDA {

    private static final Lock createLock_ = new ReentrantLock();
    private static final String configName = "grabman";
    private static final Map<String, RequestDA> sInstance = new HashMap<>();

    public static RequestDA getInstance() {
        if (sInstance.get(configName) == null) {
            try {
                createLock_.lock();
                sInstance.put(configName, new RequestDA());
            } finally {
                createLock_.unlock();
            }
        }
        return sInstance.get(configName);
    }

    public RequestDA() {
    }

    //<editor-fold defaultstate="collapsed" desc="User">
    public RequestEnt request(RequestEnt ent) {
        TransactionFrame tf = new TransactionFrame(configName);
        DBInsert insert = DBCacheImpl.getInstance().insert("insert_request");
        try {
            insert.init(tf);
            long dateCreated = System.currentTimeMillis();
            if (!insert.isCached()) {
                insert.setTable("request");
                insert.addColumn("senderID");
                insert.addColumn("receiverID");
                insert.addColumn("actionType");
                insert.addColumn("dateCreated");
                insert.addColumn("message");
                insert.addPlaceHolder(5);
            }

            insert.getParameter().add(ent.senderID);
            insert.getParameter().add(ent.receiverID);
            insert.getParameter().add(ent.actionType);
            insert.getParameter().add(dateCreated);
            insert.getParameter().add(ent.message);
            long id = insert.executeInsert();
            ent.transactionID = id;
            ent.dateCreated = dateCreated;
            return ent;
        } catch (Exception ex) {
            GA.app.error("Exception", ex);
        } finally {
            tf.releasePooledConnection();
        }
        return null;
    }

    public boolean update(RequestEnt ent) {
        TransactionFrame tf = new TransactionFrame(configName);
        DBInsert insert = DBCacheImpl.getInstance().insert("insert_request");
        try {
            insert.init(tf);

            if (!insert.isCached()) {
                insert.setTable("request");
                insert.addColumn("receiverID");
                insert.addColumn("actionType");
                insert.addPlaceHolder(2);
            }

            insert.getParameter().add(ent.receiverID);
            insert.getParameter().add(ent.actionType);
            return insert.executeInsert() > 0;

        } catch (Exception ex) {
            GA.app.error("Exception", ex);
        } finally {
            tf.releasePooledConnection();
        }
        return false;
    }

    private RequestEnt getByResultSet(ResultSet rs) throws SQLException {
        RequestEnt item = new RequestEnt();
        item.transactionID = rs.getLong("transactionID");
        item.senderID = rs.getLong("senderID");
        item.receiverID = rs.getLong("receiverID");
        item.actionType = rs.getInt("actionType");
        item.dateCreated = rs.getLong("dateCreated");
        item.message = rs.getString("message");
        return item;
    }
    
    public List<RequestEnt> getListRequest(int offset, int count, long senderID, long receiveID, int actionType) {
        List<RequestEnt> result = new ArrayList<>();
        ManagerIF cm = ClientManager.getInstance(this.configName);
        Connection cnn = cm.borrowClient();
        try {
            String query = "SELECT * FROM `request` WHERE True";
            String count_query = "SELECT COUNT(`transactionID`) AS Total FROM `request` WHERE True";
            String sub_query = "";


            if (senderID> 0) {
                sub_query = " AND `senderID` = " + senderID;
                query = query + sub_query;
                count_query = count_query + sub_query;
            }

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
//
//            stmt = cnn.prepareStatement(count_query);
//            resultSet = stmt.executeQuery();
//            if (resultSet.next()) {
//                result.totalRecords = resultSet.getLong(1);
//            }
            stmt.close();
        } catch (Exception ex) {
            GA.jdbc.error("Exception", ex);
        } finally {
            cm.returnClient(cnn);
        }
        return result;
    }
    
    
//    public List<HistoryEnt> getListHistory(int index, int offset, long senderID, long receiveID, int actionType) {
//        List<HistoryEnt> result = new ArrayList();
//        TransactionFrame tf = new TransactionFrame(configName);
//        DBQuery query = DBCacheImpl.getInstance().query("get_list_request");
//        try {
//            query.init(tf);
//            if (!query.isCached()) {
//                query.addTable("request");
//                query.addColumn("transactionID");
//                query.addColumn("senderID");
//                query.addColumn("receiverID");
//                query.addColumn("actionType");
//                query.addColumn("dateCreated");
//                query.addColumn("message");
//                query.addClause("senderID = ?");
//                //query.addClause("receiverID = ?");
//                //query.addClause("actionType = ?");
//                query.setQueryOrder("dateCreated", "desc");
//                query.setLimit("?,?");
//            }
//
//            query.getParameter().add(senderID);
//            //query.getParameter().add(receiveID);
//            //query.getParameter().add(actionType);
//            query.getParameter().add(index * offset);
//            query.getParameter().add(offset);
//
//            GA.app.info("HistoryDA().getListHistory: " + query.toString());
//            try (ResultSet rs = query.executeQuery()) {
//                while (rs.next()) {
//                    try {
//                        result.add(getByResultSet(rs));
//                    } catch (SQLException ex) {
//                        GA.jdbc.error("Exception", ex);
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            GA.jdbc.error("Exception", ex);
//        } finally {
//            tf.releasePooledConnection();
//        }
//        return result;
//    }

//    public UserEnt getDetail(long userID) {
//        UserEnt result = new UserEnt();
//        TransactionFrame tf = new TransactionFrame(configName);
//        DBQuery query = DBCacheImpl.getInstance().query("get_detail_user");
//        try {
//            query.init(tf);
//
//            if (!query.isCached()) {
//                query.addTable("user");
//                query.addColumn("userID");
//                query.addColumn("identityCard");
//                query.addColumn("userName");
//                query.addColumn("fullName");
//                query.addColumn("email");
//                query.addColumn("address");
//                query.addColumn("phone");
//                query.addColumn("schoolID");
//                query.addColumn("dateCreated");
//                query.addColumn("dateModified");
//                query.addColumn("password");
//                query.addColumn("extProperties");
//                query.addColumn("avatarUrl");
//                query.addClause("userID = ?");
//                query.setQueryOrder("userID", "desc");
//            }
//            query.getParameter().add(userID);
//
//            try (ResultSet rs = query.executeQuery()) {
//                while (rs.next()) {
//                    try {
//                        result = getByResultSet(rs);
//                        return result;
//                    } catch (SQLException ex) {
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            GA.jdbc.error("Exception", ex);
//        } finally {
//            tf.releasePooledConnection();
//        }
//        return result;
//    }
//</editor-fold>
//    public List<FeedEnt> getByState(int state, int index, int offset) {
//        List<FeedEnt> result = new ArrayList();
//        TransactionFrame tf = new TransactionFrame(configName);
//        DBQuery query = DBCacheImpl.getInstance().query("get_feed_by_state");
//        try {
//            query.init(tf);
//
//            if (!query.isCached()) {
//                query.addTable("feed");
//                query.addColumn("id");
//                query.addColumn("title");
//                query.addColumn("thumbnail");
//                query.addColumn("media_type");
//                query.addColumn("media_url");
//                query.addColumn("category_id");
//                query.addColumn("state");
//                query.addClause("state", "=?");
//                query.setQueryOrder("id","desc");
//                query.setLimit("?,?");
//            }
//            query.getParameter().add(state);
//            query.getParameter().add(index * offset);
//            query.getParameter().add(offset);
//
//            try (ResultSet rs = query.executeQuery()) {
//                while (rs.next()) {
//                    try {
//                        result.add(getByResultSet(rs));
//                    } catch (SQLException ex) {
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            GA.jdbc.error("Exception", ex);
//        } finally {
//            tf.releasePooledConnection();
//        }
//        return result;
//    }
//    public List<FeedEnt> getByGenre(int genreId, int state, int index, int offset) {
//        List<FeedEnt> result = new ArrayList();
//        TransactionFrame tf = new TransactionFrame(configName);
//        DBQuery query = DBCacheImpl.getInstance().query("get_feed_by_genre");
//        try {
//            query.init(tf);
//
//            if (!query.isCached()) {
//                query.addTable("feed", "f");
//                query.addTable("genres_feed", "gf");
//
//                query.addColumn("f.id");
//                query.addColumn("f.title");
//                query.addColumn("f.thumbnail");
//                query.addColumn("f.media_type");
//                query.addColumn("f.media_url");
//                query.addColumn("f.category_id");
//                query.addColumn("f.state");
//
//                query.addJoin("gf.feed_id","f.id");
//                query.addClause("gf.genres_id", "=?");
//                query.addClause("f.state", "=?");
//                query.setQueryOrder("id","desc");
//                query.setLimit("?,?");
//            }
//            query.getParameter().add(genreId);
//            query.getParameter().add(state);
//            query.getParameter().add(index * offset);
//            query.getParameter().add(offset);
//
//            try (ResultSet rs = query.executeQuery()) {
//                while (rs.next()) {
//                    try {
//                        result.add(getByResultSet(rs));
//                    } catch (SQLException ex) {
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            GA.jdbc.error("Exception", ex);
//        } finally {
//            tf.releasePooledConnection();
//        }
//        return result;
//    }
}
