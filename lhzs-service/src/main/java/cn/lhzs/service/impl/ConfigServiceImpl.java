package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Config;
import cn.lhzs.service.intf.ConfigService;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2017/5/7.
 */
public class ConfigServiceImpl implements ConfigService{

    Logger logger = Logger.getLogger(ConfigServiceImpl.class);

    @Override
    public Config getConfigByConfId(String confId) {
        return null;
    }
}
