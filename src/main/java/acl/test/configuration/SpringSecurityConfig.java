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
        //Add this to enable h2 console
        http.headers().frameOptions().disable();
        http.csrf().disable()
                .authorizeRequests().antMatchers("/h2/**").permitAll()
                .and().authorizeRequests().antMatchers("/**").authenticated()
                .and().httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("john").password("password").roles("USER");
        auth.inMemoryAuthentication().withUser("jane").password("password").roles("USER");
        auth.inMemoryAuthentication().withUser("mike").password("password").roles("USER");
        auth.inMemoryAuthentication().withUser("james").password("password").roles("USER");
    }
}
