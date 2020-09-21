package services.util;

public enum LoginResult {

    EMAIL_EMPTY("Bitte geben Sie eine E-Mail Adresse an."),
    PASSWORD_EMPTY("Bitte geben Sie ein Passwort ein."),
    EMAIL_PASSWORD_WRONG("Die kombination aus Email und Passwort ist falsch."),
    LOGIN_ENDKUNDE_SUCCEEDED("Login als Endkunde erfolgreich."),
    LOGIN_VERTRIBLER_SUCCEEDED("Login als Vertriebler erfolgreich."),
    LOGIN_UNEXPECTED_ERROR("Ein unerwarteter Fehler ist aufgetreten!");

    private final String message;

    LoginResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
