package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Product;
import cn.lhzs.data.vo.ProductSearchCondition;
import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.ProductService;
import cn.lhzs.service.intf.ShopService;
import com.github.pagehelper.PageInfo;
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
    public ResponseResult getProduct(Long prodId) {
        return generatorSuccessResult(productService.getProductByProdId(prodId));
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public ResponseResult getProductList(@RequestBody ProductSearchCondition productSearchCondition) {
        return generatorSuccessResult(new PageInfo(productService.getProdList(productSearchCondition)));
    }

    @RequestMapping(value = "/bac/getList")
    @ResponseBody
    public ResponseResult getProductList2(@RequestBody ProductSearchCondition productSearchCondition) {
        return generatorSuccessResult(new PageInfo(productService.getProdList(productSearchCondition)));
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult addProduct(@RequestBody Product product) {
        productService.addProd(product);
        return generatorSuccessResult();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseResult deleteProduct(Long prodId) {
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
    public ResponseResult search(@RequestBody ProductSearchCondition productSearchCondition) {
        return generatorSuccessResult(new PageInfo(productService.searchProduct(productSearchCondition)));
    }

    @RequestMapping("/all/delete")
    @ResponseBody
    public ResponseResult deleteTable() {
        productService.deleteTable();
        return generatorSuccessResult();
    }

    @RequestMapping("/batch/delete")
    @ResponseBody
    public ResponseResult batchDelete(@RequestBody ProductSearchCondition productSearchCondition) {
        productService.batchDeleteProduct(productSearchCondition);
        return generatorSuccessResult();
    }
}
