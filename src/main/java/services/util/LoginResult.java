package services.util;

public enum LoginResult {

    EMAIL_EMPTY("Bitte geben Sie eine E-Mail Adresse an."),
    PASSWORD_EMPTY("Bitte geben Sie ein Passwort ein."),
    EMAIL_PASSWORD_WRONG("Die kombination aus Email und PAsswort ist falsch."),
    LOGIN_ENDKUNDE_SUCCEEDED("Login als Endkunde erfolgreich."),
    LOGIN_VERTRIBLER_SUCCEEDED("Login als Vertriebler erfolgreich.");

    private String message;

    LoginResult(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

}
