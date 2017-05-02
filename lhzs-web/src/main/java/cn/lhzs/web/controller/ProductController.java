package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Product;
import cn.lhzs.service.intf.ProductService;
import cn.lhzs.web.result.RequestResult;
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
        List<Product> productList = productService.getProdList(reqData);

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
}
