package config.security;

import auth.SecurityService;
import auth.SecurityServiceImpl;
import auth.repository.UserRepository;
import auth.repository.UserRepositoryImpl;
import auth.service.UserDetailServiceImpl;
import auth.service.UserInfoAccessService;
import auth.service.UserInfoAccessServiceImpl;
import auth.web.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"auth"})
public class AuthConfig {
    @Bean
    public UserInfoAccessService userInfoAccessService(){
        return new UserInfoAccessServiceImpl();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailServiceImpl();
    }
    @Bean
    public UserValidator userValidator(){
        return new UserValidator();
    }
    @Bean
    public UserRepository userRepository(){
        return new UserRepositoryImpl();
    }
    @Bean
    public SecurityService securityService(){
        return new SecurityServiceImpl();
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        /**
                 * <property name="prefix">
                 * 			<value>/WEB-INF/views/</value>
                 * 		</property>
                 * 		<property name="suffix">
                 * 			<value>.jsp</value>
                 * 		</property>
                 * @return
                 */
        return new InternalResourceViewResolver("/WEB-INF/views/",".jsp");
    }
}
