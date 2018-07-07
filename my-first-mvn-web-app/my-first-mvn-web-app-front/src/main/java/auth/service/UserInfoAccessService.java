package auth.service;

import auth.web.view.UserViewEntity;
import org.springframework.stereotype.Service;

public interface UserInfoAccessService {
    public UserViewEntity getUserViewEntityByName(String userName);

    public UserViewEntity saveUserView(UserViewEntity entity);
}
