package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Shop;
import cn.lhzs.service.intf.ShopService;
import cn.lhzs.web.result.RequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ZHX on 2017/4/27.
 */
@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    public ShopService shopService;

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
}
