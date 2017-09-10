package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Config;
import cn.lhzs.data.common.Constants;
import cn.lhzs.data.dao.ConfigMapper;
import cn.lhzs.service.intf.ConfigService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by ZHX on 2017/5/7.
 */
@Service
public class ConfigServiceImpl implements ConfigService{

    @Resource
    public ConfigMapper configMapper;

    Logger logger = Logger.getLogger(ConfigServiceImpl.class);

    @Override
    public Config getConfigByConfId(Long confId) {
        return configMapper.selectByPrimaryKey(confId);
    }
}
