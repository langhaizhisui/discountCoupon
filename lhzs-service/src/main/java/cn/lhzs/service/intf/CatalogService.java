package cn.lhzs.service.intf;

import cn.lhzs.data.bean.Catalog;
import cn.lhzs.data.bean.Product;

import java.util.List;

/**
 * Created by ZHX on 2017/5/7.
 */
public interface CatalogService {
    List<Catalog> getCatalogList();

    Catalog getCatalogByProdId(String cataId);

    void addCatalog(Catalog catalog);

    void deleteCatalogByCataId(String cataId);

    void updateCatalog(Catalog catalog);
}
