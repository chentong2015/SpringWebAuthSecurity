import encoder.PasswordEncoderClass;
import encoder.SecureSaltRandom;

import java.security.SecureRandom;

public class PasswordTester {

    public static void main(String[] args) {
        // 先生成一个Byte Salt随机数，用于Hash加密密码
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        secureRandom.nextBytes(salt);
        System.out.println(salt);

        // TODO. 相同的密码 + 相同的Salt -> Hash加密成相同的结果
        SecureSaltRandom secureSaltRandom1 = new SecureSaltRandom(salt);
        PasswordEncoderClass encoderClass = new PasswordEncoderClass();
        String encodedPassword = encoderClass.encodePasswordBCrypt("test123", secureRandom);
        System.out.println(encodedPassword);

        SecureSaltRandom secureSaltRandom2 = new SecureSaltRandom(salt);
        encodedPassword = encoderClass.encodePasswordBCrypt("test123", secureRandom);
        System.out.println(encodedPassword);
    }
}
