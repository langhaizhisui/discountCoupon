package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Shop;
import cn.lhzs.data.dao.ShopMapper;
import cn.lhzs.service.intf.ShopService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ZHX on 2017/4/27.
 */
@Service
@Transactional(readOnly = true)
public class ShopServiceImpl implements ShopService {

    @Resource
    public ShopMapper shopMapper;

    @Override
    public List<Shop> getShopList(String data) {
        if (data != null) {
            JSONObject jsonObject = JSONObject.parseObject(data).getJSONObject("reqData");
            String type=jsonObject.getString("type");
            Integer page = jsonObject.getInteger("page");
            Integer size = jsonObject.getInteger("pageSize");

            Shop shop = new Shop();
            if (page != null && size != null) {
                shop.setIndex((page - 1) * size);
                shop.setSize(size);
                if(type!=null){
                    shop.setType(type);
                }
                return shopMapper.selectShop(shop);
            }
        }
        return null;
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
