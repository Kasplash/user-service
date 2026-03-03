package com.project.user_service.services;

import com.project.user_service.repositories.UserRepository;
import com.project.user_service.exeptions.UserNotExistsException;
import com.project.user_service.models.entities.User;
import com.project.user_service.models.requests.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User save(UserRequestDTO userRequestDTO) {
        long ssn = userRequestDTO.ssn();
        String name = userRequestDTO.name();
        String address = userRequestDTO.address();
        int age = userRequestDTO.age();

        User userEntity = new User(ssn, name, address, age);
/*
        if(userRepository.existsById(ssn)){
            throw new UserAlreadyExistsExeption("User already exists");
        }

 */
        return userRepository.save(userEntity);
    }

    public Optional<User> get(long ssn) {
        Optional<User> result = userRepository.findById(ssn);
        if(result.isEmpty()){
            throw new UserNotExistsException("No user found");
        }
        return result;
    }

    public void delete(long ssn){
        if(!userRepository.existsById(ssn)) {
            throw new UserNotExistsException("No user to delete");
        }
        userRepository.deleteById(ssn); // function returns void so might be misleading
    }

    public int updatePerson(UserRequestDTO person){
        long ssn = person.ssn();
        String address = person.address();
        String name = person.name();
        int age = person.age();

        int updatedEntries = userRepository.updatePerson(ssn,address, name, age);
        if(updatedEntries == 0) {
            throw new UserNotExistsException("No entries to update");
        }
        return updatedEntries;
    }
}
