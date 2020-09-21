package model.dto;

public class VertrieblerDTO extends UserDTO {
    private String email;

    public VertrieblerDTO() {
        email = "";
    }

    public VertrieblerDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
