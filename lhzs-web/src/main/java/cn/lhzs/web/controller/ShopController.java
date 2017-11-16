package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Shop;
import cn.lhzs.data.vo.ShopSearchCondition;
import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.ShopService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static cn.lhzs.result.ResponseResultGenerator.generatorSuccessResult;

/**
 * Created by ZHX on 2017/4/27.
 */
@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    public ShopService shopService;

    @RequestMapping("/getList")
    @ResponseBody
    public ResponseResult getShopList(@RequestBody Shop shop) {
        return generatorSuccessResult(new PageInfo(shopService.getShops(shop)));
    }

    @RequestMapping("/getShop")
    @ResponseBody
    public ResponseResult getShop(Long shopId) {
        return generatorSuccessResult(shopService.getShopByShopId(shopId));
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult addShop(@RequestBody Shop shop) {
        shopService.addShop(shop);
        return generatorSuccessResult();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseResult deleteShop(Long shopId) {
        shopService.deleteShopByShopId(shopId);
        return generatorSuccessResult();
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseResult updateShop(@RequestBody Shop shop) {
        shopService.updateShop(shop);
        return generatorSuccessResult();
    }

    @RequestMapping("/search")
    @ResponseBody
    public ResponseResult searchShop(@RequestBody ShopSearchCondition shopSearchCondition) {
        return generatorSuccessResult(new PageInfo(shopService.searchShop(shopSearchCondition)));
    }

    @RequestMapping("/getSiteList")
    @ResponseBody
    public ResponseResult getShopListBySite(@RequestBody Shop shop) {
        return generatorSuccessResult(shopService.getShopList(shop));
    }

    @RequestMapping("/all/delete")
    @ResponseBody
    public ResponseResult deleteTable() {
        shopService.deleteTable();
        return generatorSuccessResult();
    }

    @RequestMapping("/batch/delete")
    @ResponseBody
    public ResponseResult batchDelete(@RequestBody ShopSearchCondition shopSearchCondition) {
        shopService.searchShop(shopSearchCondition).forEach(shop -> shopService.deleteShopByShopId(shop.getId()));
        return generatorSuccessResult();
    }
}
