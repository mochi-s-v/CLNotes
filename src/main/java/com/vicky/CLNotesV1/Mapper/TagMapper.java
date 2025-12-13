package com.vicky.CLNotesV1.Mapper;

import com.vicky.CLNotesV1.DTO.RequestDTO.TagRequestDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.TagResponseDTO;
import com.vicky.CLNotesV1.Entity.TagEntity;

public class TagMapper {
    private TagMapper(){};

    //Entity -> DTO
    public static TagResponseDTO toDTO(TagEntity tagEntity) {
        if (tagEntity == null) {
            return null;
        }
        return new TagResponseDTO(
                tagEntity.getTagId(),
                tagEntity.getTagName(),
                tagEntity.getNoteTagEntityList()
                        .stream()
                        .map(noteTag -> NoteSummaryMapper.toDTO(noteTag.getNoteEntity()))
                        .toList());
    }
}
