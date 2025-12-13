package com.vicky.CLNotesV1.DTO.ResponseDTO;

import java.util.List;

public class TagResponseDTO {
    private Long tagId;
    private String tagName;
    private List<NoteSummaryDTO> notes;

    public TagResponseDTO(Long tagId,
                          String tagName,
                          List<NoteSummaryDTO> notes) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.notes = notes;
    }

    public TagResponseDTO() {
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

    public List<NoteSummaryDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteSummaryDTO> notes) {
        this.notes = notes;
    }
}
