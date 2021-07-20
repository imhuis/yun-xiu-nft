package com.imhui.security.config;

import com.imhui.security.filter.ImageCodeValidateFilter;
import com.imhui.security.handler.CustomizeAccessDeniedHandler;
import com.imhui.security.handler.CustomizeAuthenticationEntryPoint;
import com.imhui.security.handler.CustomizeAuthenticationFailureHandler;
import com.imhui.security.handler.CustomizeAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
                .withUser("admin").password(passwordEncoder().encode("123456")).authorities("ADMIN")
                .and()
                .withUser("user").password(passwordEncoder().encode("123456")).authorities("USER");
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
//        super.configure(web);
        web
                .httpFirewall(httpFirewall())
                .ignoring().antMatchers(
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/h2-console/**",
                "/actuator/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        registry
                .and()
                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/public/**").permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(new CustomizeAuthenticationSuccessHandler())
                .failureHandler(new CustomizeAuthenticationFailureHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomizeAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomizeAccessDeniedHandler())
                .and()
                .sessionManagement()
                .maximumSessions(2)
//                .expiredSessionStrategy()
        ;
        http.addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public HttpFirewall httpFirewall(){
        DefaultHttpFirewall httpFirewall = new DefaultHttpFirewall();
        httpFirewall.setAllowUrlEncodedSlash(true);
        return httpFirewall;
    }
}
