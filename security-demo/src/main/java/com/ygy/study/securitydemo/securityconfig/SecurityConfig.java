package com.ygy.study.securitydemo.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler loginSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler loginFailureHandler;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /**
         * 内存方式
         */
//        auth
//    	.inMemoryAuthentication()
//                .withUser("zhangsan").password("123456").roles("role1")
//                .and()
//                .withUser("lisi").password("123456").roles("role2");

        /**
         * 上述异常：java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
         * 改为如下
         */
//        auth
//    	.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("zhangsan").password(new BCryptPasswordEncoder().encode("123456")).roles("role1")
//                .and()
//                .withUser("lisi").password(new BCryptPasswordEncoder().encode("123456")).roles("role2");


        /**
         * 接口方式，内部可以是任何方式，内存、数据库等
         */
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.cors().and().csrf().disable();
        http.authorizeRequests()
                // 放行接口
//                .antMatchers(GitsResourceServerConfiguration.AUTH_WHITELIST).permitAll()
                .antMatchers("/v2/api-docs",//swagger api json
                        "/swagger-resources/configuration/ui",//用来获取支持的动作
                        "/swagger-resources",//用来获取api-docs的URL
                        "/swagger-resources/configuration/security",//安全选择
                        "/webjars/**",
                        "/lessonbiz/testTime/**",
                        "/swagger-ui.html",
                        "/pay/plfNotify",
                        "/udb/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                // 异常处理(权限拒绝、登录失效等)
                .and()
                    .exceptionHandling()
//                    .authenticationEntryPoint(anonymousAuthenticationEntryPoint)//匿名用户访问无权限资源时的异常处理
//                    .accessDeniedHandler(accessDeniedHandler)//登录用户没有权限访问资源
                // 登入
                .and()
                    .formLogin().permitAll()//允许所有用户
                    .successHandler(loginSuccessHandler)//登录成功处理逻辑
                    .failureHandler(loginFailureHandler)//登录失败处理逻辑
                // 登出
                .and()
                    .logout().permitAll()//允许所有用户
                    .logoutSuccessHandler(logoutSuccessHandler)//登出成功处理逻辑
//                    .deleteCookies(RestHttpSessionIdResolver.AUTH_TOKEN)
                // 会话管理
                .and()
//                    .sessionManagement().invalidSessionStrategy(invalidSessionHandler) // 超时处理
//                    .maximumSessions(1)//同一账号同时登录最大用户数
//                    .expiredSessionStrategy(sessionInformationExpiredHandler) // 顶号处理
        ;



//     http
//             .authorizeRequests()
////             .antMatchers("/","/login").permitAll()
////             .antMatchers("/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/data").hasAuthority("add")
//                .antMatchers(HttpMethod.GET, "/api/data").hasAuthority("query")
//                .antMatchers("/home").hasAuthority("base")
//             .and()
//                .cors()
//             .and()
//                 .csrf()
//                 .disable().exceptionHandling()
//             .and()
//                .formLogin().permitAll()
////                .successHandler(loginSuccessHandler)
////                // 登录失败
////                .failureHandler(loginFailureHandler).permitAll()
////             .and()
////                    // 注销成功
////                    .logout().logoutSuccessHandler(logoutSuccessHandler)
//
////                .and()
////                    // 未登录请求资源
////                    .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint())
//     ;


//
//        http
//                .formLogin()
//                .permitAll()
////                .loginProcessingUrl("/login")
//                // 登录成功
//                .successHandler(loginSuccessHandler)
//                // 登录失败
//                .failureHandler(loginFailureHandler).permitAll()
//                .and()
//                    // 注销成功
//                    .logout().logoutSuccessHandler(logoutSuccessHandler)
//
//                .and()
//                    // 未登录请求资源
//                    .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint())
//                .and()
//                    .authorizeRequests()
//                    .antMatchers(HttpMethod.POST, "/api/data").hasAuthority("add")
//                    .antMatchers(HttpMethod.GET, "/api/data").hasAuthority("query")
//                    .antMatchers("/home").hasAuthority("base")
        ;


//        http.exceptionHandling()
//                .and()
//                    .authorizeRequests()
//                    .antMatchers("/v2/api-docs",//swagger api json
//                            "/swagger-resources/configuration/ui",//用来获取支持的动作
//                            "/swagger-resources",//用来获取api-docs的URL
//                            "/swagger-resources/configuration/security",//安全选择
//                            "/webjars/**",
//                            "/lessonbiz/testTime/**",
//                            "/swagger-ui.html",
//                            "/pay/plfNotify",
//                            "/udb/**")
//                    .permitAll()
//                    .anyRequest()
//                    .authenticated()
//                .and()
//                    .formLogin()
//                    .permitAll()
//                .and()
//                    .logout()
//                    .permitAll()
//                .and()
//                    .cors()
//                .and()
//                    .csrf()
//                    .disable()
//                    .exceptionHandling()
////                    .accessDeniedHandler(accessDeniedHandler())
////                    .authenticationEntryPoint(authenticationEntryPoint())
//                .and()
////                    .addFilterBefore(authenticationProcessingFilter(authenticationManagerBean()), BasicAuthenticationFilter.class)
//                    .headers()
//                    .frameOptions()
//                    .sameOrigin().disable()

        ;
    }




}
