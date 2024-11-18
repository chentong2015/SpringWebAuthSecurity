package registration;

import bean.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;

@RestController
public class RegistrationController {

    // TODO. 注册时创建新用户，加密用户密码保存
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        if (UserRepository.isAlreadyExist(userRequest)) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        UserRepository.persistUser(userRequest, "USER");
        return ResponseEntity.ok(userRequest);
    }
}
