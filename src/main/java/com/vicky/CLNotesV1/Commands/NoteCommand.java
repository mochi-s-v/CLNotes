//package com.vicky.CLNotesV1.Commands;
//
//import com.vicky.CLNotesV1.DTO.RequestDTO.AddTagRequestDTO;
//import com.vicky.CLNotesV1.DTO.RequestDTO.NoteRequestDTO;
//import com.vicky.CLNotesV1.DTO.ResponseDTO.NoteResponseDTO;
//import com.vicky.CLNotesV1.Service.NoteService;
//import com.vicky.CLNotesV1.Utility.GenericTableUtils;
//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellOption;
//import org.springframework.shell.table.Table;
//
//import java.util.Arrays;
//import java.util.List;
//
//@ShellComponent
//public class NoteCommand {
//
//    private final NoteService noteService;
//
//    public NoteCommand(NoteService noteService) {
//        this.noteService = noteService;
//    }
//
//    @ShellMethod(key = "note-create", value = "Create a new note for a user")
//    public Table createNote(@ShellOption({"--userId", "-u"}) long userId,
//                            @ShellOption({"--title", "-t"}) String title,
//                            @ShellOption({"--content", "-c"}) String content,
//                            @ShellOption(value = {"--tags"}, defaultValue = ShellOption.NULL) String tags) {
//        List<String> tagList = (tags == null ? null : Arrays.asList(tags.split(",")));
//        NoteRequestDTO dto = new NoteRequestDTO(title, content, tagList);
//        NoteResponseDTO created = noteService.create(userId, dto);
//        return GenericTableUtils.toTable(created);
//    }
//
//    @ShellMethod(key = "note-get", value = "Get a note by its ID")
//    public Table getNote(@ShellOption({"--noteId", "-id"}) long noteId) {
//        return GenericTableUtils.toTable(noteService.getNoteById(noteId));
//    }
//
//    @ShellMethod(key = "note-update", value = "Update an existing note")
//    public Table updateNote(@ShellOption({"--noteId", "-id"}) long noteId,
//                             @ShellOption({"--title", "-t"}) String title,
//                             @ShellOption({"--content", "-c"}) String content) {
//        NoteRequestDTO dto = new NoteRequestDTO(title, content, null);
//        return GenericTableUtils.toTable(noteService.update(noteId, dto));
//    }
//
//    @ShellMethod(key = "note-delete", value = "Delete a note")
//    public String deleteNote(@ShellOption({"--noteId", "-id"}) long noteId) {
//        noteService.delete(noteId);
//        return "Note deleted: " + noteId;
//    }
//
//    @ShellMethod(key = "note-add-tag", value = "Add a tag to a note")
//    public Table addTag(@ShellOption({"--noteId", "-id"}) long noteId,
//                         @ShellOption({"--tag", "-tg"}) String tagName) {
//        AddTagRequestDTO dto = new AddTagRequestDTO(tagName);
//        return GenericTableUtils.toTable(
//                noteService.addTag(noteId, dto.getTagName())
//        );
//    }
//
//    @ShellMethod(key = "note-delete-tag", value = "Delete a tag from a note")
//    public Table deleteTag(@ShellOption({"--noteId", "-id"}) long noteId,
//                            @ShellOption({"--tag", "-tg"}) String tagName) {
//        return GenericTableUtils.toTable(
//                noteService.deleteTag(noteId, tagName)
//        );
//    }
//
//    @ShellMethod(key = "note-filter", value = "Filter notes by title, content, or tags")
//    public Table filter(@ShellOption({"--userId", "-u"}) long userId,
//                         @ShellOption(value = {"--title"}, defaultValue = ShellOption.NULL) String title,
//                         @ShellOption(value = {"--content"}, defaultValue = ShellOption.NULL) String content,
//                         @ShellOption(value = {"--tags"}, defaultValue = ShellOption.NULL) String tags) {
//        List<String> tagList = (tags == null ? null : Arrays.asList(tags.split(",")));
//        return GenericTableUtils.toTable(
//                noteService.filter(userId, title, content, tagList)
//        );
//    }
//}
