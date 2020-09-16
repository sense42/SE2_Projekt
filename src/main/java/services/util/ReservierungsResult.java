package services.util;

public enum ReservierungsResult {
    INSERAT_ALREADY_RESERVED("Inserat ist bereits Registriert"),
    UNEXPECTED_ERROR("Ein unerwarteter Fehler ist aufgetreten!"),
    RESERVATION_SUCCEEDED("Reservierung erfolgreich durchgeführt");

    private String message;

    ReservierungsResult(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

}
