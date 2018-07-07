package auth;

public interface SecurityService {

    String findLoggedInUsername();

    boolean login(String username, String password);

}
