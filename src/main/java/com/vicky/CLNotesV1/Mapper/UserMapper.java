package com.vicky.CLNotesV1.Mapper;

import com.vicky.CLNotesV1.DTO.RequestDTO.UserLoginRequestDTO;
import com.vicky.CLNotesV1.DTO.RequestDTO.UserRegisterRequestDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.UserResponseDTO;
import com.vicky.CLNotesV1.Entity.UserEntity;

public class UserMapper {

    private UserMapper() {
    }

    //DTO -> Entity
    public static UserEntity RegisterToEntity(UserRegisterRequestDTO userRegisterRequestDTO) {
        if (userRegisterRequestDTO == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegisterRequestDTO.getUsername());
        userEntity.setEmail(userRegisterRequestDTO.getEmail());
        userEntity.setPassword(userRegisterRequestDTO.getPassword());
        return userEntity;
    }


    public static UserEntity LoginToEntity(UserLoginRequestDTO userLoginRequestDTO) {
        if (userLoginRequestDTO == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userLoginRequestDTO.getUserName());
        userEntity.setEmail(userLoginRequestDTO.getEmail());
        return userEntity;
    }

    //Entity -> DTO
    public static UserResponseDTO toDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new UserResponseDTO(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getNotesEntityList()
                        .stream()
                        .map(note -> NoteMapper.toDTO(note))
                        .toList());
    }
}
