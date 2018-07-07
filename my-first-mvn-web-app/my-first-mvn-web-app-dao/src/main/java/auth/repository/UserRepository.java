package auth.repository;

import org.springframework.security.core.userdetails.UserDetails;


public interface UserRepository {

    public UserDetails getUserDetailsByUserName(String username);

    public UserDetails updateUserDetail(UserDetails userDetails  );
}
