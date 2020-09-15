package com.salari.accounting.controller;

import com.salari.accounting.model.domain.UserAddRequest;
import com.salari.accounting.model.domain.UserChangePasswordRequest;
import com.salari.accounting.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/apis")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/v1/user")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserAddRequest request){
        return new ResponseEntity<>(userService.addUser(request), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/v1/user")
    public ResponseEntity<?> deleteUser(@Valid @ApiParam(name="id",value = "id") @RequestParam(required = true) Integer id){
        return new ResponseEntity<>(userService.deleteUser(id),HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/v1/user/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody UserChangePasswordRequest request){
        return new ResponseEntity<>(userService.changePassword(request),HttpStatus.OK);
    }

    @GetMapping("/v1/user/login")
    public ResponseEntity<?> login(@Valid @ApiParam(name="username",value = "username") @RequestParam(required = true) String username,
                                   @Valid @ApiParam(name="password",value = "password") @RequestParam(required = true) String password){
        return new ResponseEntity<>(userService.login(username,password),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/v1/user/getAll")
    public ResponseEntity<?> getUsers(){
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }
}
