package auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    /**
         *  This is springframework's bean
         */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(userDetails instanceof UserDetails){
            return ((UserDetails)userDetails).getUsername();
        }else{
            return null;
        }
    }

    @Override
    public boolean login(String username, String password) {
        // get golden answer from repository
        UserDetails userDetails =userDetailsService.loadUserByUsername(username);
        if(userDetails==null){
            return false;
        }
        // inject the password
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        // see if the password fed by user matches the golden answer
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // if pass, then tell securityContextHolder that it is authenticated.
        if(usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            return true;
        }
        return false;
    }
}
