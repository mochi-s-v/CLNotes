package com.vicky.CLNotesV1.DTO.RequestDTO;

public class AddTagRequestDTO {

    private String tagName;

    public AddTagRequestDTO(String tagName) {
        this.tagName = tagName;
    }

    public AddTagRequestDTO() {
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
