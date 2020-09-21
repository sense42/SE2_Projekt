package services.util;

public enum LogoutResult {

    LOGOUT_SUCCEEDED("Logout erfolgreich."),
    LOGOUT_ERROR("Logout fehlgeschlagen.");

    private final String message;

    LogoutResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
