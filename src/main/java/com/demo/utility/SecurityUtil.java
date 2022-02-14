package com.demo.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityUtil {

    private SecurityUtil() {}

    private static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    public static String getEncodedPassword(String source) {
        return PASSWORD_ENCODER.encode(source);
    }
}
