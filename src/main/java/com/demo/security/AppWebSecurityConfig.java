package com.demo.security;

import com.demo.authentication.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@EnableWebSecurity
public class AppWebSecurityConfig {

    private final UserService userService;

    public AppWebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/secure/confirm").hasAnyAuthority("ADMIN", "OPERATOR", "ENGINEER", "MANAGER")
                                .requestMatchers("/secure/reinspect").hasAnyAuthority("ADMIN", "OPERATOR", "ENGINEER", "MANAGER") //人工复检
                                .requestMatchers("/secure/config", "/secure/configs", "/secure/clear").hasAnyAuthority("ADMIN", "MANAGER") //控制器
                                .requestMatchers("/user/**").hasAnyAuthority("ADMIN", "MANAGER")
                                .requestMatchers("/plc/**",
                                        "/static/**",
                                        "/manifest.json",
                                        "/swagger-ui/**",
                                        "/swagger-resources/**",
                                        "/v3/**",
                                        "/favicon.ico").permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(handling -> handling.authenticationEntryPoint(new Http401AuthenticationEntryPoint())
                        .accessDeniedHandler(new AccessFailHandler()))
                .formLogin(login -> login.successHandler(new AuthenticationSuccessHandler())
                        .failureHandler(new AuthenticationFailHandler())
                        .loginPage("/login").permitAll()
                        .usernameParameter("username")
                        .passwordParameter("password"))
                .logout(logout -> logout.permitAll().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true))
                .addFilterAfter(new SessionTimeoutFilter(), AuthorizationFilter.class)
        ;
        return http.build();
    }
}
