package services.util;

public enum RegistrationResult {

    EMAIL_ALREADY_EXISTS("Diese E-Mail Adresse ist bereits vergeben."),
    EMAIL_NOT_VALID("Dies ist keine gültige E-Mail Adresse."),
    EMAIL_EMPTY("Bitte geben Sie eine E-Mail Adresse an."),
    PASSWORD_SHORT("Ihr Passwort muss mindestens aus 7 Zeichen bestehen."),
    PASSWORD_EMPTY("Bitte geben Sie ein Passwort ein."),
    PASSWORD_DIFFERENT("Die eingegebenen Passwörter stimmen nicht überein."),
    FIRSTNAME_EMPTY("Bitte geben Sie Ihren Vornamen ein."),
    FIRSTNAME_SHORT("Dieser Vorname ist zu kurz."),
    SURNAME_EMPTY("Bitte geben Sie Ihren Nachnamen ein."),
    SURNAME_SHORT("Dieser Nachname ist zu kurz."),

    REGISTRATION_SUCCEEDED("Registrierung erfolgreich! Sie können sich nun einloggen."),
    VERTRIEBLER_REGISTERED("Registrierung als Vertriebler erfolgreich! Sie können sich nun einloggen."),
    ENDKUNDE_REGISTERED("Registrierung als Endkunde erfolgreich! Sie können sich nun einloggen."),

    UNEXPECTED_ERROR("Ein unerwarteter Fehler ist aufgetreten.");


    private String message;

    RegistrationResult(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
