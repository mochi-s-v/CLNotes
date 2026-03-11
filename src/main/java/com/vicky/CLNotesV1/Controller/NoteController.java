package com.vicky.CLNotesV1.Controller;

import com.vicky.CLNotesV1.DTO.RequestDTO.AddTagRequestDTO;
import com.vicky.CLNotesV1.DTO.RequestDTO.NoteRequestDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.NoteResponseDTO;
import com.vicky.CLNotesV1.Service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final NoteService noteService;

    NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/create")
    public ResponseEntity<NoteResponseDTO> createNote(@RequestBody NoteRequestDTO noteRequestDTO) {
        return new ResponseEntity<>(noteService.create(noteRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/all-notes")
    public ResponseEntity<List<NoteResponseDTO>> getAllNotes() {
        return new ResponseEntity<>(noteService.getAllNotes(), HttpStatus.OK);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteResponseDTO> update(@PathVariable long noteId,
                                                  @RequestBody NoteRequestDTO noteRequestDTO) {
        return new ResponseEntity<>(noteService.update(noteId, noteRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable long noteId) {
        noteService.delete(noteId);
    }

    @DeleteMapping("/{noteId}/deleteTag")
    public ResponseEntity<NoteResponseDTO> deleteTag(@PathVariable long noteId,
                                                     @RequestParam String tagName) {
        return new ResponseEntity<>(noteService.deleteTag(noteId, tagName), HttpStatus.OK);
    }

    @PutMapping("/{noteId}/addTag")
    public ResponseEntity<NoteResponseDTO> addTag(@PathVariable long noteId,
                                                  @RequestBody AddTagRequestDTO addTagRequestDTO) {
        return new ResponseEntity<>(noteService.addTag(noteId, addTagRequestDTO.getTagName()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> filterNotes(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) List<String> tag) {
        return new ResponseEntity<>(noteService.filter(title, content, tag), HttpStatus.OK);
    }
}
