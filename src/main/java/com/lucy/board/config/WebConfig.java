package com.lucy.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lucy.board.interceptor.LoginInterceptor;
import com.lucy.board.interceptor.RequestInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    @Qualifier(value = "loginInterceptor")
    private LoginInterceptor interceptor;
    
    @Autowired
    private RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/api/*")
                .excludePathPatterns("/api/users/send-tmp-pwd")
                .excludePathPatterns("/api/users/signup")
                .excludePathPatterns("/api/users/login");
        registry.addInterceptor(requestInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/static/js/**")
        .excludePathPatterns("/static/css/**");
    }
}
