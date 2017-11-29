package cn.lhzs.service.impl;

import cn.lhzs.base.AbstractBaseService;
import cn.lhzs.data.bean.SysUser;
import cn.lhzs.data.dao.SysUserMapper;
import cn.lhzs.service.intf.SysUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ZHX on 2017/10/18.
 */
@Service
public class SysUserServiceImpl extends AbstractBaseService<SysUser> implements SysUserService {

    Logger logger = Logger.getLogger(SysUserServiceImpl.class);

    @Resource
    public SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getUserAuthList(Long uid) {
        return sysUserMapper.getUserAuth(uid);
    }


}
