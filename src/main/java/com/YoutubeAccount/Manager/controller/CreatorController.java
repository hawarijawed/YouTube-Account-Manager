package com.YoutubeAccount.Manager.controller;

import com.YoutubeAccount.Manager.models.Users;
import com.YoutubeAccount.Manager.service.CreaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/user")
public class CreatorController {
    @Autowired
    private CreaterService createrService;

    @GetMapping("/greet/{name}")
    public ResponseEntity<String> gretting(@PathVariable String name){
        String greet_msg = createrService.greet();
        return new ResponseEntity<>(greet_msg+name, HttpStatus.OK);
    }

    @PostMapping("/add")
    public boolean AddUser(@RequestBody Users user){
        boolean flag = createrService.saveUser(user);
        return flag;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers(){
        List<Users>  user = createrService.getAllUsers();
        if(!user.isEmpty()) {
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("No users found in database", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Users user = createrService.findUserById(id);
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        if(createrService.deleteUserById(id)){
            return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAll(){
        if(createrService.deleteAll()){
            return new ResponseEntity<>("All users deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("No users found in database", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody Users user, @PathVariable String id){
        Users updatedUser = createrService.updateUser(user, id);
        if(updatedUser == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(updatedUser);
    }
}
