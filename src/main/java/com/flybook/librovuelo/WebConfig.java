package com.flybook.librovuelo;

import com.flybook.librovuelo.conf.security.InterceptorAccessAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    InterceptorAccessAuthentication interceptorAccessAuthentication;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorAccessAuthentication)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/**", "/img/**");
    }
}