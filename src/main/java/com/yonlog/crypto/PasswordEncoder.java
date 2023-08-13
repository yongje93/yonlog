package com.yonlog.crypto;

public interface PasswordEncoder {

    String encrypt(String rawPassword);

    boolean matches(String rawPassword, String encryptedPassword);
}
