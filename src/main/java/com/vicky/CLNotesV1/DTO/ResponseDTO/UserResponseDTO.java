package com.vicky.CLNotesV1.DTO.ResponseDTO;

import java.util.List;

public class UserResponseDTO {
    private long userId;
    private String userName;
    private String email;
    private List<NoteResponseDTO> notes;

    public UserResponseDTO(long userId, String userName, String email, List<NoteResponseDTO> notes) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.notes = notes;
    }

    public UserResponseDTO() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<NoteResponseDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteResponseDTO> notes) {
        this.notes = notes;
    }
}
