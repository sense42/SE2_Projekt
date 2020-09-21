package model.dto;

public class InseratDTO {

    private int id;
    private String marke;
    private int baujahr;
    private String beschreibung;
    private String email;

    public InseratDTO() {
    }

    public InseratDTO(String email) {
        this.email = email;
    }

    public InseratDTO(int id, String marke, int baujahr, String beschreibung, String email) {
        this.id = id;
        this.marke = marke;
        this.baujahr = baujahr;
        this.beschreibung = beschreibung;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public int getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(int baujahr) {
        this.baujahr = baujahr;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getMarke();
    }


}
