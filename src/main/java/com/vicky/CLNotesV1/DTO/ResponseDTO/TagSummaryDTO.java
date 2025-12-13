package com.vicky.CLNotesV1.DTO.ResponseDTO;

public class TagSummaryDTO {
    private Long TagId;
    private String tagName;


    public TagSummaryDTO(Long TagId, String tagName) {
        this.TagId = TagId;
        this.tagName = tagName;
    }

    public TagSummaryDTO() {
    }

    public Long getTagId() {
        return TagId;
    }

    public void setTagId(Long tagId) {
        TagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
