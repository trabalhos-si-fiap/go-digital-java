package com.laroz.infra.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

//    @Value("${IP_PROD}")
//    private String ipProd;

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permitir para todos os endpoints
                .allowedOriginPatterns("*")  // Domínio do seu front-end
//                .allowedOriginPatterns("http://localhost*", ipProd, "http://192.168.0.151:*")  // Domínio do seu front-end
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Métodos permitidos
                .allowedHeaders("*")  // Cabeçalhos permitidos
                .allowCredentials(true);  // Permitir envio de cookies/autenticação
    }
}
