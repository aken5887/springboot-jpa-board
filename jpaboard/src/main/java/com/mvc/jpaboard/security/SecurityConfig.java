package com.mvc.jpaboard.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    private UsersService usersService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("security config ...............................");

        http.authorizeRequests()
//                .antMatchers("/guest/**").permitAll()
//                .antMatchers("/manager/**").hasRole("MANAGER")
//                .antMatchers("/admin/**").hasRole("ADMIN");
                        .antMatchers("/boards/list").permitAll()
                        .antMatchers("/boards/register").hasAnyRole("BASIC", "MANAGER", "ADMIN");

        http.formLogin().loginPage("/common/login").successHandler(new LoginSuccessHandler());
        http.exceptionHandling().accessDeniedPage("/common/accessDenied");
        //세션무효화
        http.logout().logoutUrl("/common/logout").invalidateHttpSession(true).deleteCookies();
//        http.userDetailsService(usersService);
        http.rememberMe()
                .key("minu")
                .userDetailsService(usersService)
                .tokenRepository(getJDBCRepository())
                .tokenValiditySeconds(60*60*24);
    }

    private PersistentTokenRepository getJDBCRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        log.info("build Auth global....");
        auth.userDetailsService(usersService)
                .passwordEncoder(passwordEncoder());
    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        log.info("build Auth global....");
//        // 메모리상에 있는 제한 적인 정보를 이용
////        auth.inMemoryAuthentication()
////                .withUser("manager")
////                .password("1111")
////                .roles("MANAGER");
//    }
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//         log.info("build Auth global........");
//
//         String query1 = "SELECT uid username, upw password, true enabled FROM tbl_members WHERE uid= ?";
//
//         String query2 = "SELECT member uid, role_name role FROM tbl_member_roles   WHERE member = ?";
//
//         auth.jdbcAuthentication()
//                 .dataSource(dataSource)
//                 .usersByUsernameQuery(query1)
//                 .rolePrefix("ROLE_")
//                 .authoritiesByUsernameQuery(query2);
//     }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence rawPassword) {
//                return rawPassword.toString();
//            }
//
//            @Override
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                return rawPassword.equals(encodedPassword);
//            }
//        };
//    }
}