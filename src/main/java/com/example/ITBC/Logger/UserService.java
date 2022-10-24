package com.example.ITBC.Logger;

import org.passay.*;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    public static PasswordValidator validator = new PasswordValidator(
            new LengthRule(8, 16),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new CharacterRule(EnglishCharacterData.Alphabetical, 1),
            new WhitespaceRule()
    );


    public RuleResult validPassword(String password) {
        final char[] charArrayPassword = password.toCharArray();
        return validator.validate(new PasswordData(new String(charArrayPassword)));
    }
}
