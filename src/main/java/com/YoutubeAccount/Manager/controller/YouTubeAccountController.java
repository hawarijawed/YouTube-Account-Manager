package com.YoutubeAccount.Manager.controller;

import com.YoutubeAccount.Manager.models.YouTubeAccount;
import com.YoutubeAccount.Manager.service.YouTubeAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
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

    //Find channel by channelId
    @GetMapping("/channel/{id}")
    public ResponseEntity<?> getAccountByChannel(@PathVariable String channelId){
        YouTubeAccount account = youTubeAccountService.findByChannel(channelId);
        if(account != null){
            return new ResponseEntity<>(account, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public YouTubeAccount addAccount(@RequestBody YouTubeAccount account){
        return  youTubeAccountService.createAccount(account);
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
}
