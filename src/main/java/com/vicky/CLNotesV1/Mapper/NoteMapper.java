package com.vicky.CLNotesV1.Mapper;

import com.vicky.CLNotesV1.DTO.RequestDTO.NoteRequestDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.NoteResponseDTO;
import com.vicky.CLNotesV1.Entity.NoteEntity;

public class NoteMapper {
    private NoteMapper(){}

    // DTO -> Entity
    public static NoteEntity toEntity(NoteRequestDTO noteRequestDTO) {
        if (noteRequestDTO == null) {
            return null;
        }

        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setContent(noteRequestDTO.getContent());
        noteEntity.setTitle(noteRequestDTO.getTitle());
        // IMPORTANT:
        // Tags are NOT mapped here because service layer handles:
        // - find tag by name
        // - create tag if not exists
        // - create NoteTagEntity objects
        // - set relationship correctly
        return noteEntity;
    }

    // Entity -> DTO
    public static NoteResponseDTO toDTO(NoteEntity noteEntity) {
        if (noteEntity == null) {
            return null;
        }
        return new NoteResponseDTO(
                noteEntity.getNotesId(),
                noteEntity.getTitle(),
                noteEntity.getContent(),
                noteEntity.getNoteTagEntityList()
                        .stream()
                        .map(noteTag -> TagSummaryMapper.toDTO(noteTag.getTagEntity()))
                        .toList());
    }
}
