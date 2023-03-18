package com.kq.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 跨越解决
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //映射路径
                .allowedOriginPatterns("*")  //应许那些域来访问
                .allowedMethods("GET","POST","PUT","DELETE","HEAD","OPTIONS")  //应许那些方法来请求
                .allowCredentials(true)  //是否应许携带cookie
                .maxAge(3600)  //设置有效期
                .allowedHeaders("*");  //请求头
}

    /**
     * 解决cookie无法携带的问题
     * @return
     */
    @Bean
    public CookieSerializer httpSessionIdResolver() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setSameSite("None");
        cookieSerializer.setUseSecureCookie(true);
        return cookieSerializer;
    }
}
