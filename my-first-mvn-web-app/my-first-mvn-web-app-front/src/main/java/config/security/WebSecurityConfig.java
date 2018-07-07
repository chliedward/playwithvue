package config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@Import(AuthConfig.class)
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

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/auth/*").permitAll()
        		.antMatchers("/login.jsp").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers( "/dispatcher1/helloWorld/hello").permitAll()
                .antMatchers( "/dispatcher1/helloWorld/login").permitAll()
                .antMatchers( "/dispatcher1/helloWorld/postTest").permitAll()
            	.antMatchers("/dispatcher1/helloWorld/helloAuth").hasAnyRole("ADMIN","USER")

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
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("adminpass").roles("ADMIN");


        // this line makes ..... DAO uses your userDetailsServiceImpl's method to load user from repo
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}