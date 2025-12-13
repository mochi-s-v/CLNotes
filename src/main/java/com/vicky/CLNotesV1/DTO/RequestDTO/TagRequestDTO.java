package com.vicky.CLNotesV1.DTO.RequestDTO;

public class TagRequestDTO {
    private String tag;

    public TagRequestDTO(String tag) {
        this.tag = tag;
    }

    public TagRequestDTO() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
