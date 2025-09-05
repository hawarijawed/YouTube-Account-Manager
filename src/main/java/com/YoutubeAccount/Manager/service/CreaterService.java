package com.YoutubeAccount.Manager.service;

import com.YoutubeAccount.Manager.models.Users;
import com.YoutubeAccount.Manager.repositories.UserRepository;
import java.util.Collections;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class CreaterService {
    @Autowired
    private final UserRepository userRepository;
    public CreaterService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean saveUser (Users user){
        userRepository.save(user);
        Optional<Users> use = userRepository.findById(user.getId());
        if(!use.isEmpty()){
            return true;
        }
        return false;
    }

    public List<Users> getAllUsers(){
        List<Users> user = userRepository.findAll();
        if(!user.isEmpty()){
            return user;
        }
        return Collections.emptyList();
    }

    public Users findUserById(String id){
        return userRepository.findById(id).orElse(null);
    }

    public String greet(){
        return "What a sunny day !!!";
    }
}
