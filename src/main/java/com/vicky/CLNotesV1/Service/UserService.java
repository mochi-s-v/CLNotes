package com.vicky.CLNotesV1.Service;

import com.vicky.CLNotesV1.DTO.RequestDTO.UserLoginRequestDTO;
import com.vicky.CLNotesV1.DTO.RequestDTO.UserRegisterRequestDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.UserResponseDTO;
import com.vicky.CLNotesV1.Entity.UserEntity;
import com.vicky.CLNotesV1.Mapper.UserMapper;
import com.vicky.CLNotesV1.Repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    UserService(UserRepo userRepo,
                PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(userRegisterRequestDTO.getUsername());
        user.setEmail(userRegisterRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterRequestDTO.getPassword()));
        user.setRole("USER");
        return UserMapper.toDTO(userRepo.save(user));
    }

    public UserResponseDTO updateUser(long userId, UserLoginRequestDTO userLoginRequestDTO) {
        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("No Matching userId found"));
        userEntity.setUsername(userLoginRequestDTO.getUserName());
        userEntity.setEmail(userLoginRequestDTO.getEmail());
        return UserMapper.toDTO(userRepo.save(userEntity));
    }

    public void deleteUser(long userId) {
        if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
        } else {
            throw new RuntimeException("No matching userId found");
        }
    }

    public UserResponseDTO getUser(long userId) {
        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("No Matching userId found"));
        return UserMapper.toDTO(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No userName found"));
    }
}
