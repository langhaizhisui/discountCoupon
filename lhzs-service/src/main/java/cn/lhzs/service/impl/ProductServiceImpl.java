package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Product;
import cn.lhzs.data.dao.ProductMapper;
import cn.lhzs.service.intf.ProductService;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZHX on 2017/4/27.
 */
@Service
public class ProductServiceImpl implements ProductService {

    Logger logger = Logger.getLogger(ProductServiceImpl.class);

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
        product.setScanNum(0);
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        productMapper.insert(product);
    }

    @Override
    public void addBatchProd(List<Product> products) {
        productMapper.batchInsert(products);
    }


    @Override
    public void deleteProdByProdId(String prodId) {
        productMapper.deleteByPrimaryKey(Long.parseLong(prodId));
    }

    @Override
    public void updateProd(Product product) {
        product.setUpdateTime(new Date());
        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public JSONObject searchProduct(String data) {
        if (data != null) {
            JSONObject jsonObject = JSONObject.parseObject(data).getJSONObject("reqData");
            Integer page = jsonObject.getInteger("page");
            Integer size = jsonObject.getInteger("size");
            String key = jsonObject.getString("key");
            Map searchKey = new HashMap();

            page = page == null ? 0 : page;
            size = size == null ? 20 : size;

            if (key != null) {
                key = "%" + key + "%";
                searchKey.put("key", key);
            } else {
                Integer prodId = jsonObject.getInteger("prodId");
                String category = jsonObject.getString("category");
                String platform = jsonObject.getString("platform");
                String expirationStart = jsonObject.getString("expirationStart");
                String expirationEnd = jsonObject.getString("expirationEnd");
                String createStart = jsonObject.getString("createTimeStart");
                String createEnd = jsonObject.getString("createTimeEnd");

                if (prodId != null && !"".equals(prodId)) {
                    searchKey.put("prodId", prodId);
                }
                if (category != null && !"".equals(category)) {
                    searchKey.put("category", category);
                }
                if (platform != null && !"".equals(platform)) {
                    searchKey.put("platform", platform);
                }
                if (expirationStart != null && !"".equals(expirationStart)) {
                    searchKey.put("expirationStart", expirationStart);
                    searchKey.put("expirationEnd", expirationEnd);
                }
                if (createStart != null && !"".equals(createStart)) {
                    searchKey.put("createStart", createStart + " 00:00:00");
                    searchKey.put("createEnd", createEnd + " 00:00:00");
                }

                searchKey.put("index", page == 0 ? 0 : (page - 1) * size);
                searchKey.put("size", size);
            }

            List<Product> productList = productMapper.searchProduct(searchKey);

            Long count = productMapper.selectCount(searchKey);
            Long totalPage = count / size;
            if (count % size != 0) {
                totalPage = totalPage + 1;
            }

            JSONObject prodJson = new JSONObject();
            prodJson.put("list", productList);
            prodJson.put("totalPage", totalPage);
            prodJson.put("page", page);

            return prodJson;
        }
        return null;

    }

    @Override
    public Long getCount() {
        return null;
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

        Map countMap = new HashMap();
        if(category!=null && !"".equals(category)){
            countMap.put("category",category);
        }
        Long count = productMapper.selectCount(countMap);
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

    @Override
    public void deleteTable() {
        productMapper.deleteTable();
    }
}
