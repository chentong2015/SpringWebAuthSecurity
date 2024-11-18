package encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;

import java.util.HashMap;
import java.util.Map;

public class PasswordEncoderConfig {

    // 常见的密码加密器，根据需求采用不同的Encoder
    private void listCommonPasswordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("ldap", new LdapShaPasswordEncoder());
        encoders.put("MD4", new Md4PasswordEncoder());
        encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        // encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        // encoders.put("scrypt", new SCryptPasswordEncoder());
        // encoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
        // encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
        // encoders.put("sha256", new StandardPasswordEncoder());
        // encoders.put("argon2", new Argon2PasswordEncoder());
    }
}
