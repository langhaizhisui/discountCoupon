package cn.lhzs.service.impl;

import cn.lhzs.base.AbstractBaseService;
import cn.lhzs.data.bean.Shop;
import cn.lhzs.data.dao.ShopMapper;
import cn.lhzs.data.vo.ShopSearchCondition;
import cn.lhzs.service.intf.ShopService;
import cn.lhzs.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

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
        return shopMapper.select(shop);
    }

    @Override
    public List<Shop> searchShop(ShopSearchCondition shopSearchCondition) {
        startPage(shopSearchCondition.getPage(), shopSearchCondition.getSize());
        return findByCondition(getShopSearchExample(shopSearchCondition));
    }

    private Example getShopSearchExample(ShopSearchCondition shopSearchCondition) {
        Example example = new Example(Shop.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmptyString(shopSearchCondition.getShopId() + "")) {
            criteria.andEqualTo("id", shopSearchCondition.getShopId());
        }
        if (StringUtil.isNotEmptyString(shopSearchCondition.getShopName())) {
            criteria.andEqualTo("webShop", shopSearchCondition.getShopName());
        }
        if (StringUtil.isNotEmptyString(shopSearchCondition.getSite())) {
            criteria.andEqualTo("site", shopSearchCondition.getSite());
        }
        if (StringUtil.isNotEmptyString(shopSearchCondition.getType())) {
            criteria.andEqualTo("type", shopSearchCondition.getType());
        }
        if (shopSearchCondition.getCreateTimeStart() != null && shopSearchCondition.getCreateTimeEnd()!=null) {
            criteria.andGreaterThanOrEqualTo("createTime",shopSearchCondition.getCreateTimeStart())
                    .orLessThanOrEqualTo("createTime",shopSearchCondition.getCreateTimeEnd());
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
    public List<Shop> getAllShop() {
        return shopMapper.selectAll();
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
