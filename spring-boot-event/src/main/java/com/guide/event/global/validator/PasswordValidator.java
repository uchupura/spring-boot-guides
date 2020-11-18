package com.guide.event.global.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        /**
         * ^ represents starting character of the string.
         * (?=.*[0-9]) represents a digit must occur at least once.
         * (?=.*[a-z]) represents a lower case alphabet must occur at least once.
         * (?=.*[A-Z]) represents an upper case alphabet that must occur at least once.
         * (?=.*[@#$%^&-+=()] represents a special character that must occur at least once.
         * (?=\\S+$) white spaces don’t allowed in the entire string.
         * .{8, 20} represents at least 8 characters and at most 20 characters.
         * $ represents the end of the string.
         */
        // Regex to check valid password.
        StringBuilder regex = new StringBuilder();
        regex.append("^");
        regex.append("(?=.*[0-9])");
        regex.append("(?=.*[a-z])");
        regex.append("(?=.*[A-Z])");
        regex.append("(?=.*[!@#$%^&+=])");
        regex.append("(?=\\S+$)");
        regex.append(".{8,20}");
        regex.append("$");

        // Compile the ReGex
        Pattern p = Pattern.compile(regex.toString());

        // If the password is empty
        // return false
        if (value == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(value);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }
}
