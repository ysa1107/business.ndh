/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.List;

/**
 *
 * @author ysa
 */
public class TListUserResult {
    public long totalRecords;
    public List<TUserValue> listData;

    public short getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(short errorCode) {
        this.errorCode = errorCode;
    }

    public short errorCode;

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<TUserValue> getListData() {
        return listData;
    }

    public void setListData(List<TUserValue> listData) {
        this.listData = listData;
    }
}
