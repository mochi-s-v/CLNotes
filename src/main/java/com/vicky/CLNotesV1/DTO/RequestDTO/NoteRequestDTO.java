package com.vicky.CLNotesV1.DTO.RequestDTO;

import java.util.List;

public class NoteRequestDTO {
    private String title;
    private String content;
    private List<String> tagList;


    public NoteRequestDTO(String title,
                          String content,
                          List<String> tagList) {
        this.title = title;
        this.content = content;
        this.tagList = tagList;
    }

    public NoteRequestDTO() {
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
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
