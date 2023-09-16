package com.example.wallet.wallet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/user/**").permitAll() // signup of new account
                .antMatchers("/user/**").hasAuthority(UserConstants.USER_AUTHORITY)          // user driven actions
                .antMatchers("/**").hasAnyAuthority(UserConstants.ADMIN_AUTHORITY, UserConstants.SERVICE_AUTHORITY)               // admin driven actions
                .and()
                .formLogin();
    }


}
