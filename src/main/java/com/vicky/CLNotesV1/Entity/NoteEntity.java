package com.vicky.CLNotesV1.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notesId;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id_fk")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "noteEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<NoteTagEntity> noteTagEntityList = new ArrayList<>();

    public NoteEntity() {
    }

    public NoteEntity(Long notesId,
                      String title,
                      String content,
                      UserEntity userEntity,
                      List<NoteTagEntity> noteTagEntityList) {
        this.notesId = notesId;
        this.title = title;
        this.content = content;
        this.userEntity = userEntity;
        this.noteTagEntityList = noteTagEntityList;
    }

    public List<NoteTagEntity> getNoteTagEntityList() {
        return noteTagEntityList;
    }

    public void setNoteTagEntityList(List<NoteTagEntity> noteTagEntityList) {
        this.noteTagEntityList = noteTagEntityList;
    }

    public Long getNotesId() {
        return notesId;
    }

    public void setNotesId(Long notesId) {
        this.notesId = notesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
