package com.api.learning.ElearningBE.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Objects;

//@Configuration
//@EnableAuthorizationServer
public class AuthorizationServerConfig {//extends AuthorizationServerConfigurerAdapter {
//    static final String CLIENT_ID = "phuc-client";
//    static final String CLIENT_SECRET = "phuc-secret";
//    static final String GRANT_TYPE_PASSWORD = "password";
////    static final String AUTHORIZATION_CODE = "authorization_code";
////    static final String IMPLICIT = " implicit";
//    static final String REFRESH_TOKEN = "refresh_token";
//    static final String SCOPE_READ = "read";
//    static final String SCOPE_WRITE = "write";
//    static final String TRUST = "trust";
//    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60;
//    static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60 * 60;
//    private String signingKey = "phucs2002p";
//
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
////    @Autowired
////    private DataSource dataSource;
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter(){
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setAccessTokenConverter(new CustomTokenConverter());
//        converter.setSigningKey(signingKey);
//        return converter;
//    }
//    @Bean
//    public TokenStore tokenStore(){
//        return new JdbcTokenStore(Objects.requireNonNull(jdbcTemplate.getDataSource()));
////        return new JwtTokenStore(accessTokenConverter());
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients
//                .jdbc(jdbcTemplate.getDataSource());
////        clients
////                .inMemory()
////                .withClient(CLIENT_ID)
////                .secret(passwordEncoder.encode(CLIENT_SECRET))
////                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, REFRESH_TOKEN)
////                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
////                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
////                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints
//                .pathMapping("/oauth/token", "/api/login")
//                .tokenStore(tokenStore())
//                .authenticationManager(authenticationManager)
//                .accessTokenConverter(accessTokenConverter())
//                .reuseRefreshTokens(false)
//                .
//    }
}
