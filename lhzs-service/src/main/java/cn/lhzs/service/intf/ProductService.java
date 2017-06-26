package cn.lhzs.service.intf;

import cn.lhzs.data.bean.Product;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by ZHX on 2017/4/27.
 */
public interface ProductService {
    List<Product> getProdList(Product product);

    Product getProductByProdId(String prodId);

    void addProd(Product product);

    void deleteProdByProdId(String prodId);

    void updateProd(Product product);

    JSONObject searchProduct(String key);

    Long getCount();

    JSONObject getProds(String data);

    void deleteTable();
}
