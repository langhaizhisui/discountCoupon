package cn.lhzs.service.impl;


import cn.lhzs.data.dao.SysAuthMenuMapper;
import cn.lhzs.service.intf.SysAuthMenuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ZHX on 2017/10/18.
 */
@Service
public class SysAuthMenuServiceImpl implements SysAuthMenuService {

    Logger logger = Logger.getLogger(SysAuthMenuServiceImpl.class);

    @Resource
    public SysAuthMenuMapper sysAuthMenuMapper;

}
