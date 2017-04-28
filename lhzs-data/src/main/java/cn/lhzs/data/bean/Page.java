package cn.lhzs.data.bean;

/**
 * Created by ZHX on 2017/4/27.
 */
public class Page {
    private Integer page;
    private Integer size;
    private Integer index;
    private Boolean lock;

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


}
