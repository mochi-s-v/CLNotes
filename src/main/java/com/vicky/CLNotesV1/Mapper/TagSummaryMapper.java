package com.vicky.CLNotesV1.Mapper;

import com.vicky.CLNotesV1.DTO.ResponseDTO.TagSummaryDTO;
import com.vicky.CLNotesV1.Entity.NoteTagEntity;
import com.vicky.CLNotesV1.Entity.TagEntity;

public class TagSummaryMapper {

    private TagSummaryMapper() {
    }

    //Entity -> DTO
    public static TagSummaryDTO toDTO(TagEntity tagEntity) {
        if (tagEntity == null) {
            return null;
        }

        return new TagSummaryDTO(
                tagEntity.getTagId(),
                tagEntity.getTagName());
    }
}
