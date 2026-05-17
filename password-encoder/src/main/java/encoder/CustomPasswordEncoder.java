package encoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// 自定义密码加密器
public class CustomPasswordEncoder implements PasswordEncoder {

    private static final String SECRET_PART = "PSWD34521";

    @Override
    public String encode(CharSequence rawPassword) {
        rawPassword = rawPassword + SECRET_PART;
        byte[] messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256")
                    .digest(rawPassword.toString().getBytes());
        } catch (NoSuchAlgorithmException e) {
            return "EMPTY_STRING";
        }

        BigInteger no = new BigInteger(1, messageDigest);
        return no.toString(10);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
