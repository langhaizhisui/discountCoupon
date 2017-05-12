package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Product;
import cn.lhzs.data.dao.ProductMapper;
import cn.lhzs.service.intf.ProductService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZHX on 2017/4/27.
 */
@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    @Resource
    public ProductMapper productMapper;

    @Override
    public List<Product> getProdList(Product product) {
        Integer page = product.getPage();
        Integer size = product.getSize();
        String category = product.getCategory();
        if (page == null || size == null) {
            product.setIndex(0);
            product.setSize(20);
        } else {
            product.setIndex((page - 1) * size);
            product.setSize(size);
        }
        if (category != null && !"".equals(category)) {
            product.setCategory(category);
        }
        return productMapper.selectProduct(product);
    }

    @Override
    public Product getProductByProdId(String prodId) {
        return productMapper.selectByPrimaryKey(Long.parseLong(prodId));
    }

    @Override
    public void addProd(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void deleteProdByProdId(String prodId) {
        productMapper.deleteByPrimaryKey(Long.parseLong(prodId));
    }

    @Override
    public void updateProd(Product product) {
        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public List<Product> searchProduct(String data) {
        if (data != null) {
            JSONObject jsonObject = JSONObject.parseObject(data).getJSONObject("reqData");
            String key = jsonObject.getString("key");
            key = "%" + key + "%";
            Map searchKey = new HashMap();
            searchKey.put("key", key);
            return productMapper.searchProduct(searchKey);
        }
        return null;

    }

    @Override
    public Long getCount() {
        return productMapper.selectCount();
    }

    public JSONObject getProds(String data) {
        JSONObject jsonObject = JSONObject.parseObject(data).getJSONObject("reqData");
        Integer page = jsonObject.getInteger("page");
        Integer size = jsonObject.getInteger("pageSize");
        String category = jsonObject.getString("category");

        Product product = new Product();
        product.setPage(page);
        product.setSize(size);
        product.setCategory(category);
        List<Product> productList = getProdList(product);

        Long count = getCount();
        Long totalPage = count / size;
        if (count % size != 0) {
            totalPage = totalPage + 1;
        }

        JSONObject prodJson = new JSONObject();
        prodJson.put("prodList", productList);
        prodJson.put("totalPage", totalPage);
        prodJson.put("page", page);

        return prodJson;
    }
}
