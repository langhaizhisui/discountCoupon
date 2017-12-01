package cn.lhzs.web.controller;

import cn.lhzs.result.ResponseResult;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ShiroPermissionManage shiroPermissionManage;

    @RequestMapping("perm/update")
    @ResponseBody
    public ResponseResult updatePermission(){
        if(shiroPermissionManage.updateFilter()){
            return generatorSuccessResult();
        }
        return generatorFailResult("权限更新失败");
    }
}
