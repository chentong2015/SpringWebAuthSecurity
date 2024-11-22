import encoder.PasswordEncoderHandler;
import encoder.SaltSecureRandom;

import java.util.Arrays;

public class PasswordTester {

    // TODO. 相同的密码 + 相同的Salt -> Hash加密成相同结果
    public static void main(String[] args) {
        SaltSecureRandom saltSecureRandom = new SaltSecureRandom();

        PasswordEncoderHandler passwordEncoder = new PasswordEncoderHandler();
        String encodedPassword = passwordEncoder.encodePasswordBCrypt("password321", saltSecureRandom);
        System.out.println(encodedPassword);

        encodedPassword = passwordEncoder.encodePasswordBCrypt("password321", saltSecureRandom);
        System.out.println(encodedPassword);

        encodedPassword = passwordEncoder.encodePasswordBCrypt("password123", saltSecureRandom);
        System.out.println(encodedPassword);

        System.out.println(Arrays.toString(saltSecureRandom.getBytesSalt()));
    }
}
