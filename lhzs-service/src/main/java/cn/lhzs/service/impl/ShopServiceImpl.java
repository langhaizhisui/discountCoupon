package cn.lhzs.service.impl;

import cn.lhzs.base.AbstractBaseService;
import cn.lhzs.data.bean.Shop;
import cn.lhzs.data.dao.ShopMapper;
import cn.lhzs.service.intf.ShopService;
import cn.lhzs.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.pagehelper.PageHelper.startPage;

/**
 * Created by ZHX on 2017/4/27.
 */
@Service
public class ShopServiceImpl extends AbstractBaseService<Shop> implements ShopService {

    Logger logger = Logger.getLogger(ShopServiceImpl.class);

    @Resource
    public ShopMapper shopMapper;

    @Override
    public List<Shop> getShopList(Shop shop) {
        Example example = new Example(Shop.class);
        Example.Criteria criteria = example.createCriteria();
        return findByCondition(example);
    }

    @Override
    public List<Shop> searchShop(Shop shop) {
        startPage(shop.getPage(), shop.getSize());
        return findByCondition(getShopSearchExample(shop));
    }

    private Example getShopSearchExample(Shop shop) {
        Example example = new Example(Shop.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmptyString(shop.getId() + "")) {
            criteria.andEqualTo("id", shop.getId());
        }
        if (StringUtil.isNotEmptyString(shop.getWebShop())) {
            criteria.andEqualTo("webShop", shop.getWebShop());
        }
        if (StringUtil.isNotEmptyString(shop.getSite())) {
            criteria.andEqualTo("site", shop.getSite());
        }
        if (StringUtil.isNotEmptyString(shop.getType())) {
            criteria.andEqualTo("type", shop.getType());
        }
        if (shop.getCreateTime() != null) {

        }
        return example;
    }

    @Override
    public List<Shop> getShops(Shop shop) {
        return findByCondition(getShopsExample(shop));
    }

    private Example getShopsExample(Shop shop) {
        Example example = new Example(Shop.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmptyString(shop.getType())) {
            criteria.andEqualTo("type", shop.getType());
        }
        if (StringUtil.isNotEmptyString(shop.getSite())) {
            criteria.andEqualTo("site", shop.getSite());
        }
        return example;
    }

    @Override
    public void deleteTable() {
        shopMapper.deleteTable();
    }

    @Override
    public Shop getShopByShopId(Long shopId) {
        return findById(shopId);
    }

    @Override
    public void addBatchShop(List<Shop> shops) {
//        shopMapper.batchInsert(shops);
    }

    @Override
    public void addShop(Shop shop) {
        save(shop);
    }

    @Override
    public void deleteShopByShopId(Long shopId) {
        deleteById(shopId);
    }

    @Override
    public void updateShop(Shop shop) {
        save(shop);
    }

}
