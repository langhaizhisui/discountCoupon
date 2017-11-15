package cn.lhzs.data.dao;

import cn.lhzs.data.base.Mapper;
import cn.lhzs.data.bean.Article;

public interface ShopMapper  extends Mapper<Article> {

    void deleteTable();
}