package com.vicky.CLNotesV1.DTO.ResponseDTO;

public class NoteSummaryDTO {
    private Long noteId;
    private String title;

    public NoteSummaryDTO(Long noteId, String title) {
        this.noteId = noteId;
        this.title = title;
    }

    public NoteSummaryDTO() {}

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}