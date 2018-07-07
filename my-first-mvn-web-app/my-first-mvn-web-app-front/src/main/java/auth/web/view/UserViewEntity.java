package auth.web.view;

import lombok.Data;

@Data
public class UserViewEntity {
    String userName;
    String passwordPlain;
    String passwordEncoded;
}
