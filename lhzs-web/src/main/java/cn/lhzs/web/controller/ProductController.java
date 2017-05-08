package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Product;
import cn.lhzs.data.bean.Shop;
import cn.lhzs.service.intf.ProductService;
import cn.lhzs.service.intf.ShopService;
import cn.lhzs.web.result.RequestResult;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping("/search")
    @ResponseBody
    public RequestResult search(@RequestBody String reqData) {
        JSONObject searchJson = new JSONObject();
        if (reqData.indexOf("旗舰店")!=-1 || reqData.indexOf("专卖店")!=-1 || reqData.indexOf("自营店")!=-1) {
            List<Shop> shopList = shopService.searchShop(reqData);
            searchJson.put("type", "1");
            searchJson.put("list",shopList);
        } else {
            List<Product> productList = productService.searchProduct(reqData);
            searchJson.put("type", "2");
            searchJson.put("list",productList);
        }
        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("搜索成功");
        result.setData(searchJson);
        return result;
    }
}
