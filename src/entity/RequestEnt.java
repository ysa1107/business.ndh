///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package entity;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// *
// * @author ysa
// */
//public class RequestEnt {
//
//    public long getTransactionID() {
//        return transactionID;
//    }
//
//    public long getSenderID() {
//        return senderID;
//    }
//
//    public long getReceiverID() {
//        return receiverID;
//    }
//
//    public int getActionType() {
//        return actionType;
//    }
//
//    public long getDateCreated() {
//        return dateCreated;
//    }
//
//    public long getDateModified() {
//        return dateModified;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public long transactionID;
//    public long senderID;
//    public long receiverID;
//    public int actionType;
//    public long dateCreated;
//    public long dateModified;
//    public String message;
//    public String senderName;
//    public String receiveName;
//    public String schoolName;
//    public String senderDate;
//    public String receiveDate;
//
//    public RequestEnt(){}
//    
//    public RequestEnt(RequestEnt item) {
//        this.transactionID = item.transactionID;
//        this.senderID = item.senderID;
//        this.receiverID = item.receiverID;
//        this.actionType = item.actionType;
//        this.dateCreated = item.dateCreated;
//        this.dateModified = item.dateModified;
//        this.message = item.message;
//        this.schoolName = "";
//        if (item.senderID > 0) {
//            this.senderName = UserDA.getInstance().getDetail(item.senderID).fullName;
//        }
//        if (item.receiverID > 0) {
//            this.receiveName = UserDA.getInstance().getDetail(item.receiverID).fullName;
//        }
//        senderDate = DateTimeUtils.toString(new Date(item.dateCreated), "dd/MM/yyyy");
//    }
//    
//    public static List<RequestEnt> toListRequestEnt(List<RequestEnt> lst){
//        List<RequestEnt> result = new ArrayList<>();
//        for(RequestEnt item:lst){
//            result.add(new RequestEnt(item));
//        }
//        return result;
//    }    
//}
