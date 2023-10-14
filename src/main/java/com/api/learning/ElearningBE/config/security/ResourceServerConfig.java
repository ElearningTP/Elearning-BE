package com.api.learning.ElearningBE.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

//@Configuration
//@EnableResourceServer
public class ResourceServerConfig {//extends ResourceServerConfigurerAdapter {
//    private static final String RESOURCE_ID = "resource_id";
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId(RESOURCE_ID);
//    }
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .requestMatchers()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/v2/api-docs", "/swagger-ui.html", "/swagger-resources/**", "/configuration/**", "/configuration/ui","/webjars/**").permitAll()
//                .antMatchers("/login","/oauth2/**").permitAll()
//                .antMatchers("/v1/account/hello").permitAll()
//                .antMatchers("/**").authenticated()
//                .and().oauth2Login()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
//    }
}
