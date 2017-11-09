/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Y Sa
 */
public class EnumUtils {

    public enum PageDefines {
        Default_Page,
        UserAdmin_Page,
        User_Page,
        Brand_Page,
        School_Page,
        Category_Page,
        Article_Page,
    }
    
    public static final String SESSION_USER_LOGIN = "UserLogin";
    public static final String SESSION_USERID_LOGIN = "UserIDLogin";
    public static final String SESSION_USER_RIGHT = "UserIDLoginRights";
    public static final String SESSION_USER_SSO_TOKEN = "UserIDLoginTokenSSO";
    public static final String SESSION_URL_BACK = "URLComeBack";
}
