package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Product;
import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.ProductService;
import cn.lhzs.service.intf.ShopService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.lhzs.result.ResponseResultGenerator.generatorSuccessResult;

/**
 * Created by ZHX on 2017/4/27.
 */
@Controller
@RequestMapping("/prod")
public class ProductController {

    @Autowired
    public ProductService productService;
    @Autowired
    public ShopService shopService;

    @RequestMapping(value = "/getProduct", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getProduct(String prodId) {
        return generatorSuccessResult(productService.getProductByProdId(prodId));
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public ResponseResult getProductList(@RequestBody String reqData) {
        return generatorSuccessResult(productService.getProds(reqData));
    }

    @RequestMapping(value = "/bac/getList")
    @ResponseBody
    public ResponseResult getProductList2(@RequestBody String reqData) {
        return generatorSuccessResult(productService.getProds(reqData));
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult addProduct(@RequestBody Product product) {
        productService.addProd(product);
        return generatorSuccessResult();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseResult deleteProduct(String prodId) {
        productService.deleteProdByProdId(prodId);
        return generatorSuccessResult();
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseResult updateProduct(@RequestBody Product product) {
        productService.updateProd(product);
        return generatorSuccessResult();
    }

    @RequestMapping("/search")
    @ResponseBody
    public ResponseResult search(@RequestBody String reqData) {
        JSONObject searchResult = new JSONObject();
//        searchResult.put("shopList", shopService.searchShop(reqData));
        searchResult.put("prodList", productService.searchProduct(reqData));
        return generatorSuccessResult(searchResult);
    }

    @RequestMapping("/all/delete")
    @ResponseBody
    public ResponseResult deleteTable(@RequestBody String reqData) {
        productService.deleteTable();
        return generatorSuccessResult();
    }

    @RequestMapping("/batch/delete")
    @ResponseBody
    public ResponseResult batchDelete(@RequestBody String reqData) {
        JSONObject productJson = productService.searchProduct(reqData);
        JSONArray productArray = productJson.getJSONArray("list");
        while (productArray.size() != 0) {
            for (int i = 0; i < productArray.size(); i++) {
                JSONObject product = productArray.getJSONObject(i);
                productService.deleteProdByProdId(product.getString("prodId"));
            }
            productJson = productService.searchProduct(reqData);
            productArray = productJson.getJSONArray("list");
        }

        return generatorSuccessResult();
    }
}
