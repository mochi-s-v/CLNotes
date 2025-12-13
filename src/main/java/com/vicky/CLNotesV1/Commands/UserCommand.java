//package com.vicky.CLNotesV1.Commands;
//
//import com.vicky.CLNotesV1.DTO.RequestDTO.UserRequestDTO;
//import com.vicky.CLNotesV1.DTO.ResponseDTO.UserResponseDTO;
//import com.vicky.CLNotesV1.Service.UserService;
//import com.vicky.CLNotesV1.Utility.GenericTableUtils;
//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellOption;
//import org.springframework.shell.table.Table;
//
//@ShellComponent
//public class UserCommand {
//    private final UserService userService;
//    public UserCommand(UserService userService) {
//        this.userService = userService;
//    }
//
//    @ShellMethod(key = "user-create", value = "creates new user")
//    public Table create(@ShellOption({"--username", "-u"}) String userName,
//                        @ShellOption({"--email", "-e"}) String email) {
//        UserRequestDTO userRequestDTO = new UserRequestDTO(userName, email);
//        return GenericTableUtils.toTable(userService.createUser(userRequestDTO));
//    }
//
//    @ShellMethod(key = "user-update", value = "update an existing user")
//    public Table update(@ShellOption({"--userId", "-id"}) long userId,
//                        @ShellOption({"--username", "-u"}) String userName,
//                        @ShellOption({"--email", "-e"}) String email){
//        UserRequestDTO userRequestDTO = new UserRequestDTO(userName, email);
//        return GenericTableUtils.toTable(userService.updateUser(userId, userRequestDTO));
//    }
//
//    @ShellMethod(key = "user-delete", value = "delete user")
//    public void delete(@ShellOption({"--userId", "-id"}) long userId) {
//        userService.deleteUser(userId);
//    }
//
//    @ShellMethod(key = "user-get", value = "get user details")
//    public Table getUserById(@ShellOption({"--userId", "-id"}) long userId) {
//        return GenericTableUtils.toTable(userService.getUser(userId));
//    }
//}
