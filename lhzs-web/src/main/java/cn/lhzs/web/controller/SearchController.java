package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Product;
import cn.lhzs.data.bean.Shop;
import cn.lhzs.data.vo.ProductSearchCondition;
import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.ProductService;
import cn.lhzs.service.intf.SearchService;
import cn.lhzs.service.intf.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static cn.lhzs.result.ResponseResultGenerator.generatorSuccessResult;

/**
 * Created by IBA-EDV on 2017/11/23.
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopService shopService;

    @RequestMapping("build/index")
    @ResponseBody
    public ResponseResult buildSearchIndex() {
        searchService.builderSearchIndex(productService.getAllProduct(),"products","prod");
        return generatorSuccessResult();
    }

    @RequestMapping("product")
    @ResponseBody
    public ResponseResult searchProductList(@RequestBody Product product) {
        return generatorSuccessResult(searchService.searchProductList(product.getName()));
    }

    @RequestMapping("shop")
    @ResponseBody
    public ResponseResult searchShopList(@RequestBody Shop shop) {
        return generatorSuccessResult(searchService.searchProductList(shop.getWebShop()));
    }
}
