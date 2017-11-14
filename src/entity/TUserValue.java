/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


/**
 *
 * @author Y Sa
 */
public class TUserValue {
    public long userID;
    public String identityCard;
    public String userName;
    public String password;
    public String fullName;
    public String email;
    public String address;
    public String phone;
    public long schoolID;
    public long dateCreated;
    public long dateModified;

    public long extProperties;
    public String avatarURL;
    public short type;
    public short status;
    public long birthday;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public long getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(long schoolID) {
        this.schoolID = schoolID;
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

    public long getExtProperties() {
        return extProperties;
    }

    public void setExtProperties(long extProperties) {
        this.extProperties = extProperties;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }



    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public short getGender() {
        return gender;
    }

    public void setGender(short gender) {
        this.gender = gender;
    }

    public short gender;
    
    
    public TUserValue(TUserValue item){
        this.userID = item.userID;
        this.identityCard = item.identityCard;
        this.userName = item.userName;
                this.password = item.password;

        this.fullName = item.fullName;
        this.email = item.email;
        this.address = item.address;
        this.phone = item.phone;
        this.schoolID = item.schoolID;
        this.dateCreated = item.dateCreated;
        this.dateModified = item.dateModified;
        this.extProperties = item.extProperties;
        this.avatarURL = item.avatarURL;
        //this.schoolName = SchoolDA.getInstance().getDetail(item.schoolId).name;
        this.type = item.type;
        this.status = item.status;
        this.birthday = item.birthday;
        this.gender = item.gender;
    }    
    
    public TUserValue(){
        
    }
}
