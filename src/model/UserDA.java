package model;

import com.kyt.framework.config.LogUtil;
import com.kyt.framework.dbconn.ClientManager;
import com.kyt.framework.dbconn.ManagerIF;
import com.kyt.framework.util.DateTimeUtils;
import constant.DATABASE;
import entity.TListUserResult;
import entity.TUserFilter;
import entity.TUserResult;
import entity.TUserValue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.log4j.Logger;

/**
 *
 * @author Y Sa 10/11/2017
 */
public class UserDA {

    private static final Logger logger = LogUtil.getLogger(UserDA.class);
    private static UserDA _instance;

    private static final String INSERT_QUERY = "INSERT INTO user(identityCard, fullName, userName, password, email, address, phone, schoolID, dateCreated, extProperties, avatarURL, type, status, birthday, genre) "
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY = "UPDATE user SET identityCard = ?, fullName = ?, userName = ?, password = ?, email = ?, address = ?, phone = ?, schoolID = ?, dateModified = ?, extProperties = ?, avatarURL = ?, type = ?, status = ?, birthday = ?, gender = ? "
                + "WHERE userID = ? ";

    private static final String SELECT_QUERY = "SELECT * FROM user WHERE %s";
    
    private static final String COUNT_QUERY = "SELECT count(1) FROM user WHERE %s";
    
    static final String GET_USER_QUERY = "SELECT * "
            + "FROM user WHERE userID = ?";

    static final String GET_USERS_SELECT = "SELECT * ";

    static final String GET_USERS_FROM = "FROM user WHERE 1=1 ";
    static final String GET_USERS_COUNT = "SELECT COUNT(1) as totalRecord ";

    static final String USERS_BY_USERNAME_FROM = "FROM user WHERE userName = ? ";
    static final String DELETE_USER = "DELETE FROM user WHERE userID = ? ";
    
    public static UserDA getInstance() {
        if (_instance == null) {
            _instance = new UserDA();
        }
        return _instance;
    }

    public static TUserValue toValue(ResultSet rs) {
        TUserValue ret = new TUserValue();
        try {
            if (rs != null) {
                ret.setUserID(rs.getLong("userID"));
                ret.setAddress(rs.getString("address"));
                ret.setAvatarURL(rs.getString("avatarURL"));
                ret.setDateCreated(rs.getLong("dateCreated"));
                ret.setDateModified(rs.getLong("dateModified"));
                ret.setEmail(rs.getString("email"));
                ret.setExtProperties(rs.getLong("extProperties"));
                ret.setFullName(rs.getString("fullName"));
                ret.setIdentityCard(rs.getString("identityCard"));
                ret.setPassword(rs.getString("password"));
                ret.setPhone(rs.getString("phone"));
                ret.setSchoolID(rs.getLong("schoolID"));
                ret.setStatus(rs.getShort("status"));
                ret.setType(rs.getShort("type"));
                ret.setUserID(rs.getLong("userID"));
                ret.setUserName(rs.getString("userName"));
                ret.setBirthday(rs.getLong("birthday"));
                ret.setGender(rs.getShort("gender"));
            }
        } catch (Exception e) {
            logger.error(LogUtil.stackTrace(e));
        }
        return ret;
    }
    
    public static boolean insert(TUserValue item) {
        boolean result = false;
        ManagerIF cm = ClientManager.getInstance(DATABASE.NDH);
        Connection cnn = cm.borrowClient();
        try (PreparedStatement st = cnn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int index = 0; 
            st.setString(++index,item.identityCard);
            st.setString(++index,item.fullName);
            st.setString(++index,item.userName);
            st.setString(++index,item.password);
            st.setString(++index,item.email);
            st.setString(++index,item.address);
            st.setString(++index,item.phone);
            st.setLong(++index, item.schoolID);
            st.setLong(++index, DateTimeUtils.getMilisecondsNow());
            st.setLong(++index, item.extProperties);
            st.setString(++index, item.avatarURL);
            st.setShort(++index, item.type);
            st.setShort(++index, item.status);
            st.setLong(++index, item.birthday);
            st.setShort(++index, item.gender);
            int row = st.executeUpdate();
            result = row > 0;
        } catch (SQLException ex) {
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            if (cnn != null) {
                cm.returnClient(cnn);
            }
        }
        return result;
    }
    
    public static boolean update(TUserValue item) {
        boolean result = false;
        ManagerIF cm = ClientManager.getInstance(DATABASE.NDH);
        Connection cnn = cm.borrowClient();
        try (PreparedStatement st = cnn.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int index = 0; 
            st.setString(++index,item.identityCard);
            st.setString(++index,item.fullName);
            st.setString(++index,item.userName);
            st.setString(++index,item.password);
            st.setString(++index,item.email);
            st.setString(++index,item.address);
            st.setString(++index,item.phone);
            st.setLong(++index, item.schoolID);
            st.setLong(++index, DateTimeUtils.getMilisecondsNow());
            st.setLong(++index, item.extProperties);
            st.setString(++index, item.avatarURL);
            st.setShort(++index, item.type);
            st.setShort(++index, item.status);
            st.setLong(++index, item.userID);
            int row = st.executeUpdate();
            result = row > 0;
        } catch (SQLException ex) {
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            if (cnn != null) {
                cm.returnClient(cnn);
            }
        }
        return result;
    }
    
