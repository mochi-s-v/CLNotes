package com.vicky.CLNotesV1.Controller;

import com.vicky.CLNotesV1.DTO.RequestDTO.UserLoginRequestDTO;
import com.vicky.CLNotesV1.DTO.RequestDTO.UserRegisterRequestDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.UserResponseDTO;
import com.vicky.CLNotesV1.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO) {
        return new ResponseEntity<>(userService.createUser(userRegisterRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable long userId, @RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        return new ResponseEntity<>(userService.updateUser(userId, userLoginRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @GetMapping("/health")
    public String health() {
        return "Healthy";
    }
}
