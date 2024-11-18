package encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.security.SecureRandom;

// TODO. 对密码进行Hashing加密处理，避免被破解
public class PasswordEncoderHandler {

    // 1. BCrypt Password Encoder 在加密时通过SecureRandom生成随机Salt
    public String encodePasswordBCrypt(String plainPassword, SecureRandom secureRandom) {
        int strength = plainPassword.length();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, secureRandom);
        return bCryptPasswordEncoder.encode(plainPassword);
    }

    // 2. Pbkdf2 Password Encoder
    public String encodePasswordPbkdf2(String plainPassword) {
        String pepper = "pepper"; // secret key used by password encoding
        int iterations = 200000;  // number of hash iteration
        int hashWidth = 256;      // hash width in bits

        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(pepper, iterations, hashWidth,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        pbkdf2PasswordEncoder.setEncodeHashAsBase64(true);
        return pbkdf2PasswordEncoder.encode(plainPassword);
    }

    // 3. SCrypt Password Encoder 自定义CPU和内存代价，降低被解密的可能性
    public String testSCryptPasswordEncoder(String plainPassword) {
        int cpuCost = (int) Math.pow(2, 14); // factor to increase CPU costs
        int memoryCost = 8;      // increases memory usage
        int parallelization = 1; // currently not supported by Spring Security
        int keyLength = 32;      // key length in bytes
        int saltLength = 64;     // salt length in bytes

        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder(
                cpuCost, memoryCost, parallelization, keyLength, saltLength);
        return sCryptPasswordEncoder.encode(plainPassword);
    }

    // 4. Argon2 Password Encoder 添加Salt以及长度的控制
    public String testArgon2PasswordEncoder(String plainPassword) {
        int saltLength = 16; // salt length in bytes
        int hashLength = 32; // hash length in bytes
        int parallelism = 1; // currently not supported by Spring Security
        int memory = 4096;   // memory costs
        int iterations = 3;

        Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(
                saltLength, hashLength, parallelism, memory, iterations);
        return argon2PasswordEncoder.encode(plainPassword);
    }
}
