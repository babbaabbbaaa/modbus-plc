package com.demo.security;

import com.demo.authentication.UserService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@EnableWebSecurity
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public AppWebSecurityConfig(UserService userService) {
        this.userService = userService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/plc/**",
                        "/static/**",
                        "/manifest.json",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/**",
                        "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/secure/confirm").hasAnyAuthority("ADMIN", "OPERATOR", "ENGINEER", "MANAGER") //重码确认
                .antMatchers("/secure/reinspect").hasAnyAuthority("ADMIN", "OPERATOR", "ENGINEER", "MANAGER") //人工复检
                .antMatchers("/secure/config", "/secure/configs", "/secure/clear").hasAnyAuthority("ADMIN", "MANAGER") //控制器
                .antMatchers("/user/**").hasAnyAuthority("ADMIN", "MANAGER") //用户管理/角色管理
                .anyRequest().authenticated()
//                .accessDecisionManager(accessDecisionManager())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http401AuthenticationEntryPoint())
                .accessDeniedHandler(new AccessFailHandler())
                .and()
                .formLogin()
                .successHandler(new AuthenticationSuccessHandler())
                .failureHandler(new AuthenticationFailHandler())
                .loginPage("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .addFilterAfter(new SessionTimeoutFilter(), AuthorizationFilter.class)
        ;

    }

//    @Bean
//    public AccessDecisionManager accessDecisionManager() {
//        List<AccessDecisionVoter<?>> accessDecisionVoters = Arrays.asList(
//                new WebExpressionVoter(),
//                new DynamicAccessDecisionVoter(),
//                new AuthenticatedVoter()
//        );
//        return new UnanimousBased(accessDecisionVoters);
//    }
}
