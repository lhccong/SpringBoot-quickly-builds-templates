package com.cong.springbootinit.config;

import com.cong.springbootinit.interceptors.JwtInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @SpringBootConfiguration[取消拦截器配置]
 * @author lhc
 * @date 2022-09-19 21:47
 */
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                //排除swagger拦截
                .excludePathPatterns("/v2/**","/favicon.ico","/swagger-ui.html/**","/doc.html","/webjars/**","/swagger-resources/**","/v2/api-docs");
    }
}
