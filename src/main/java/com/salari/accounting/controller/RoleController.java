package com.salari.accounting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/v1")
public class RoleController {

    @GetMapping("/role")
    public ResponseEntity<?> getRoleById(Short id){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
