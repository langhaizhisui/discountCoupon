package cn.lhzs.web.controller;

import cn.lhzs.data.vo.LoginCondition;
import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.ArticleService;
import cn.lhzs.web.exception.LoginException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static cn.lhzs.result.ResponseResultGenerator.generatorLoginResult;
import static cn.lhzs.result.ResponseResultGenerator.generatorSuccessResult;
import static cn.lhzs.result.ResponseResultGenerator.generatorUnLoginResult;

/**
 * Created by ZHX on 2017/5/7.
 */
@Controller
public class LoginController {

    @Autowired
    public ArticleService articleService;

    @RequestMapping("/login")
    @ResponseBody
    public ResponseResult login(@RequestBody LoginCondition loginCondition) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginCondition.getUserName(), loginCondition.getPassword());
        try {
            token.setRememberMe(true);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
            if (!currentUser.isAuthenticated()) {
                token.clear();
            }
            return generatorLoginResult();
        } catch (UnknownAccountException e) {
            throw new LoginException("未知账户" + token.getPrincipal());
        } catch (IncorrectCredentialsException e) {
            throw new LoginException("凭证错误" + token.getPrincipal());
        } catch (LockedAccountException e) {
            throw new LoginException("账户已锁定" + token.getPrincipal());
        } catch (ExcessiveAttemptsException e) {
            throw new LoginException("错误次数过多" + token.getPrincipal());
        } catch (AuthenticationException e) {
            throw new LoginException("登录错误" + token.getPrincipal());
        }
    }

    @RequestMapping("/unLogin")
    @ResponseBody
    public ResponseResult unLogin() {
        return generatorUnLoginResult();
    }

    @RequestMapping("/forbidden")
    @ResponseBody
    public ResponseResult forbidden() {
        return generatorUnLoginResult();
    }
}
