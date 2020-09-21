package model.dto;

public class EndkundeDTO extends UserDTO {

    private String email;

    public EndkundeDTO() {
        email = "";
    }

    public EndkundeDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
