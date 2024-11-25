package main.backend.controller;

import main.backend.exception.ResourceConflictException;
import main.backend.model.UserRequest;
import main.backend.model.entity.UserEntity;
import main.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = POST, value = "/signup")
    public ResponseEntity<?> singUpUser(@RequestBody UserRequest userRequest) {
        UserEntity existUser = this.userService.findUserByUsername(userRequest.getUsername());
        if (existUser != null) {
            // 当抛出异常时返回Exception ResponseEntity
            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
        }

        UserEntity user = this.userService.persistUser(userRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
