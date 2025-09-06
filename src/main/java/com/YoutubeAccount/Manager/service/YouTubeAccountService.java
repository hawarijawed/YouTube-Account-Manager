package com.YoutubeAccount.Manager.service;

import com.YoutubeAccount.Manager.models.YouTubeAccount;
import com.YoutubeAccount.Manager.repositories.YouTubeAccountRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YouTubeAccountService {
    @Autowired
    private final YouTubeAccountRespository youTubeAccountRespository;

    public YouTubeAccountService(YouTubeAccountRespository youTubeAccountRespository){
        this.youTubeAccountRespository = youTubeAccountRespository;
    }

    //Create account
    public boolean createAccount(YouTubeAccount account){
        youTubeAccountRespository.save(account);
        return true;
    }

    //Get channels from userId
    public List<YouTubeAccount> getAccountByUser(String userId){
        return youTubeAccountRespository.findByUserId(userId);
    }

    //Get channel by channelId
    public YouTubeAccount findByChannel(String channelId){
        return youTubeAccountRespository.findByChannelId(channelId);
    }

    //Find channel by ID
    public YouTubeAccount findByAccountId(String id){
        return youTubeAccountRespository.findById(id).orElse(null);
    }
}
