package com.docuart.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration cc = new CorsConfiguration();
        //cc.addAllowedOrigin("*");
        cc.addAllowedOriginPattern("*");
        cc.addAllowedMethod("*");
        cc.addAllowedHeader("*");
        cc.addExposedHeader("Authorization,Link,X-Total-Count");

        cc.setAllowCredentials(true);
        cc.setMaxAge(1800L);
        return new CorsFilter(source);
    }

}
