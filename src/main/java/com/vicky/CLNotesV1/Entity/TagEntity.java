package com.vicky.CLNotesV1.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;
    @Column(unique = true)
    private String tagName;

    @OneToMany(mappedBy = "tagEntity", fetch = FetchType.EAGER)
    private List<NoteTagEntity> noteTagEntityList = new ArrayList<>();

    public TagEntity(Long tagId, String tagName,
                     List<NoteTagEntity> noteTagEntityList) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.noteTagEntityList = noteTagEntityList;
    }

    public TagEntity() {
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<NoteTagEntity> getNoteTagEntityList() {
        return noteTagEntityList;
    }

    public void setNoteTagEntityList(List<NoteTagEntity> noteTagEntityList) {
        this.noteTagEntityList = noteTagEntityList;
    }
}
