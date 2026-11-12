package com.shopman.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

/** PBKDF2-HMAC-SHA256 password verification for demo accounts. */
public final class PasswordUtil {
    private static final int ITERATIONS = 120_000;
    private static final int KEY_LENGTH_BITS = 256;

    private PasswordUtil() { }

    public static boolean verify(String password, String saltBase64, String hashBase64) {
        if (password == null || saltBase64 == null || hashBase64 == null) return false;
        PBEKeySpec spec = null;
        try {
            byte[] salt = Base64.getDecoder().decode(saltBase64);
            byte[] expected = Base64.getDecoder().decode(hashBase64);
            spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH_BITS);
            byte[] actual = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
            return MessageDigest.isEqual(actual, expected);
        } catch (Exception ex) {
            return false;
        } finally {
            if (spec != null) spec.clearPassword();
        }
    }
}
