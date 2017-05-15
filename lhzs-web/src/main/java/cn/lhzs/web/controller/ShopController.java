package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Shop;
import cn.lhzs.service.intf.ShopService;
import cn.lhzs.web.result.RequestResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public RequestResult getShopList(@RequestBody String reqData) {
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

        JSONObject shopList = shopService.getShops(shop);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取网店成功");
        result.setData(shopList);
        return result;
    }

    @RequestMapping("/getShop")
    @ResponseBody
    public RequestResult getShop(String shopId) {

        Shop shop = shopService.getShopByShopId(shopId);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取网店成功");
        result.setData(shop);
        return result;
    }

    @RequestMapping("/add")
    @ResponseBody
    public RequestResult addShop(@RequestBody Shop shop) {
        shopService.addShop(shop);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("增加网店成功");
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public RequestResult deleteShop(String shopId) {
        shopService.deleteShopByProdId(shopId);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("删除网店成功");
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public RequestResult updateShop(@RequestBody Shop shop) {
        shopService.updateShop(shop);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("修改网店成功");
        return result;
    }

    @RequestMapping("/search")
    @ResponseBody
    public RequestResult searchShop(@RequestBody String reqData) {
        List<Shop> shopList = shopService.searchShop(reqData);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("搜索成功");
        result.setData(shopList);
        return result;
    }

    @RequestMapping("/getSiteList")
    @ResponseBody
    public RequestResult getShopListBySite(@RequestBody String reqData) {
        JSONObject jsonObject = JSONObject.parseObject(reqData).getJSONObject("reqData");
        String site = jsonObject.getString("site");
        String type = jsonObject.getString("type");

        Shop shop = new Shop();
        shop.setSite(site);
        shop.setType(type);

        List<Shop> shopList = shopService.getShopList(shop);

        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取网店成功");
        result.setData(shopList);
        return result;
    }

}
