package com.vicky.CLNotesV1.Service;

import com.vicky.CLNotesV1.DTO.RequestDTO.UserRequestDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.UserResponseDTO;
import com.vicky.CLNotesV1.Entity.UserEntity;
import com.vicky.CLNotesV1.Mapper.UserMapper;
import com.vicky.CLNotesV1.Repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        UserEntity userEntity = UserMapper.toEntity(userRequestDTO);
        return UserMapper.toDTO(userRepo.save(userEntity));
    }

    public UserResponseDTO updateUser(long userId, UserRequestDTO userRequestDTO) {
        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("No Matching userId found"));
        userEntity.setUserName(userRequestDTO.getUserName());
        userEntity.setEmail(userRequestDTO.getEmail());
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

}
