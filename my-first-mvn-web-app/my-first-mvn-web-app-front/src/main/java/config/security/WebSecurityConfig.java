package config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
/**
 * 
 * This config cooperates with :
 * 	 <filter>
 *	  <filter-name>springSecurityFilterChain</filter-name>
 *    <filter-class>org.springframework.web.filter.DelegatingFilterProxy </filter-class>            
 *   </filter>
 *   <filter-mapping>
 *     <filter-name>springSecurityFilterChain</filter-name>
 *     <url-pattern>/*</url-pattern>
 *    </filter-mapping>
 *    in web.xml.
 *    Since the filter is in the global context.
 *    So the url is not yet thrown to servlet. 
 *    (i.e., if you access http://localhost:8180/my-first-mvn-web-app-front/dispatcher1/helloWorld/hello
 *     http here is "/dispatcher1/helloWorld/hello" )
 *    See Also: HelloWorldController.java.   please compare the difference in request mapping logic 
 *   @author shang
 * */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        		.antMatchers("/login.jsp").permitAll()
            	.antMatchers("/dispatcher1/helloWorld/helloAuth").hasRole("ADMIN")
                //.antMatchers( "/dispatch1/helloWorld/hello").permitAll()
                
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
 
}