/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.CategoryEnt;

/**
 *
 * @author ysa
 */
public class CategoryUtils {

    public static String genCategoryLink(CategoryEnt item, short type, String rootURL) {
        try {
            if (type == 1) { //article
                return rootURL + "bai-viet/" + item.slug + "-" + item.categoryID + ".html";
            }
        } catch (Exception e) {

        }
        return "";
    }
}
