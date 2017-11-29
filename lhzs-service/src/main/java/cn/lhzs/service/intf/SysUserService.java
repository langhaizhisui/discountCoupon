package cn.lhzs.service.intf;

import cn.lhzs.base.IBaseService;
import cn.lhzs.data.bean.SysUser;

import java.util.List;

/**
 * Created by ZHX on 2017/10/18.
 */
public interface SysUserService extends IBaseService<SysUser> {
    List<SysUser> getUserAuthList(Long uid);
}
