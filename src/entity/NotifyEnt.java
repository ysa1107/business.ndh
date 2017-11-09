/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ysa
 */
public class NotifyEnt {

    public long id;
    public String message;
    public long userCreated;
    public long dateCreated;
    public short status;
    public String userCreatedName;
    public String dateCreatedView;


    public NotifyEnt(){}
    
    public NotifyEnt(NotifyEnt item) {
        this.id = item.id;
        this.message = item.message;
        this.dateCreated = item.dateCreated;
        this.status = item.status;
        this.userCreated = item.userCreated;
        if (item.userCreated > 0) {
            this.userCreatedName = UserDA.getInstance().getDetail(item.userCreated).userName;
        }        
        this.dateCreatedView = DateTimeUtils.toString(new Date(item.dateCreated), "dd/MM/yyyy");
    }
    
    public static List<NotifyEnt> toListNotifyEnt(List<NotifyEnt> lst){
        List<NotifyEnt> result = new ArrayList<>();
        for(NotifyEnt item:lst){
            result.add(new NotifyEnt(item));
        }
        return result;
    }    
}
