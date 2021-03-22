package com.guide.oauth2.password.auth.config.support;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static org.springframework.util.StringUtils.isEmpty;

@Converter
public class StringCryptoConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (!isEmpty(attribute)) {
            return Cryptor.encrypt(attribute);
        }
        return attribute;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (!isEmpty(dbData)) {
            return Cryptor.decrypt(dbData);
        }
        return dbData;
    }

    /*private String encrypt(String attribute)  {
        try {
            return encryptor.encrypt(attribute);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private String decrypt(String dbData)  {
        try {
            return encryptor.decrypt(dbData);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }*/
}
