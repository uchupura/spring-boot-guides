package com.guide.oauth2.password.auth.config.support;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Component
public class Cryptor {
    private static final String CIPHER_INSTANCE_NAME = "AES";
    private static final String SECRET_KEY_ALGORITHM = "AES";
    private static Cipher encryptCipher;
    private static Cipher decryptCipher;

    @Value("${chat.platform.aes-key:#{null}}")
    private String aesKey;

    @PostConstruct
    public void init() {
        try {
            Key secretKey = new SecretKeySpec(Base64.decodeBase64(aesKey), SECRET_KEY_ALGORITHM);

            encryptCipher = Cipher.getInstance(CIPHER_INSTANCE_NAME);
            decryptCipher = Cipher.getInstance(CIPHER_INSTANCE_NAME);

            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String attribute) {
        try {
            byte[] bytesToEncrypt = attribute.getBytes();
            byte[] encryptedBytes = encryptCipher.doFinal(bytesToEncrypt);
            return java.util.Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String dbData) {
        try {
            byte[] encryptedBytes = java.util.Base64.getDecoder().decode(dbData);
            byte[] decryptedBytes = decryptCipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
