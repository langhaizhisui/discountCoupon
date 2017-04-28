package cn.lhzs.service.intf;

import cn.lhzs.data.bean.Product;

import java.util.List;

/**
 * Created by ZHX on 2017/4/27.
 */
public interface ProductService {
    List<Product> getProdList(String data);

    Product getProductByProdId(String prodId);

    void addProd(Product product);

    void deleteProdByProdId(String prodId);

    void updateProd(Product product);
}
