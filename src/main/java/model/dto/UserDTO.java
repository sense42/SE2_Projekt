package model.dto;

public class UserDTO {

    private String email;
    private String password;
    private String vorname;
    private String nachname;

    public UserDTO() {
        email = "";
        password = "";
        vorname = "";
        nachname = "";
    }


    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDTO(String email, String password, String vorname, String nachname) {
        this.email = email;
        this.password = password;
        this.vorname = vorname;
        this.nachname = nachname;
    }
}
