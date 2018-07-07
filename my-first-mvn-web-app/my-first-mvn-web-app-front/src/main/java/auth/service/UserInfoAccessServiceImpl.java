package auth.service;

import auth.repository.UserRepository;
import auth.web.view.UserViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Primary
@Service
public class UserInfoAccessServiceImpl implements UserInfoAccessService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserViewEntity getUserViewEntityByName(String userName) {
        UserDetails userDetails =   userRepository.getUserDetailsByUserName(userName);
        UserViewEntity entity = new UserViewEntity();
        entity.setUserName(userDetails.getUsername());
        entity.setPasswordEncoded(userDetails.getPassword());
        return entity;
    }

    @Override
    public UserViewEntity saveUserView(UserViewEntity entity) {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User(entity.getUserName(),
                bCryptPasswordEncoder.encode(entity.getPasswordPlain()) ,

                roles);
        userRepository.updateUserDetail(userDetails);
        return null;
    }
}
