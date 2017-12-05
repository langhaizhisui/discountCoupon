package cn.lhzs.web.controller.admin;

import cn.lhzs.common.util.WebUtil;
import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.SysUserService;
import cn.lhzs.web.shiro.ShiroPermissionManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.lhzs.result.ResponseResultGenerator.generatorFailResult;
import static cn.lhzs.result.ResponseResultGenerator.generatorSuccessResult;

/**
 * Created by IBA-EDV on 2017/12/1.
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private ShiroPermissionManage shiroPermissionManage;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("perm/update")
    @ResponseBody
    public ResponseResult updatePermission(){
        if(shiroPermissionManage.updateFilter()){
            return generatorSuccessResult();
        }
        return generatorFailResult("权限更新失败");
    }

    @RequestMapping("menu")
    @ResponseBody
    public ResponseResult getMenu(){
        return generatorSuccessResult(sysUserService.getMenu());
    }
}
