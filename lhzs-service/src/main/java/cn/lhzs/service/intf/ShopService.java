package cn.lhzs.service.intf;

import cn.lhzs.data.bean.Shop;

import java.util.List;

/**
 * Created by ZHX on 2017/4/27.
 */
public interface ShopService {

    List<Shop> getShopList(String data);

    Shop getShopByShopId(String shopId);

    void addShop(Shop shop);

    void deleteShopByProdId(String shopId);

    void updateShop(Shop shop);
}
