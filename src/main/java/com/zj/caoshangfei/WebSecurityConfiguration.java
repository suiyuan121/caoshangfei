package com.zj.caoshangfei;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Jerry on 2017/7/17.
 */
@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/", "/p*/", "/p*", "/c*/", "/c*",
                "/gongzhuang/**", "/search/**/", "/topic/**", "/new*/**", "/caoshangfei/**/");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login").permitAll().and()
                .authorizeRequests().antMatchers("/logout").authenticated().and()
                .authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").and()
                .authorizeRequests().antMatchers("/**").permitAll().and()
                .exceptionHandling().accessDeniedPage("/accessDenied").and().csrf().disable();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }
}
