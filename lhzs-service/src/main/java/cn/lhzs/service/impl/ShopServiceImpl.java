package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Shop;
import cn.lhzs.data.dao.ShopMapper;
import cn.lhzs.service.intf.ShopService;
import com.alibaba.fastjson.JSONObject;
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
@Transactional(readOnly = true)
public class ShopServiceImpl implements ShopService {

    @Resource
    public ShopMapper shopMapper;

    @Override
    public List<Shop> getShopList(Shop shop) {
        Integer page = shop.getPage();
        Integer size = shop.getSize();
        String type = shop.getType();
        if (page != null && size != null) {
            shop.setIndex((page - 1) * size);
            shop.setSize(size);
            if (type != null) {
                shop.setType(type);
            }
            return shopMapper.selectShop(shop);
        }
        return null;
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
    public Integer selectCountByType(String type) {
        Shop shop=new Shop();
        shop.setType(type);
        return shopMapper.selectCountByType(shop);
    }

    @Override
    public JSONObject getShops(String data) {
        JSONObject jsonObject = JSONObject.parseObject(data).getJSONObject("reqData");
        String type = jsonObject.getString("type");
        Integer page = jsonObject.getInteger("page");
        Integer size = jsonObject.getInteger("pageSize");

        Shop shop = new Shop();
        shop.setPage(page);
        shop.setSize(size);
        shop.setType(type);
        List<Shop> shopList = getShopList(shop);

        Integer count = selectCountByType(type);
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
    public Shop getShopByShopId(String shopId) {
        return shopMapper.selectByPrimaryKey(shopId);
    }

    @Override
    public void addShop(Shop shop) {
        shopMapper.insert(shop);
    }

    @Override
    public void deleteShopByProdId(String shopId) {
        shopMapper.deleteByPrimaryKey(shopId);
    }

    @Override
    public void updateShop(Shop shop) {
        shopMapper.updateByPrimaryKey(shop);
    }


}
