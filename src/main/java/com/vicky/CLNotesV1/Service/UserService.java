package com.vicky.CLNotesV1.Service;

import com.vicky.CLNotesV1.DTO.RequestDTO.UserRequestDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.UserResponseDTO;
import com.vicky.CLNotesV1.Entity.Roles;
import com.vicky.CLNotesV1.Entity.UserEntity;
import com.vicky.CLNotesV1.Mapper.UserMapper;
import com.vicky.CLNotesV1.Repository.UserRepo;
import com.vicky.CLNotesV1.Utility.GetCurrentUsername;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        UserEntity userEntity = UserMapper.toEntity(userRequestDTO);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Roles.USER);
        return UserMapper.toDTO(userRepo.save(userEntity));
    }

    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        UserEntity userEntity = userRepo.findByUsername(GetCurrentUsername.get())
                .orElseThrow(() -> new RuntimeException("No Matching userId found"));
        userEntity.setUsername(userRequestDTO.getUsername());
        userEntity.setEmail(userRequestDTO.getEmail());
        return UserMapper.toDTO(userRepo.save(userEntity));
    }

    public void deleteUser() {
        UserEntity userEntity = userRepo.findByUsername(GetCurrentUsername.get())
                .orElseThrow(() -> new RuntimeException("No Matching userId found"));
        userRepo.deleteById(userEntity.getUserId());
    }

    public UserResponseDTO getUser() {
        UserEntity userEntity = userRepo.findByUsername(GetCurrentUsername.get())
                .orElseThrow(() -> new RuntimeException("No Matching userId found"));
        return UserMapper.toDTO(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepo.findByUsername(username)
               .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public UserEntity getAuthenticatedUser() {
        String username = GetCurrentUsername.get();
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found"));
    }
}