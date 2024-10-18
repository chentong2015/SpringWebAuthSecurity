package org.example.config;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.security.SecureRandom;

public class PasswordEncoderClass {

    // 1. BCryptPasswordEncoder
    public void testBCryptPasswordEncoder(String plainPassword) {
        int strength = 10; // work factor of bcrypt
        SecureRandom secureRandom = new SecureRandom(); // salt generator 为加密提供一个随机数字
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, secureRandom);
        String encodedPassword = bCryptPasswordEncoder.encode(plainPassword);
        System.out.println(encodedPassword);
    }

    // 2. Pbkdf2PasswordEncoder
    public void testPbkdf2PasswordEncoder(String plainPassword) {
        String pepper = "pepper"; // secret key used by password encoding
        int iterations = 200000;  // number of hash iteration
        int hashWidth = 256;      // hash width in bits

        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(pepper, iterations, hashWidth);
        pbkdf2PasswordEncoder.setEncodeHashAsBase64(true);
        String encodedPassword = pbkdf2PasswordEncoder.encode(plainPassword);
        System.out.println(encodedPassword);
    }

    // 3. SCryptPasswordEncoder
    // 自定义CPU和内存的代价，降低被解密的可能性
    public void testSCryptPasswordEncoder(String plainPassword) {
        int cpuCost = (int) Math.pow(2, 14); // factor to increase CPU costs
        int memoryCost = 8;      // increases memory usage
        int parallelization = 1; // currently not supported by Spring Security
        int keyLength = 32;      // key length in bytes
        int saltLength = 64;     // salt length in bytes

        SCryptPasswordEncoder sCryptPasswordEncoder =
                new SCryptPasswordEncoder(cpuCost, memoryCost, parallelization, keyLength, saltLength);
        String encodedPassword = sCryptPasswordEncoder.encode(plainPassword);
        System.out.println(encodedPassword);
    }

    // 4. Argon2PasswordEncoder
    public void testArgon2PasswordEncoder(String plainPassword) {
        int saltLength = 16; // salt length in bytes
        int hashLength = 32; // hash length in bytes
        int parallelism = 1; // currently not supported by Spring Security
        int memory = 4096;   // memory costs
        int iterations = 3;

        Argon2PasswordEncoder argon2PasswordEncoder =
                new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
        String encodePassword = argon2PasswordEncoder.encode(plainPassword);
        System.out.println(encodePassword);
    }
}

