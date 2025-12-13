//package com.vicky.CLNotesV1.Commands;
//
//import com.vicky.CLNotesV1.Service.TagService;
//import com.vicky.CLNotesV1.Utility.GenericTableUtils;
//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellOption;
//import org.springframework.shell.table.Table;
//
//import java.util.List;
//
//@ShellComponent
//public class TagCommand {
//
//    private final TagService tagService;
//
//    public TagCommand(TagService tagService) {
//        this.tagService = tagService;
//    }
//
//    @ShellMethod(key = "tag-all", value = "List all tags with pagination")
//    public Table listAllTags(@ShellOption(value = {"--page", "-p"}, defaultValue = "0") int page,
//                              @ShellOption(value = {"--size", "-s"}, defaultValue = "10") int size) {
//        return GenericTableUtils.toTable(tagService.allTags(page, size));
//    }
//
//
//    @ShellMethod(key = "tag-notes", value = "Get notes associated with a tag")
//    public Table getNotesByTag(@ShellOption(value = {"--tag", "-t"}) String tagName,
//                                @ShellOption(value = {"--page", "-p"}, defaultValue = "0") int page,
//                                @ShellOption(value = {"--size", "-s"}, defaultValue = "10") int size) {
//        return GenericTableUtils.toTable(tagService.getNotesByTag(tagName, page, size));
//    }
//
//
//    @ShellMethod(key = "tag-search", value = "Search tags containing a keyword")
//    public Table searchTags(@ShellOption(value = {"--keyword", "-k"}) String keyword) {
//        return GenericTableUtils.toTable(tagService.searchTags(keyword));
//    }
//}
