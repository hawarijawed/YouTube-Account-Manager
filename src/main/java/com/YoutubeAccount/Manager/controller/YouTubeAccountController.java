package com.YoutubeAccount.Manager.controller;

import com.YoutubeAccount.Manager.models.YouTubeAccount;
import com.YoutubeAccount.Manager.service.YouTubeAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/youtubeaccount")
public class YouTubeAccountController {

    @Autowired
    private final YouTubeAccountService youTubeAccountService;

    public YouTubeAccountController(YouTubeAccountService youTubeAccountService){
        this.youTubeAccountService = youTubeAccountService;
    }
    //Find channel by id
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable String id){
        YouTubeAccount account = youTubeAccountService.findByAccountId(id);
        if(account != null) {
            return new ResponseEntity<>(account, HttpStatus.FOUND);
        }

        return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAccount(@RequestBody YouTubeAccount account){
        log.info("Add account controller is called");
        YouTubeAccount savedAccount = youTubeAccountService.createAccount(account);
        //log.info("Saved account in controller: {}", savedAccount);
        if(savedAccount == null){
            return  new ResponseEntity<>("Account with same id exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable String id){
        //If accound does not exist
        if(youTubeAccountService.findByAccountId(id) == null){
            return  new ResponseEntity<>("Given accound does not exist", HttpStatus.NOT_FOUND);
        }
        youTubeAccountService.deleteAccountById(id);
        return new ResponseEntity<>("Account Deleted", HttpStatus.OK);
    }

    @PutMapping("/subscribe/{id}")
    public ResponseEntity<String> subscribeChannel(@PathVariable String id){
        boolean flag = youTubeAccountService.subscribe(id);
        if(!flag){
            return new ResponseEntity<>("Account not found", HttpStatus.BAD_REQUEST);
        }
        String message = "You Subscribed "+youTubeAccountService.findByAccountId(id).getChannelName();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
