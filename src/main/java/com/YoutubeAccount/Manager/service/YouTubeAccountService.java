package com.YoutubeAccount.Manager.service;

import com.YoutubeAccount.Manager.models.YouTubeAccount;
import com.YoutubeAccount.Manager.repositories.UserRepository;
import com.YoutubeAccount.Manager.repositories.YouTubeAccountRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
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

        if(!userRepository.existsById(account.getUserId())){
            log.info("user id {}",account.getUserId());
            throw new RuntimeException("User with ID " + account.getUserId() + " does not exist");
        }
        if(youTubeAccountRespository.existsById(account.getId())){
            return null;
        }
        YouTubeAccount savedAccount = youTubeAccountRespository.save(account);
        log.info("Saved account: {}", savedAccount);
        return savedAccount;
    }

    //Get channels from userId
    public List<YouTubeAccount> getAccountByUser(String userId){
        return youTubeAccountRespository.findByUserId(userId);
    }

    //Find channel by ID
    public YouTubeAccount findByAccountId(String id){
        return youTubeAccountRespository.findById(id).orElse(null);
    }

    //Delete account by id
    public void deleteAccountById(String id){
        youTubeAccountRespository.deleteById(id);
    }

    //update the account
    public YouTubeAccount updateAccount(YouTubeAccount account){
        YouTubeAccount account1 = youTubeAccountRespository.findById(account.getId()).orElse(null);
        if(account1 == null){
            return null;
        }
        account1.setChannelName(account.getChannelName());
        youTubeAccountRespository.save(account1);
        return account1;
    }

    //Subscribe channel
    public boolean subscribe(String id){
        YouTubeAccount account = youTubeAccountRespository.findById(id).orElse(null);
        if(account == null){
            return false;
        }
        account.setSubscribers(account.getSubscribers()+1);
        youTubeAccountRespository.save(account);
        return true;
    }

}
