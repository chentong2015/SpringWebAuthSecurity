package custom.rest;

import custom.model.UserRequest;
import custom.repository.UserRepoManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pwd.PasswordValidator;

@RestController
public class RegistrationController {

    private final UserRepoManager userService;
    private final PasswordValidator passwordValidator;

    public RegistrationController(UserRepoManager userService) {
        this.userService = userService;
        this.passwordValidator = new PasswordValidator();
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signup(@RequestBody UserRequest userRequest) {
        boolean validPwd = this.passwordValidator.validatePasswordCreation(userRequest.getUsername(), userRequest.getPassword());
        if (!validPwd) {
           return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Password is not match Policy !");
        }

        if (this.userService.persistUser(userRequest)) {
            return ResponseEntity.ok().body("Success: Sign up done !");
        }
        return ResponseEntity.badRequest().body("Error: Sign up failed !");
    }
}
