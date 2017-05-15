package cn.lhzs.service.intf;

import cn.lhzs.data.bean.Shop;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by ZHX on 2017/4/27.
 */
public interface ShopService {

    List<Shop> getShopList(Shop shop);

    Shop getShopByShopId(String shopId);

    void addShop(Shop shop);

    void deleteShopByProdId(String shopId);

    void updateShop(Shop shop);

    List<Shop> searchShop(String data);

    Integer selectCountByTypeSite(String type, String site);

    JSONObject getShops(Shop shop);
}
