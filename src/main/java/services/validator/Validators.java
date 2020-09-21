package services.validator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

public class Validators {

    public static Validator<String> getemailValidator() {
        Validator<String> emailValidator = new com.vaadin.data.Validator<String>() {
            @Override
            public ValidationResult apply(String s, ValueContext valueContext) {
                if (!s.contains("@")) {
                    return ValidationResult.error("In der E-Mail Adresse fehlt das '@' Zeichen!");
                }
                if (!s.contains(".de") && !s.contains(".com")) {
                    return ValidationResult.error("es werden nur E-mail Adressen der Domein '.de' und '.com' unterstützt.");
                }
                return ValidationResult.ok();
            }
        };
        return emailValidator;
    }
}
