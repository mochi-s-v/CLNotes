package com.vicky.CLNotesV1.Service;

import com.vicky.CLNotesV1.DTO.RequestDTO.NoteRequestDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.NoteResponseDTO;
import com.vicky.CLNotesV1.Entity.NoteEntity;
import com.vicky.CLNotesV1.Entity.NoteTagEntity;
import com.vicky.CLNotesV1.Entity.TagEntity;
import com.vicky.CLNotesV1.Entity.UserEntity;
import com.vicky.CLNotesV1.Mapper.NoteMapper;
import com.vicky.CLNotesV1.Repository.NoteRepo;
import com.vicky.CLNotesV1.Repository.TagRepo;
import com.vicky.CLNotesV1.Repository.UserRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NoteService {
    private final NoteRepo noteRepo;
    private final UserRepo userRepo;
    private final TagRepo tagRepo;
    private final UserService userService;
    NoteService(NoteRepo noteRepo,
                UserRepo userRepo,
                TagRepo tagRepo,
                UserService userService ) {
       this.noteRepo = noteRepo;
        this.userRepo = userRepo;
        this.tagRepo = tagRepo;
        this.userService = userService;
    }

    public NoteResponseDTO create(NoteRequestDTO noteRequestDTO) {
        UserEntity userEntity = userService.getAuthenticatedUser();
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setTitle(noteRequestDTO.getTitle());
        noteEntity.setContent(noteRequestDTO.getContent());
        noteEntity.setUserEntity(userEntity);
        userEntity.getNotesEntityList().add(noteEntity);
        if (noteRequestDTO.getTagList() != null && !noteRequestDTO.getTagList().isEmpty()) {
            noteRequestDTO.getTagList()
                    .forEach(tag -> {
                        TagEntity tagEntity = tagRepo.findByTagName(tag)
                                .orElseGet(() -> {
                                    TagEntity newTag = new TagEntity();
                                    newTag.setTagName(tag);
                                    return tagRepo.save(newTag);
                                });
                        NoteTagEntity noteTag = new NoteTagEntity();
                        noteTag.setNoteEntity(noteEntity);
                        noteTag.setTagEntity(tagEntity);
                        noteEntity.getNoteTagEntityList().add(noteTag);
                        tagEntity.getNoteTagEntityList().add(noteTag);
                    });
        }
        return NoteMapper.toDTO(noteRepo.save(noteEntity));
    }

    public NoteResponseDTO update(long noteId, NoteRequestDTO noteRequestDTO) {
        NoteEntity noteEntity = noteRepo.findById(noteId)
                .orElseThrow(() -> new RuntimeException("No matching notesId found"));
        UserEntity userEntity = userService.getAuthenticatedUser();
        if (userEntity.getNotesEntityList().contains(noteEntity)) {
            noteEntity.setTitle(noteRequestDTO.getTitle());
            noteEntity.setContent(noteRequestDTO.getContent());
            return NoteMapper.toDTO(noteRepo.save(noteEntity));
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    public void delete(long noteId) {
        NoteEntity noteEntity = noteRepo.findById(noteId)
                .orElseThrow(() -> new RuntimeException("No matching notesId found"));
        UserEntity userEntity = userService.getAuthenticatedUser();
        if (noteEntity.getNotesId().equals(userEntity.getUserId())) {
            noteRepo.deleteById(noteId);
        } else {
            throw new RuntimeException("No matching notesId found");
        }
    }

    public List<NoteResponseDTO> getAllNotes() {
        UserEntity userEntity = userService.getAuthenticatedUser();
        return userEntity.getNotesEntityList()
                .stream()
                .map(noteEntity -> NoteMapper.toDTO(noteEntity))
                .toList();
    }

    public NoteResponseDTO deleteTag(long noteId, String tagName) {
        NoteEntity noteEntity = noteRepo.findById(noteId)
                .orElseThrow(() -> new RuntimeException("No matching noteId found"));
        UserEntity userEntity = userService.getAuthenticatedUser();
        if (userEntity.getNotesEntityList().contains(noteEntity)) {
            TagEntity tagEntity = tagRepo.findByTagName(tagName)
                    .orElseThrow(() ->  new RuntimeException("No matching tag found"));
            NoteTagEntity toRemove = noteEntity.getNoteTagEntityList()
                    .stream()
                    .filter(noteTag -> noteTag.getTagEntity().getTagId().equals(tagEntity.getTagId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Tag not associated with this note"));
            noteEntity.getNoteTagEntityList().remove(toRemove);
            tagEntity.getNoteTagEntityList().remove(toRemove);
            noteRepo.save(noteEntity);
            return NoteMapper.toDTO(noteEntity);
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    public NoteResponseDTO addTag(long noteId, String tagName) throws RuntimeException{
        NoteEntity noteEntity = noteRepo.findById(noteId)
                .orElseThrow(() -> new RuntimeException("No matching noteId found"));
        UserEntity userEntity = userService.getAuthenticatedUser();
        if(userEntity.getNotesEntityList().contains(noteEntity)) {
            TagEntity tagEntity = tagRepo.findByTagName(tagName)
                    .orElseGet(() -> {
                        TagEntity newTag = new TagEntity();
                        newTag.setTagName(tagName);
                        return tagRepo.save(newTag);
                    });
            boolean alreadyExist = noteEntity.getNoteTagEntityList()
                    .stream()
                    .anyMatch(noteTag -> noteTag.getTagEntity().getTagName().equals(tagEntity.getTagName()));
            if (alreadyExist) {
                return NoteMapper.toDTO(noteEntity);
            }
            NoteTagEntity noteTag = new NoteTagEntity();
            noteTag.setNoteEntity(noteEntity);
            noteTag.setTagEntity(tagEntity);
            noteEntity.getNoteTagEntityList().add(noteTag);
            tagEntity.getNoteTagEntityList().add(noteTag);
            return NoteMapper.toDTO(noteRepo.save(noteEntity));
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    public List<NoteResponseDTO> filter(String title,
                                  String content,
                                  List<String> tag) {
        UserEntity userEntity = userService.getAuthenticatedUser();
        Stream<NoteEntity> stream = userEntity.getNotesEntityList().stream();
        if (title != null && !title.isBlank()) {
            stream = stream.filter(
                    noteEntity -> noteEntity.getTitle()
                            .toLowerCase()
                            .contains(title.toLowerCase())
            );
        }
        if (content != null && !content.isBlank()) {
            stream = stream.filter(
                    noteEntity -> noteEntity.getContent()
                            .toLowerCase()
                            .contains(content.toLowerCase())
            );
        }
        if (tag != null && !tag.isEmpty()) {
            stream = stream.filter(
                    noteEntity -> noteEntity.getNoteTagEntityList()
                            .stream()
                            .map(noteTag -> noteTag.getTagEntity().getTagName())
                            .collect(Collectors.toSet())
                            .containsAll(tag)
            );
        }
        return stream
                .map(noteEntity -> NoteMapper.toDTO(noteEntity))
                .toList();
    }
}
