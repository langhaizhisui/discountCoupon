package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Shop;
import cn.lhzs.data.dao.ShopMapper;
import cn.lhzs.service.intf.ShopService;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZHX on 2017/4/27.
 */
@Service
public class ShopServiceImpl implements ShopService {

    Logger logger = Logger.getLogger(ShopServiceImpl.class);

    @Resource
    public ShopMapper shopMapper;

    @Override
    public List<Shop> getShopList(Shop shop) {
        return shopMapper.selectShop(shop);
    }

    public List<Shop> searchShop(String data) {
        if (data != null) {
            JSONObject jsonObject = JSONObject.parseObject(data).getJSONObject("reqData");
            String key = jsonObject.getString("key");
            key = "%" + key + "%";

            Map keyMap = new HashMap();
            keyMap.put("key", key);
            return shopMapper.searchShop(keyMap);
        }
        return null;
    }

    @Override
    public Integer selectCountByTypeSite(String type, String site) {
        Shop shop = new Shop();
        shop.setType(type);
        if (site != null) {
            shop.setSite(site);
        }
        return shopMapper.selectCountByType(shop);
    }

    @Override
    public JSONObject getShops(Shop shop) {

        List<Shop> shopList = getShopList(shop);

        Integer size = shop.getSize();
        Integer page = shop.getPage();
        Integer count = selectCountByTypeSite(shop.getType(), shop.getSite());
        Integer totalPage = count / size;
        if (count % size != 0) {
            totalPage += 1;
        }

        JSONObject shopJson = new JSONObject();
        shopJson.put("shopList", shopList);
        shopJson.put("totalPage", totalPage);
        shopJson.put("page", page);

        return shopJson;
    }

    @Override
    public void deleteTable() {
        shopMapper.deleteTable();
    }

    @Override
    public Shop getShopByShopId(Long shopId) {
        return shopMapper.selectByPrimaryKey(shopId);
    }

    @Override
    public void addShop(Shop shop) {
        shopMapper.insert(shop);
    }

    @Override
    public void deleteShopByShopId(Long shopId) {
        shopMapper.deleteByPrimaryKey(shopId);
    }

    @Override
    public void updateShop(Shop shop) {
        shopMapper.updateByPrimaryKey(shop);
    }

}
