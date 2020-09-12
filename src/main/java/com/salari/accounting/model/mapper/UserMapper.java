package com.salari.accounting.model.mapper;

import com.salari.accounting.model.dto.UserDTO;
import com.salari.accounting.model.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO USER_DTO(User user);
}
