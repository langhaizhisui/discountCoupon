package com.iba.drp.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iba.drp.util.StringUtil;
import com.iba.drp.util.WebUtil;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 国际化信息设置(基于SESSION)
 */
public class LocaleInterceptor extends BaseInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		// 设置客户端语言
		Locale locale = (Locale) session.getAttribute("LOCALE");
		if (locale == null) {
			String language = request.getParameter("locale");
			if (StringUtil.isNotBlank(language)) {
				locale = new Locale(language);
				session.setAttribute("LOCALE", locale);
			} else {
				locale = request.getLocale();
			}
		}
		session.setAttribute("HOST", WebUtil.getHost(request));
		LocaleContextHolder.setLocale(locale);
		return super.preHandle(request, response, handler);
	}
}
