package com.vicky.CLNotesV1.Controller;

import com.vicky.CLNotesV1.DTO.RequestDTO.LoginRequestDTO;
import com.vicky.CLNotesV1.DTO.RequestDTO.UserRequestDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.UserResponseDTO;
import com.vicky.CLNotesV1.Service.UserService;
import com.vicky.CLNotesV1.Utility.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    UserController(JWTUtil jwtUtil,
                   UserService userService,
                   AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.createUser(userRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.updateUser(userRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping
    public void delete() {
        userService.deleteUser();
    }

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUserById() {
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getUsername(),
                    loginRequestDTO.getPassword()
                    ));
            return jwtUtil.generateToken(loginRequestDTO.getUsername());
        } catch (Exception e) {
            return "Username or password is wrong";
        }
    }
}
