package cn.lhzs.web.shiro;

import cn.lhzs.data.bean.SysUser;
import cn.lhzs.service.intf.SysUserService;
import cn.lhzs.util.StringUtil;
import cn.lhzs.web.exception.LoginException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser sysUser = sysUserService.findBy("account", super.getAvailablePrincipal(principals));
        if (null == sysUser) {
            throw new LoginException("用户名或密码错误");
        }

        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        if (getSessionUserAuth(simpleAuthorInfo, subject)) {
            return simpleAuthorInfo;
        }
        return getUserAuth(simpleAuthorInfo, subject, sysUser);
    }

    private AuthorizationInfo getUserAuth(SimpleAuthorizationInfo simpleAuthorInfo, Subject subject, SysUser sysUser) {
        sysUserService.getUserAuthList(sysUser.getId()).forEach(userAuth -> simpleAuthorInfo.addStringPermission(userAuth.getName()));
        subject.getSession().setAttribute("userAuth", setUserAuthName(sysUser.getId()));
        return simpleAuthorInfo;
    }

    private boolean getSessionUserAuth(SimpleAuthorizationInfo simpleAuthorInfo, Subject subject) {
        if (null != subject) {
            String userAuth = (String) subject.getSession().getAttribute("userAuth");
            if (StringUtil.isNotEmptyString(userAuth)) {
                Arrays.asList(userAuth.split(",")).forEach(auth -> simpleAuthorInfo.addStringPermission(auth));
                return true;
            }
        }
        return false;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        SysUser sysUser = sysUserService.findBy("account", token.getUsername());
        if (!checkUser(sysUser, token)) {
            throw new LoginException("用户名或密码错误");
        }
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
        setAuthenticationSession(sysUser.getId());
        return authcInfo;
    }

    private boolean checkUser(SysUser sysUser, UsernamePasswordToken token) {
        String password = getTokenPassword(token.getPassword());
        if (sysUser != null && StringUtil.isNotEmptyString(password) && password.equals(sysUser.getPassword())) {
            return true;
        }
        return false;
    }

    private String getTokenPassword(char[] password) {
        StringBuilder builder = new StringBuilder();
        if (password != null) {
            for (int i = 0; i < password.length; i++) {
                builder.append(password[i]);
            }
        }
        return builder.toString();
    }

    /**
     * 将一些数据放到ShiroSession中，以便于其它地方使用
     * 比如Controller里面，使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setAuthenticationSession(Long uid) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            session.setTimeout(1000 * 60 * 60 * 2);
            session.setAttribute("user", uid);
            session.setAttribute("userAuth", setUserAuthName(uid));
        }
    }

    private String setUserAuthName(Long uid) {
        String authName = "";
        List<SysUser> userAuthList = sysUserService.getUserAuthList(uid);
        for (int i = 0; i < userAuthList.size(); i++) {
            authName += userAuthList.get(i).getName() + ",";
        }
        return "".equals(authName) ? "" : authName.substring(0, authName.length() - 1);
    }

}