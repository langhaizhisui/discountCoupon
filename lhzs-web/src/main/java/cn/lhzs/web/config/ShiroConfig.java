package cn.lhzs.web.config;

import cn.lhzs.web.shiro.ShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @descption shiro配置
 * @author ZHX
 */
@Configuration
public class ShiroConfig {

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public ShiroRealm shiroRealm() {
		return new ShiroRealm();
	}

	@Bean
	public SecurityManager securityManager(ShiroRealm realm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		return securityManager;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		return constructShiroFilterFactoryBean(securityManager);
	}

	private ShiroFilterFactoryBean constructShiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/unLogin");
		shiroFilterFactoryBean.setUnauthorizedUrl("/forbidden");
		shiroFilterFactoryBean.setFilters(logoutFilter("/unLogin"));
		shiroFilterFactoryBean.setFilterChainDefinitionMap(initFilterChainConfiguration());
		return shiroFilterFactoryBean;
	}

	private Map<String, Filter> logoutFilter(String redirectUrl) {
		Map<String, Filter> filters = new LinkedHashMap<>();
		filters.put("logout", new LogoutFilter() {{
			setRedirectUrl(redirectUrl);
		}});
		return filters;
	}

	private Map<String, String> initFilterChainConfiguration() {
		Map<String, String> filterChainDefinitionManager = new LinkedHashMap<>();
//		List<String> anonsList = ShiroResource.getAnonsList();
//		if(anonsList != null){
//			anonsList.stream().filter(Objects::nonNull).forEach(e->filterChainDefinitionManager.put(e, "anon"));
//		}
		filterChainDefinitionManager.put("/login","anon");
		filterChainDefinitionManager.put("/admin/**", "authc,perms[admin:manage]");
		return filterChainDefinitionManager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
