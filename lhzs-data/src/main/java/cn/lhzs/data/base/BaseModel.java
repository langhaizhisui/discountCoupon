package cn.lhzs.data.base;

import javax.persistence.Transient;

/**
 * Created by ZHX on 2017/11/9.
 */
public class BaseModel {
    @Transient
    private Integer page;
    @Transient
    private Integer size;
    @Transient
    private Integer index;
    @Transient
    private Boolean lock;
    @Transient
    private String cTime;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }
}
