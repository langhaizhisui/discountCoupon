package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Product;
import cn.lhzs.data.dao.ProductMapper;
import cn.lhzs.service.intf.ProductService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ZHX on 2017/4/27.
 */
@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    @Resource
    public ProductMapper productMapper;

    @Override
    public List<Product> getProdList(String data) {
        if (data != null) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            Integer page = jsonObject.getInteger("page");
            Integer size = jsonObject.getInteger("pageSize");

            Product product = new Product();
            if (page == null || size == null) {
                product.setIndex(0);
                product.setSize(20);
            } else {
                product.setIndex((page - 1) * size);
                product.setSize(size);
            }
            return productMapper.selectProduct(product);
        }
        return null;
    }

    @Override
    public Product getProductByProdId(String prodId) {
        return productMapper.selectByPrimaryKey(prodId);
    }

    @Override
    public void addProd(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void deleteProdByProdId(String prodId) {
        productMapper.deleteByPrimaryKey(prodId);
    }

    @Override
    public void updateProd(Product product) {
        productMapper.updateByPrimaryKey(product);
    }
}
