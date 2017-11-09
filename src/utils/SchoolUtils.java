/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.kyt.framework.config.LogUtil;
import entity.BrandEnt;
import entity.SchoolEnt;

/**
 *
 * @author ysa
 */
public class SchoolUtils {

    private static final org.apache.log4j.Logger _logger = LogUtil.getLogger(SchoolUtils.class);


    public static String genBrandLink(BrandEnt ent, String rootURL) {
        try {
            return rootURL + "thuong-hieu/" + ent.slug + "-" + ent.brandID + ".html";
        } catch (Exception e) {

        }
        return "";
    }

    public static String genSchoolLink(SchoolEnt ent, String rootURL) {
        try {
            return rootURL + "truong-hoc/" + ent.slug + "-" + ent.schoolID + ".html";
        } catch (Exception e) {

        }
        return "";
    }
    
    public static String genSchoolImage(SchoolEnt ent, String staticURL) {
        try {
            return staticURL + ent.featuredImages;
        } catch (Exception e) {

        }
        return "";
    }
}
