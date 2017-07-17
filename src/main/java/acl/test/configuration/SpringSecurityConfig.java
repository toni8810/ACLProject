package acl.test.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by toni8810 on 14/07/17.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                .and().authorizeRequests().antMatchers("/personal/**").hasAnyRole("ROLE_ADMIN","ROLE_USER")
                .and().authorizeRequests().antMatchers("/public/**").hasAnyRole("ROLE_ADMIN","ROLE_USER","ROLE_VISITOR")
                .and().httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("john").password("password").roles("ADMIN", "USER", "VISITOR");
        auth.inMemoryAuthentication().withUser("jane").password("password").roles("USER", "VISITOR");
        auth.inMemoryAuthentication().withUser("mike").password("password").roles("VISITOR");
    }
}
