package encoder;

import java.security.SecureRandom;

public class SaltBytesGenerator {

    private SaltBytesGenerator() {
    }

    // 生成一个随机的Salt Bytes用于加密密码
    public static byte[] generateNewSaltBytes() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }
}
