package com.api.learning.ElearningBE.config;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
//    private static final String DATE_FORMAT = "dd/MM/yyyy";
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
//        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
        builder.dateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));
        builder.indentOutput(true);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        converters.add(new ResourceHttpMessageConverter());
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Define resource handlers to serve Swagger UI resources.
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    @Bean
    public FilterRegistrationBean<CorsFilter> filterRegistrationBean(){
        final UrlBasedCorsConfigurationSource source = corsConfigurationSource();
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
    @Bean
    public CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source = corsConfigurationSource();
        return new CorsFilter(source);
    }
    private UrlBasedCorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Accept", "Origin","Content-Disposition","Cache-Control"));
        configuration.setExposedHeaders(Arrays.asList("Accept", "Origin", "Content-Type", "Depth", "User-Agent", "Authorization", "Cache-Control","Content-Disposition"));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
