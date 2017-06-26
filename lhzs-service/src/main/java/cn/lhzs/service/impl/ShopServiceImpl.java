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

    public JSONObject searchShop(String data) {
        if (data != null) {
            JSONObject jsonObject = JSONObject.parseObject(data).getJSONObject("reqData");
            Integer page = jsonObject.getInteger("page");
            Integer size = jsonObject.getInteger("size");
            String key = jsonObject.getString("key");


            Map searchKey = new HashMap();

            page = page == null ? 0 : page;
            size = size == null ? 20 : size;

            if (key != null) {
                key = "%" + key + "%";
                searchKey.put("key", key);
            }else{
                Integer id = jsonObject.getInteger("id");
                String webShop = jsonObject.getString("webShop");
                String site = jsonObject.getString("site");
                String type = jsonObject.getString("type");
                String createStart = jsonObject.getString("createTimeStart");
                String createEnd = jsonObject.getString("createTimeEnd");

                if (id != null && !"".equals(id)) {
                    searchKey.put("id", id);
                }
                if (webShop != null && !"".equals(webShop)) {
                    searchKey.put("webShop", webShop);
                }
                if (site != null && !"".equals(site)) {
                    searchKey.put("site", site);
                }
                if (type != null && !"".equals(type)) {
                    searchKey.put("type", type);
                }
                if (createStart != null && !"".equals(createStart)) {
                    searchKey.put("createStart", createStart + " 00:00:00");
                    searchKey.put("createEnd", createEnd + " 00:00:00");
                }

                searchKey.put("index", page == 0 ? 0 : (page - 1) * size);
                searchKey.put("size", size);
            }

            List<Shop> shopList = shopMapper.searchShop(searchKey);

            Long count = shopMapper.selectCount(searchKey);
            Long totalPage = count / size;
            if (count % size != 0) {
                totalPage = totalPage + 1;
            }

            JSONObject prodJson = new JSONObject();
            prodJson.put("list", shopList);
            prodJson.put("totalPage", totalPage);
            prodJson.put("page", page);

            return prodJson;
        }
        return null;
    }

    @Override
    public Long selectCountByTypeSite(String type, String site) {
        Map shop =new HashMap();
        shop.put("type",type);
        if (site != null) {
            shop.put("site",site);
        }
        return shopMapper.selectCount(shop);
    }

    @Override
    public JSONObject getShops(Shop shop) {

        List<Shop> shopList = getShopList(shop);

        Integer size = shop.getSize();
        Integer page = shop.getPage();
        Long count = selectCountByTypeSite(shop.getType(), shop.getSite());
        Long totalPage = count / size;
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
