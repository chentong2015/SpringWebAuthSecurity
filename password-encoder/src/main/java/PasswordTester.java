import encoder.HashingPwdEncoder;
import encoder.SaltSecureRandom;

import java.util.Arrays;

public class PasswordTester {

    // TODO. 相同密码 + 相同Salt -> Hash加密成相同结果
    public static void main(String[] args) {
        SaltSecureRandom saltSecureRandom = new SaltSecureRandom();

        HashingPwdEncoder passwordEncoder = new HashingPwdEncoder();
        String encodedPassword = passwordEncoder.encodePasswordBCrypt("password321", saltSecureRandom);
        System.out.println(encodedPassword);

        // 单向加密用于验证原始密码是否一致
        encodedPassword = passwordEncoder.encodePasswordBCrypt("password321", saltSecureRandom);
        System.out.println(encodedPassword);

        encodedPassword = passwordEncoder.encodePasswordBCrypt("password123", saltSecureRandom);
        System.out.println(encodedPassword);

        System.out.println(Arrays.toString(saltSecureRandom.getBytesSalt()));
    }
}
