package entity;

import java.util.List;

/**
 * Created by ysa on 11/12/17.
 */
public class TUserFilter {

    public List<String> lstUserIds;
    public long fromDate;
    public  long toDate;
    public int pageIndex;
    public int pageSize;
    public String username;
    public List<String> lstUsernames;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getLstUserIds() {
        return lstUserIds;
    }

    public void setLstUserIds(List<String> lstUserIds) {
        this.lstUserIds = lstUserIds;
    }

    public  List<String> getLstUsernames(){
        return lstUsernames;
    }

    public void setLstUsernames(List<String> lstUsernames){
        this.lstUsernames = lstUsernames;
    }

    public long getFromDate() {
        return fromDate;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public long getToDate() {
        return toDate;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
