package com.vicky.CLNotesV1.DTO.ResponseDTO;

import java.util.Collections;
import java.util.List;

public class NoteResponseDTO {
    private long notesId;
    private String title;
    private String content;
    private List<TagSummaryDTO> tags;


    public NoteResponseDTO(long notesId,
                           String title,
                           String content,
                           List<TagSummaryDTO> tags) {
        this.notesId = notesId;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public NoteResponseDTO() {
    }

    public List<TagSummaryDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagSummaryDTO> tags) {
        this.tags = tags;
    }

    public long getNotesId() {
        return notesId;
    }

    public void setNotesId(long notesId) {
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
}
