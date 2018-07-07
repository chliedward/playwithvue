package auth.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Primary
@Service
public class UserRepositoryImpl implements UserRepository{


    public static Map<String,UserDetails> mockUserDetailRepo = new HashMap<>();

    @Override
    public UserDetails getUserDetailsByUserName(String username) {
        return mockUserDetailRepo.get(username);
    }

    @Override
    public UserDetails updateUserDetail(UserDetails userDetails) {
        if(userDetails!=null && userDetails.getUsername()!=null &&
                !"".equals(userDetails.getUsername())
                ){
            mockUserDetailRepo.put(userDetails.getUsername(),userDetails);
            return userDetails;
        }else{
            return null;
        }

    }
}
