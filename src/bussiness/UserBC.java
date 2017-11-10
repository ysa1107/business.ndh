/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussiness;

import com.kyt.framework.config.LogUtil;
import constant.ECode;
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

    public static TUserResult insert(TUserValue item) {
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
    
    public static TUserResult update(TUserValue item) {
        TUserResult result = new TUserResult();
        try {
            result.setValue(item);
            if (!UserDA.update(item)) {
                result.setErrorCode(ECode.SERVER_ERROR);
                return result;
            }
        } catch (Exception ex) {
            logger.error(ex.getStackTrace());
        }
        return result;
    }        
}
