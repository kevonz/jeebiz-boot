/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.boot.autoconfigure;

import java.io.File;
import java.util.Iterator;
import java.util.Map.Entry;

import org.springframework.biz.web.servlet.handler.Log4j2MDCInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

import net.jeebiz.boot.autoconfigure.config.LocalResourceProperteis;

public class DefaultWebMvcConfigurer implements WebMvcConfigurer {
	
	private final String META_INF_RESOURCES = "classpath:/META-INF/resources/"; 
	private final String META_INF_WEBJAR_RESOURCES = "classpath:/META-INF/resources/webjars/"; 
    
	private ThemeChangeInterceptor themeChangeInterceptor;
	private LocaleChangeInterceptor localeChangeInterceptor;
	private Log4j2MDCInterceptor log4j2MDCInterceptor;
    private LocalResourceProperteis localResourceProperteis;
    
    public DefaultWebMvcConfigurer(LocalResourceProperteis localResourceProperteis,
			ThemeChangeInterceptor themeChangeInterceptor, LocaleChangeInterceptor localeChangeInterceptor,
			Log4j2MDCInterceptor log4j2mdcInterceptor) {
		super();
		this.localResourceProperteis = localResourceProperteis;
		this.themeChangeInterceptor = themeChangeInterceptor;
		this.localeChangeInterceptor = localeChangeInterceptor;
		log4j2MDCInterceptor = log4j2mdcInterceptor;
	}
	
    @Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(log4j2MDCInterceptor).addPathPatterns("/**").order(Integer.MIN_VALUE);
		registry.addInterceptor(themeChangeInterceptor).addPathPatterns("/**").order(Integer.MIN_VALUE + 1);
		registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/**").order(Integer.MIN_VALUE + 2);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	// 本地资源映射
    	if(!CollectionUtils.isEmpty(localResourceProperteis.getLocalLocations())){
    		Iterator<Entry<String, String>> ite = localResourceProperteis.getLocalLocations().entrySet().iterator();
    		while (ite.hasNext()) {
				Entry<String, String> entry = ite.next();
				if (localResourceProperteis.isLocalRelative()) {
					registry.addResourceHandler(entry.getKey()).addResourceLocations(ResourceUtils.FILE_URL_PREFIX
							+ localResourceProperteis.getLocalStorage() + File.separator + entry.getValue());
				} else {
					registry.addResourceHandler(entry.getKey()).addResourceLocations(entry.getValue());
				}
			}
		}
    	// 指定个性化资源映射
		registry.addResourceHandler("/assets/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/assets/");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		// swagger增加url映射
		if(registry.hasMappingForPattern("/doc.html**")) {
			registry.addResourceHandler("/doc.html**").addResourceLocations(META_INF_RESOURCES);
		}
		if(registry.hasMappingForPattern("/swagger-ui.html**")) {
			registry.addResourceHandler("/swagger-ui.html**").addResourceLocations(META_INF_RESOURCES);
		}
		if(registry.hasMappingForPattern("/webjars/**")) {
			registry.addResourceHandler("/webjars/**").addResourceLocations(META_INF_WEBJAR_RESOURCES)
				.resourceChain(false).addResolver(new WebJarsResourceResolver());
		}
		
	}
	
}
