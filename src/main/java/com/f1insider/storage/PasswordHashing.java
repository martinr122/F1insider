package com.f1insider.storage;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class PasswordHashing {
    private static final String SALT = "ioBFP61n0(Mq";

    public static String doHashing(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashArray = md.digest((password + SALT).getBytes());
            return new BigInteger(1, hashArray).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
