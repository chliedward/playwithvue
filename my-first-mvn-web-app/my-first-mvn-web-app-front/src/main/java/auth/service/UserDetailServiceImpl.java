package auth.service;

import auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *  UserDetailService is springframework's service
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
                 * User(String username, String password,
                 * 			Collection<? extends GrantedAuthority> authorities
                 */
        // load from repo and return a User instance.
        UserDetails userDetail = userRepository.getUserDetailsByUserName(username);
        return userDetail;
    }

}
