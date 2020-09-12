package com.salari.accounting.service;

import com.salari.accounting.model.domain.UserAddRequest;
import com.salari.accounting.model.dto.BaseDTO;
import com.salari.accounting.model.dto.MetaDTO;
import com.salari.accounting.model.entity.User;
import com.salari.accounting.model.mapper.UserMapper;
import com.salari.accounting.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.salari.accounting.service.GlobalService.serviceExceptionBuilder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public BaseDTO addUser(UserAddRequest request){

        Optional<User> duplicateUser= userRepository.findUserByUserNameOrNationalCode(request.getUserName(),request.getNationalCode());
        if(duplicateUser.isPresent())
            throw serviceExceptionBuilder("duplicate.user", HttpStatus.BAD_REQUEST);

            User user=User.builder()
                    .userName(request.getUserName())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .nationalCode(request.getNationalCode())
                    .password(request.getPassword())
                    .roleId(request.getRoleId())
                    .build();
            userRepository.save(user);

            return BaseDTO.builder()
                    .metaDTO(MetaDTO.getInstance())
                    .data(userMapper.USER_DTO(user))
                    .build();
    }
}
