package com.project.user_service.controllers;


import com.project.user_service.exeptions.UserAlreadyExistsExeption;
import com.project.user_service.exeptions.UserIdIsNull;
import com.project.user_service.exeptions.UserNotExistsException;
import com.project.user_service.models.entities.User;
import com.project.user_service.models.requests.UserRequestDTO;

import com.project.user_service.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public User createPerson(@Valid @RequestBody UserRequestDTO person) {
        return userService.save(person);
    }

    @GetMapping
    public Optional<User> getPerson(@RequestParam long ssn) {
        return userService.get(ssn);
    }

    @DeleteMapping
    public void deletePerson(@RequestParam long ssn){
        userService.delete(ssn); // is a void action so no data to return
    }

    @PatchMapping
    public ResponseEntity<String> updatePerson(@Valid @RequestBody UserRequestDTO person){
        int updatedEntries = userService.updatePerson(person);
        return new ResponseEntity<>("Updated entries: " + updatedEntries, HttpStatus.CREATED);
    }

    @ExceptionHandler(UserAlreadyExistsExeption.class)
    public ResponseEntity<?> handleUserAlreadyExisting(UserAlreadyExistsExeption ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<?> handleNoUserException(UserNotExistsException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UserIdIsNull.class)
    public ResponseEntity<?> handleUserIdIsNull(UserIdIsNull ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
