package com.YoutubeAccount.Manager.service;

import com.YoutubeAccount.Manager.models.YouTubeAccount;
import com.YoutubeAccount.Manager.repositories.UserRepository;
import com.YoutubeAccount.Manager.repositories.YouTubeAccountRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YouTubeAccountService {
    @Autowired
    private final YouTubeAccountRespository youTubeAccountRespository;

    @Autowired
    private final UserRepository userRepository;

    public YouTubeAccountService(YouTubeAccountRespository youTubeAccountRespository, UserRepository userRepository){
        this.youTubeAccountRespository = youTubeAccountRespository;
        this.userRepository = userRepository;
    }

    //Create account
    public YouTubeAccount createAccount(YouTubeAccount account){

        if(userRepository.existsById(account.getUserId())){
            throw new RuntimeException("User with ID " + account.getUserId() + " does not exist");
        }

        // 2. Check if this user already added the same channel
        if (youTubeAccountRespository.findByUserIdAndChannelId(account.getUserId(), account.getChannelId()).isPresent()) {
            throw new RuntimeException("Channel already linked to this user");
        }

        return youTubeAccountRespository.save(account);
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

    //Delete account by id
    public void deleteAccountById(String id){
        youTubeAccountRespository.deleteById(id);
    }
}
