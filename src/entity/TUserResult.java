/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import constant.ECode;


/**
 *
 * @author ysa
 */
public class TUserResult {
    public short errorCode;
    public TUserValue value;

    public short getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(short errorCode) {
        this.errorCode = errorCode;
    }

    public TUserValue getValue() {
        return value;
    }

    public void setValue(TUserValue value) {
        this.value = value;
    }
    
    public TUserResult (){
        this.errorCode = ECode.SUCCESS;
        this.value = new TUserValue();
    }
}

