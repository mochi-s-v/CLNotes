package com.vicky.CLNotesV1.Mapper;

import com.vicky.CLNotesV1.DTO.RequestDTO.UserRequestDTO;
import com.vicky.CLNotesV1.DTO.ResponseDTO.UserResponseDTO;
import com.vicky.CLNotesV1.Entity.UserEntity;

public class UserMapper {

    private UserMapper() {
    }

    //DTO -> Entity
    public static UserEntity toEntity(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRequestDTO.getUserName());
        userEntity.setEmail(userRequestDTO.getEmail());
        return userEntity;
    }

    //Entity -> DTO
    public static UserResponseDTO toDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new UserResponseDTO(
                userEntity.getUserId(),
                userEntity.getUserName(),
                userEntity.getEmail(),
                userEntity.getNotesEntityList()
                        .stream()
                        .map(note -> NoteMapper.toDTO(note))
                        .toList());
    }
}
