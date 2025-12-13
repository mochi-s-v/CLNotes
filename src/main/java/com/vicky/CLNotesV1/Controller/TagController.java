package com.vicky.CLNotesV1.Controller;

import com.vicky.CLNotesV1.DTO.ResponseDTO.NoteResponseDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.NoteSummaryDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.TagResponseDTO;
import com.vicky.CLNotesV1.Service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;
    TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TagResponseDTO>> findAll(@RequestParam int page,
                                                        @RequestParam int size) {
        return new ResponseEntity<>(tagService.allTags(page, size), HttpStatus.OK);
    }

    @GetMapping("/notes")
    public ResponseEntity<List<NoteSummaryDTO>> findNotesByTag(@RequestParam String tagName,
                                                               @RequestParam int page,
                                                               @RequestParam int size) {
        return new ResponseEntity<>(tagService.getNotesByTag(tagName, page, size), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TagResponseDTO>> searchTags(@RequestParam String keyword) {
        return new ResponseEntity<>(tagService.searchTags(keyword), HttpStatus.OK);
    }
}
