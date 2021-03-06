package lv.id.voldemars.deviceapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/h2console/**").permitAll().and()
                .authorizeRequests().antMatchers("/status/**").permitAll().and()
                .authorizeRequests().anyRequest().authenticated().and()
                .httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().disable();

    }
}
