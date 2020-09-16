package model.dto;

public class ReservierungDTO {

    private String kundeEmail;
    private int inseratID;

    public ReservierungDTO() {
    }

    public ReservierungDTO(String kundeEmail, int inseratID) {
        this.kundeEmail = kundeEmail;
        this.inseratID = inseratID;
    }

    public String getKundeEmail() {
        return kundeEmail;
    }

    public void setKundeEmail(String kundeEmail) {
        this.kundeEmail = kundeEmail;
    }

    public int getInseratID() {
        return inseratID;
    }

    public void setInseratID(int inseratID) {
        this.inseratID = inseratID;
    }
}
