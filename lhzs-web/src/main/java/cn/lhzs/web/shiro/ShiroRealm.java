package cn.lhzs.web.shiro;

import cn.lhzs.data.bean.SysUser;
import cn.lhzs.service.intf.SysAuthMenuService;
import cn.lhzs.service.intf.SysAuthService;
import cn.lhzs.service.intf.SysRoleAuthService;
import cn.lhzs.service.intf.SysRoleService;
import cn.lhzs.service.intf.SysRoleUserService;
import cn.lhzs.service.intf.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleAuthService sysRoleAuthService;
    @Autowired
    private SysAuthService sysAuthService;
    @Autowired
    private SysAuthMenuService sysAuthMenuService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser sysUser = sysUserService.findBy("account", super.getAvailablePrincipal(principals));
        if (null == sysUser) {
            throw new AuthorizationException();
        }

        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        simpleAuthorInfo.addStringPermission("admin:manage");
        return simpleAuthorInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        if ("biibuy".equals(token.getUsername())) {
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("biibuy", "biibuy", this.getName());
            this.setAuthenticationSession("biibuy");
            return authcInfo;
        }
        if ("zhan".equals(token.getUsername())) {
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("zhan", "zhan", this.getName());
            this.setAuthenticationSession("zhan");
            return authcInfo;
        }
        return null;
    }

    /**
     * 将一些数据放到ShiroSession中，以便于其它地方使用
     * 比如Controller里面，使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setAuthenticationSession(Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            session.setTimeout(1000 * 60 * 60 * 2);
            session.setAttribute("currentUser", value);
        }
    }
}