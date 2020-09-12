package com.salari.accounting.controller;

import com.salari.accounting.model.domain.UserAddRequest;
import com.salari.accounting.model.domain.UserChangePasswordRequest;
import com.salari.accounting.model.domain.UserLoginRequest;
import com.salari.accounting.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/apis/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/v1/user")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserAddRequest request){
        return new ResponseEntity<>(userService.addUser(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/user")
    public ResponseEntity<?> deleteUser(@Valid @ApiParam(name="id",value = "id") @RequestParam(required = true) Integer id){
        return new ResponseEntity<>(userService.deleteUser(id),HttpStatus.OK);
    }

    @PutMapping("/v1/user/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody UserChangePasswordRequest request){
        return new ResponseEntity<>(userService.changePassword(request),HttpStatus.OK);
    }

    @GetMapping("/v1/user/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest request){
        return new ResponseEntity<>(userService.login(request),HttpStatus.OK);
    }

    public ResponseEntity<?> getUsers(){
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }
}
