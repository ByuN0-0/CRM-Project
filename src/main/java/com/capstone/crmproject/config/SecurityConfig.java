package com.capstone.crmproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login","loginProc","/register","registerProc").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );
        http
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true")
                        .permitAll()
                );
        //http
        //        .csrf((auth)->auth.disable());
        http
                .sessionManagement((session) -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                );
        http
                .sessionManagement((session) -> session
                        .sessionFixation().changeSessionId()
                );
        http
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );
        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
