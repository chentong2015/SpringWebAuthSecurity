package login_template.repo;

import login_template.model.User;
import login_template.model.UserAuthEntity;

import java.util.Optional;

public interface UserRepository {

    Optional<UserAuthEntity> findAuthDataByLogin(String login);

    // 注册一个新的用户
    User save(User user);
}