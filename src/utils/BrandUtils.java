/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.BrandEnt;

/**
 *
 * @author ysa
 */
public class BrandUtils {

    public static String genImageLink(BrandEnt ent, String staticURL) {
        try {
            return staticURL + ent.imageURL;
        } catch (Exception e) {

        }
        return "";
    }
    
    public static String genBrandLink(BrandEnt ent,String rootURL) {
        try {
            return rootURL + "thuong-hieu/" + ent.slug + "-" + ent.brandID + ".html";
        } catch (Exception e) {

        }
        return "";
    }
}
