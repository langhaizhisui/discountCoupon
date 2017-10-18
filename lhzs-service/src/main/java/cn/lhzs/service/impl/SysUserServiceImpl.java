package cn.lhzs.service.impl;

import cn.lhzs.data.dao.SysUserMapper;
import cn.lhzs.service.intf.SysAuthService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ZHX on 2017/10/18.
 */
@Service
public class SysUserServiceImpl implements SysAuthService {

    Logger logger = Logger.getLogger(SysUserServiceImpl.class);

    @Resource
    public SysUserMapper sysUserMapper;

}
