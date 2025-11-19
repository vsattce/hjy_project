package com.hjy.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.lang.reflect.Array;
import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

    }

    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/login", "/register").permitAll()
                                .anyRequest().authenticated())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 👇 重点：启用表单登录（但返回JSON，不是跳页面！）
                .formLogin(form -> form
                        .loginProcessingUrl("/api/auth/login") // 自定义登录接口
                        .successHandler((req, res, auth) -> {
                            res.setContentType("application/json");
                            res.getWriter().write("{\"success\": true, \"message\": \"Login successful!\"}");
                        })
                        .failureHandler((req, res, ex) -> {
                            res.setStatus(401);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"success\": false, \"error\": \"Invalid credentials\"}");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler((req, res, auth) -> {
                            res.setContentType("application/json");
                            res.getWriter().write("{\"success\": true, \"message\": \"Logout successful!\"}");
                        })
                );

        return http.build();
    }

    //    所有跨域先给controller层处理，后续再来改这个
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
//
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
