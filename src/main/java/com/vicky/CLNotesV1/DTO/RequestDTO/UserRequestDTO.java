package com.vicky.CLNotesV1.DTO.RequestDTO;

public class UserRequestDTO {
    private String username;
    private String email;
    private String password;

    public UserRequestDTO(String userName, String email, String password) {
        this.username = userName;
        this.email = email;
        this.password = password;
    }

    public UserRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
