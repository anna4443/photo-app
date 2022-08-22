package com.as.photoapp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    private static Utils instance = new Utils();

    private Utils() {
    }

    public static Utils getInstance(){
        return instance;
    }

    public String hashPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }

        return String.valueOf(sb);
    }
}
