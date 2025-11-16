package com.hjy.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 设置访问源地址
        // "http://localhost:8081"
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        // example:"token,content-text"
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        // example:post,put
        config.addAllowedMethod("*");
        // 有效期 1800秒
//        // OPTIONS
//        浏览器会先发一个"预检请求"(OPTIONS请求)去问服务器："嘿，我能不能跨域访问你的资源呀？"
//
//        这个setMaxAge(1800L)就是告诉浏览器："放心吧，我允许你问1800秒（30分钟）！在这30分钟内，
//        你不用再问我，直接用之前问过的结果就行。"
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }
}
