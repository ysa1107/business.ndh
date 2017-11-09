/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import grabman.model.SchoolDA;

/**
 *
 * @author ysa
 */
public class UserEnt {

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getExtProperties() {
        return extProperties;
    }

    public void setExtProperties(int extProperties) {
        this.extProperties = extProperties;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }
    public long userID;
    public String identityCard;
    public String userName;
    public String fullName;
    public String email;
    public String address;
    public String phone;
    public long schoolId;
    public long dateCreated;
    public long dateModified;
    public String password;
    public int extProperties;
    public String avatarUrl;
    public String schoolName = "";
    public short type;
    public String typeName;
    
    
    public UserEnt(UserEnt item){
        this.userID = item.userID;
        this.identityCard = item.identityCard;
        this.userName = item.userName;
        this.fullName = item.fullName;
        this.email = item.email;
        this.address = item.address;
        this.phone = item.phone;
        this.schoolId = item.schoolId;
        this.dateCreated = item.dateCreated;
        this.dateModified = item.dateModified;
        this.password = item.password;
        this.extProperties = item.extProperties;
        this.avatarUrl = item.avatarUrl;
        this.schoolName = SchoolDA.getInstance().getDetail(item.schoolId).name;
        this.type = item.type;
        switch(item.type){
            case 0: // receiver
                this.typeName = "Người nhận sự trợ giúp";
                break;
            case 1: //sender
                this.typeName = "Người gửi sự trợ giúp";
                break;
        }      
    }    
    
    public UserEnt(){
        
    }
}
