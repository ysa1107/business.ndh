/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussiness;

import com.kyt.framework.config.LogUtil;
import constant.ECode;
import entity.TListUserResult;
import entity.TUserFilter;
import entity.TUserResult;
import entity.TUserValue;
import model.UserDA;
import org.apache.log4j.Logger;

/**
 *
 * @author ysa
 */
public class UserBC {

    private static final Logger logger = LogUtil.getLogger(UserBC.class);

    public static UserBC instance = null;

    public static UserBC getInstance() {
        if (instance == null) {
            instance = new UserBC();
        }
        return instance;
    }

    public UserBC() {

    }
    public TUserResult insert(TUserValue item) {
        TUserResult result = new TUserResult();
        try {
            result.setValue(item);
            if (!UserDA.insert(item)) {
                result.setErrorCode(ECode.SERVER_ERROR);
                return result;
            }
        } catch (Exception ex) {
            logger.error(ex.getStackTrace());
        }
        return result;
    }
    
    public boolean update(TUserValue item) {
        TUserResult result = new TUserResult();
        try {
            result.setValue(item);
            if (!UserDA.update(item)) {
                result.setErrorCode(ECode.SERVER_ERROR);
                return false;
            }
        } catch (Exception ex) {
            logger.error(ex.getStackTrace());
        }
        return true;
    }

    public TListUserResult getUsers(TUserFilter filter){
        TListUserResult result = new TListUserResult();
        try{
            return UserDA.getUsers(filter);
        }catch(Exception ex){
            logger.error(ex.getStackTrace());
        }
        return result;
    }

    public TUserResult getUserByUserName(String username){
        TUserResult result = new TUserResult();
        try{
            long userID = UserDA.getUserByUsername(username);
            if(userID == 0){
                return result;
            }
            result = UserDA.getUser(userID);
        }catch (Exception ex){
            logger.error(ex.getStackTrace());
        }
        return result;
    }
}
