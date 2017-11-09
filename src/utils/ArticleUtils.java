/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.ArticleEnt;

/**
 *
 * @author ysa
 */
public class ArticleUtils {

    public static String genImageLink(ArticleEnt ent, String staticURL) {
        try {
            return staticURL + ent.imageURL;
        } catch (Exception e) {

        }
        return "";
    }
    
    public static String genArticleLink(ArticleEnt ent,String rootURL) {
        try {
            return rootURL + "bai-viet/" + ent.categorySlug + "/" + ent.slug + "-" + ent.articleID + ".html";
        } catch (Exception e) {

        }
        return "";
    }
}
