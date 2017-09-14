package com.iba.drp.support.login;

import com.iba.drp.exception.LoginException;
import com.iba.drp.config.Resources;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * shiro登录
 */
public final class LoginHelper {
	private LoginHelper() {
	}

	/** 用户登录 */
	public static final Boolean login(String account, String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(account, password);
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			 return subject.isAuthenticated();
		} catch (UnknownAccountException e) {
			throw new LoginException(Resources.getMessage("ACCOUNT_UNKNOWN", token.getPrincipal()));
		} catch (DisabledAccountException e) {
			throw new LoginException(Resources.getMessage("ACCOUNT_DISABLE", token.getPrincipal()));
		} catch (LoginException e) {
			throw new LoginException(Resources.getMessage("ACCOUNT_PWDERROR", token.getPrincipal()));
		} catch (Exception e) {
			throw new LoginException(Resources.getMessage("LOGIN_FAIL"), e);
		}
	}
}
