package com.vicky.CLNotesV1.Entity;

import jakarta.persistence.*;

@Entity
public class NoteTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteTagId;

    @ManyToOne
    @JoinColumn(name = "tag_id_fk")
    private TagEntity tagEntity;

    @ManyToOne
    @JoinColumn(name = "note_id_fk")
    private NoteEntity noteEntity;

    private Integer priority;
    private Boolean pinned;

    public NoteTagEntity(Long noteTagId,
                         TagEntity tagEntity,
                         NoteEntity noteEntity,
                         Integer priority,
                         Boolean pinned) {
        this.noteTagId = noteTagId;
        this.tagEntity = tagEntity;
        this.noteEntity = noteEntity;
        this.priority = priority;
        this.pinned = pinned;
    }

    public NoteTagEntity() {
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public Long getNoteTagId() {
        return noteTagId;
    }

    public void setNoteTagId(Long noteTagId) {
        this.noteTagId = noteTagId;
    }

    public TagEntity getTagEntity() {
        return tagEntity;
    }

    public void setTagEntity(TagEntity tagEntity) {
        this.tagEntity = tagEntity;
    }

    public NoteEntity getNoteEntity() {
        return noteEntity;
    }

    public void setNoteEntity(NoteEntity noteEntity) {
        this.noteEntity = noteEntity;
    }
}
