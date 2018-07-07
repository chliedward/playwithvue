package auth.web.validator;

import auth.web.view.UserViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Service
public class UserValidator implements Validator {


    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    /**
         * Validats user's input
         * @param target
         * @param errors
         */
    @Override
    public void validate(Object target, Errors errors) {
        UserViewEntity user = (UserViewEntity) target;

    }
}
