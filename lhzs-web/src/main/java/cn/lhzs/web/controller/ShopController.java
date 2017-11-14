package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Shop;
import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.ShopService;
import cn.lhzs.result.RequestResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static cn.lhzs.result.ResponseResultGenerator.generatorFailResult;
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
    public ResponseResult getShopList(@RequestBody String reqData) {
        JSONObject jsonObject = JSONObject.parseObject(reqData).getJSONObject("reqData");
        String type = jsonObject.getString("type");
        Integer page = jsonObject.getInteger("page");
        Integer size = jsonObject.getInteger("pageSize");
        String site = jsonObject.getString("site");

        Shop shop = new Shop();
        shop.setSize(size);
        shop.setType(type);
        shop.setPage(page);
        shop.setIndex((page - 1) * size);
        if (site != null) {
            shop.setSite(site);
        }

        return generatorSuccessResult(shopService.getShops(shop));
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
        try {
            shopService.deleteShopByShopId(shopId);
            return generatorSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return generatorFailResult("删除失败");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseResult updateShop(@RequestBody Shop shop) {
        shopService.updateShop(shop);
        return generatorSuccessResult();
    }

    @RequestMapping("/search")
    @ResponseBody
    public ResponseResult searchShop(@RequestBody String reqData) {
        return generatorSuccessResult(shopService.searchShop(reqData));
    }

    @RequestMapping("/getSiteList")
    @ResponseBody
    public ResponseResult getShopListBySite(@RequestBody String reqData) {
        JSONObject jsonObject = JSONObject.parseObject(reqData).getJSONObject("reqData");

        Shop shop = new Shop();
        shop.setSite(jsonObject.getString("site"));
        shop.setType(jsonObject.getString("type"));

        return generatorSuccessResult(shopService.getShopList(shop));
    }

    @RequestMapping("/all/delete")
    @ResponseBody
    public ResponseResult deleteTable(@RequestBody Shop shop) {
        shopService.deleteTable();
        return generatorSuccessResult();
    }

    @RequestMapping("/batch/delete")
    @ResponseBody
    public ResponseResult batchDelete(@RequestBody String reqData) {

        JSONObject shopJson = shopService.searchShop(reqData);
        JSONArray shopArray = shopJson.getJSONArray("list");
        while (shopArray.size() != 0) {
            for (int i = 0; i < shopArray.size(); i++) {
                JSONObject shop = shopArray.getJSONObject(i);
                shopService.deleteShopByShopId(Long.parseLong(shop.getString("id")));
            }
            shopJson = shopService.searchShop(reqData);
            shopArray = shopJson.getJSONArray("list");
        }

        return generatorSuccessResult();
    }
}
