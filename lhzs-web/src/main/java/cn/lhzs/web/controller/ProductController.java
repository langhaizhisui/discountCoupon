package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Product;
import cn.lhzs.service.intf.ProductService;
import cn.lhzs.service.intf.ShopService;
import cn.lhzs.result.RequestResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ZHX on 2017/4/27.
 */
@Controller
@RequestMapping("/prod")
public class ProductController {

    Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    public ProductService productService;
    @Autowired
    public ShopService shopService;

    @RequestMapping(value = "/getProduct", method = RequestMethod.GET)
    @ResponseBody
    public RequestResult getProduct(String prodId) {
        Product product = productService.getProductByProdId(prodId);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取商品成功");
        result.setData(product);
        return result;
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public RequestResult getProductList(@RequestBody String reqData) {
        JSONObject productList = productService.getProds(reqData);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取列表成功");
        result.setData(productList);
        return result;
    }

    @RequestMapping(value = "/bac/getList")
    @ResponseBody
    public RequestResult getProductList2(@RequestBody String reqData) {
        JSONObject productList = productService.getProds(reqData);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取列表成功");
        result.setData(productList);
        return result;
    }

    @RequestMapping("/add")
    @ResponseBody
    public RequestResult addProduct(@RequestBody Product product) {
        productService.addProd(product);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取商品成功");
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public RequestResult deleteProduct(String prodId) {
        productService.deleteProdByProdId(prodId);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("删除商品成功");
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public RequestResult updateProduct(@RequestBody Product product) {
        productService.updateProd(product);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("更新商品成功");
        return result;
    }

    @RequestMapping("/search")
    @ResponseBody
    public RequestResult search(@RequestBody String reqData) {
        JSONObject searchResult = new JSONObject();
        JSONObject shopList = shopService.searchShop(reqData);
        searchResult.put("shopList", shopList);
        JSONObject prodList = productService.searchProduct(reqData);
        searchResult.put("prodList", prodList);
        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("搜索成功");
        result.setData(searchResult);
        return result;
    }

    @RequestMapping("/all/delete")
    @ResponseBody
    public RequestResult deleteTable(@RequestBody String reqData) {
        productService.deleteTable();

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("删除所有商品成功");
        return result;
    }

    @RequestMapping("/batch/delete")
    @ResponseBody
    public RequestResult batchDelete(@RequestBody String reqData) {

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

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("批量删除成功");
        return result;
    }
}