    public static TUserResult getUser(long userId) {
        TUserResult result = new TUserResult();
        ManagerIF cm = ClientManager.getInstance(DATABASE.NDH);
        Connection cnn = cm.borrowClient();
        try (PreparedStatement ps = cnn.prepareStatement(GET_USER_QUERY)) {
            ps.setLong(1, userId);
            TUserValue user = null;

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = toValue(rs);
            }

            result.setValue(user);
            result.setErrorCode((short) 0);

            rs.close();
        } catch (SQLException ex) {
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            if (cnn != null) {
                cm.returnClient(cnn);
            }
        }
        return result;
    }

    public static boolean deleteUser(long id) {
        boolean result = false;
        ManagerIF cm = ClientManager.getInstance(DATABASE.NDH);
        Connection cnn = cm.borrowClient();
        try (PreparedStatement ps = cnn.prepareStatement(DELETE_USER)) {

            ps.setLong(1, id);
            result = ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            if (cnn != null) {
                cm.returnClient(cnn);
            }
        }
        return result;
    }

    public static long getUserByUsername(String username) {
        long ret = 0;
        ManagerIF cm = ClientManager.getInstance(DATABASE.NDH);
        Connection cnn = cm.borrowClient();
        try (PreparedStatement ps = cnn.prepareStatement(GET_USERS_SELECT + USERS_BY_USERNAME_FROM)) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret = rs.getLong("id");
                }
            }
        } catch (SQLException ex) {
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            if (cnn != null) {
                cm.returnClient(cnn);
            }
        }
        return ret;
    }

    public static TListUserResult getUsers(TUserFilter filter) {
        TListUserResult result = new TListUserResult();
        ManagerIF cm = ClientManager.getInstance(DATABASE.NDH);
        Connection cnn = cm.borrowClient();
        try {
            List<TUserValue> listData = new ArrayList<>();
            Map<Integer, Object> params = new HashMap<>();
            StringBuilder fromQuery = new StringBuilder(GET_USERS_FROM);

            int i = 1;
            if (filter.getLstUserIds() != null && filter.getLstUserIds().size() > 0) {
                fromQuery.append("AND id IN ( ");
                for (int t = 0; t < filter.getLstUserIds().size(); t++) {
                    fromQuery.append("?, ");
                    params.put(i++, filter.getLstUserIds().get(t));
                }
                fromQuery.append("0) ");
            }

            if (filter.getUsername() != null) {
                fromQuery.append("AND username like ? ");
                params.put(i++, "%" + filter.getUsername() + "%");
            }

//            if (filter.getEmail() != null) {
//                fromQuery.append("AND email like ? ");
//                params.put(i++, "%" + filter.getEmail() + "%");
//            }
//            if (filter.getStatus() > 0) {
//                fromQuery.append("AND status = ? ");
//                params.put(i++, filter.getStatus());
//            }
//            if (filter.getGender() > 0) {
//                fromQuery.append("AND gender = ? ");
//                params.put(i++, filter.getGender());
//            }
//            if (filter.getBrandId() > 0) {
//                fromQuery.append("AND id IN (SELECT user_id FROM brand_user WHERE brand_id = ?)");
//                params.put(i++, filter.getBrandId());
//            }


            //count query
            PreparedStatement ps = cnn.prepareStatement(GET_USERS_COUNT + fromQuery);
            setParameters(ps, params);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int totalRecord = rs.getInt("totalRecord");

            //select query
//            if (filter.getOrderBy() != null && !"".equals(filter.getOrderBy())) {
//                fromQuery.append("ORDER BY ").append(filter.getOrderBy());
//
//                if (filter.getOrderType() != null && !"".equals(filter.getOrderBy())) {
//                    fromQuery.append(" ").append(filter.getOrderType()).append(" ");
//                }
//            }

            String countStr = "";
            if (filter.getPageIndex() > 0) {
                countStr = "LIMIT ?, ? ";
                params.put(i++, (filter.getPageIndex() - 1) * filter.getPageSize());
                params.put(i++, filter.getPageSize());
            }

            ps = cnn.prepareStatement(GET_USERS_SELECT + fromQuery + countStr);
            setParameters(ps, params);
            rs = ps.executeQuery();
            TUserValue user = null;

            while (rs.next()) {
                user = toValue(rs);
                listData.add(user);
            }

            //set data
            result.setErrorCode((short) 0);
            result.setListData(listData);
            result.setTotalRecords(totalRecord);

            ps.close();
        } catch (SQLException ex) {
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            if (cnn != null) {
                cm.returnClient(cnn);
            }
        }
        return result;
    }

    private static void setParameters(PreparedStatement st, Map<Integer, Object> params) throws SQLException {
        if (params != null) {
            for (Integer key : params.keySet()) {
                st.setObject(key, params.get(key));
            }
        }
    }
}
