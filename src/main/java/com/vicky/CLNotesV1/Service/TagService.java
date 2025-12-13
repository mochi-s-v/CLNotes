package com.vicky.CLNotesV1.Service;

import com.vicky.CLNotesV1.DTO.ResponseDTO.NoteSummaryDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.TagResponseDTO;
import com.vicky.CLNotesV1.Entity.TagEntity;
import com.vicky.CLNotesV1.Mapper.NoteMapper;
import com.vicky.CLNotesV1.Mapper.NoteSummaryMapper;
import com.vicky.CLNotesV1.Mapper.TagMapper;
import com.vicky.CLNotesV1.Repository.NoteTagRepo;
import com.vicky.CLNotesV1.Repository.TagRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepo tagRepo;
    private final NoteTagRepo noteTagRepo;
    TagService(TagRepo tagRepo,
               NoteTagRepo noteTagRepo) {
        this.tagRepo = tagRepo;
        this.noteTagRepo = noteTagRepo;
    }

    public List<TagResponseDTO> allTags(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tagRepo.findAll(pageable)
                .getContent()
                .stream()
                .map(tagEntity -> TagMapper.toDTO(tagEntity))
                .toList();
    }

    public List<NoteSummaryDTO> getNotesByTag(String tagName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        TagEntity tagEntity = tagRepo.findByTagName(tagName)
                .orElseThrow(() -> new RuntimeException("No tag found"));
        return noteTagRepo.findByTagEntity(tagEntity, pageable)
                .getContent()
                .stream()
                .map(noteTag -> NoteSummaryMapper.toDTO(noteTag.getNoteEntity()))
                .toList();
    }

    public List<TagResponseDTO> searchTags(String keyword) {
        return tagRepo.findByTagNameContainingIgnoreCase(keyword)
                .stream()
                .map(tagEntity -> TagMapper.toDTO(tagEntity))
                .toList();
    }
}
