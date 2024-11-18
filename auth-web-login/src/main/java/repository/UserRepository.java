package repository;

import bean.UserRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.print.attribute.standard.MediaSize;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 模拟DAO持久层的数据检索和存储
public class UserRepository {

    private static Map<String, UserDetails> userHashMap = new ConcurrentHashMap<>();

    // 用户具有不同的Role角色授权
    public static void persistUser(UserRequest userRequest, String... roles) {
        String name = userRequest.getUsername();
        UserDetails userDetails = User.withUsername(name)
                .password(userRequest.getPassword())
                .roles(roles)
                .build();
        userHashMap.put(name, userDetails);
    }

    public static boolean isAlreadyExist(UserRequest userRequest) {
        return userHashMap.containsKey(userRequest.getUsername());
    }
}
