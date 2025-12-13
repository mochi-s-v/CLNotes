package com.vicky.CLNotesV1.Mapper;

import com.vicky.CLNotesV1.DTO.ResponseDTO.NoteSummaryDTO;
import com.vicky.CLNotesV1.Entity.NoteEntity;

public class NoteSummaryMapper {
    public NoteSummaryMapper() {
    }

    //Entity -> DTO
    public static NoteSummaryDTO toDTO(NoteEntity noteEntity) {
        if (noteEntity == null) {
            return null;
        }
        return new NoteSummaryDTO(
                noteEntity.getNotesId(),
                noteEntity.getTitle()
        );
    }

}
