package custom.repository;

import custom.model.UserRequest;
import encoder.PasswordEncoderHandler;
import encoder.SaltSecureRandom;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 模拟DAO持久层数据检索和存储: 需要进一步考虑并发请求
@Service
public class UserRepoManager {

    private final SaltSecureRandom saltRandom;
    private final PasswordEncoderHandler encoderHandler;
    private final Map<String, UserDetails> userDetailsMap;

    public UserRepoManager() {
        this.userDetailsMap = new ConcurrentHashMap<>();
        this.saltRandom = new SaltSecureRandom();
        this.encoderHandler = new PasswordEncoderHandler();
    }

    // 持久化存储指定的用户(带Role角色授权)
    public boolean persistUser(UserRequest userRequest) {
        if (isFoundUser(userRequest)) {
            return false;
        }
        String encodePassword = this.encoderHandler.encodePasswordBCrypt(userRequest.getPassword(), saltRandom);
        UserDetails newUser = User.withUsername(userRequest.getUsername())
                .password(encodePassword)
                .roles(userRequest.getRole().name())
                .build();
        this.userDetailsMap.put(userRequest.getUsername(), newUser);
        return true;
    }

    // TODO. 必须保证使用同样的加密方式来验证Password
    public boolean authenticateUser(UserRequest userRequest) {
        if (!isFoundUser(userRequest)) {
           return false;
        }
        String encodePassword = this.encoderHandler.encodePasswordBCrypt(userRequest.getPassword(), saltRandom);
        UserDetails userDetails = this.userDetailsMap.get(userRequest.getUsername());
        return userDetails.getPassword().equals(encodePassword);
    }

    private boolean isFoundUser(UserRequest userRequest) {
        return this.userDetailsMap.containsKey(userRequest.getUsername());
    }
}