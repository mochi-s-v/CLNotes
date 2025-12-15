package com.vicky.CLNotesV1.DTO.RequestDTO;

public class UserLoginRequestDTO {
    private String userName;
    private String email;

    public UserLoginRequestDTO(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public UserLoginRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
