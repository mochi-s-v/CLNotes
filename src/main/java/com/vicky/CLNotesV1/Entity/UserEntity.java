package com.vicky.CLNotesV1.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String email;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<NoteEntity> notesEntityList = new ArrayList<>();

    public UserEntity(Long userId,
                      String userName,
                      String email,
                      List<NoteEntity> notesEntityList) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.notesEntityList = notesEntityList;
    }

    public UserEntity() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<NoteEntity> getNotesEntityList() {
        return notesEntityList;
    }

    public void setNotesEntityList(List<NoteEntity> notesEntityList) {
        this.notesEntityList = notesEntityList;
    }
}
