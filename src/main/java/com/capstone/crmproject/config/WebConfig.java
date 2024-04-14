package com.capstone.crmproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //config 비활성화
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000") // 모든 출처 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메소드 지정
                .allowedHeaders("*") // 모든 헤더 허용
                .exposedHeaders("X-CSRF-Token") // CSRF 토큰을 노출시킬 헤더 지정
                .allowCredentials(true) // 자격 증명 정보 허용
                .maxAge(3600); // preflight 요청의 캐시 유효 기간 설정
    }
}
