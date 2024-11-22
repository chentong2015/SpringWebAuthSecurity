package login.api;

import login.bean.UserRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserRepoManager userService;

    public LoginController(UserRepoManager userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signin(@RequestBody UserRequest userRequest) {
        boolean loginResult = this.userService.authenticateUser(userRequest);
        if (loginResult) {
            return ResponseEntity.ok().body("Sign In Success !");
        }
        return ResponseEntity.ok().body("Failed: Make sure the username and password is correct !");
    }
}
