package org.example;

import jwt.JwtTokenValidator;

import java.security.SecureRandom;

public class Main {

    public static void main(String[] args) {
        JwtTokenValidator tokenValidator = new JwtTokenValidator();

        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        System.out.println(salt);

        secureRandom.nextBytes(salt);
        System.out.println(salt);

        System.out.println("Hello world!");
    }
}