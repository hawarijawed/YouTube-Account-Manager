package com.YoutubeAccount.Manager.repositories;

import com.YoutubeAccount.Manager.models.YouTubeAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface YouTubeAccountRespository extends MongoRepository<YouTubeAccount, String> {
    //Find all Youtube from user id
    List<YouTubeAccount> findByUserId(String userId);


    //Delete all accounts for a user (if needed)
    void deleteByUserId(String userId);


}
