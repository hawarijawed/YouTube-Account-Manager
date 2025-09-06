package com.YoutubeAccount.Manager.repositories;

import com.YoutubeAccount.Manager.models.YouTubeAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface YouTubeAccountRespository extends MongoRepository<YouTubeAccount, String> {
    //Find all Youtube from user id
    List<YouTubeAccount> findByUserId(String userId);

    //Find Youtube account from channelId
    YouTubeAccount findByChannelId(String channelId);

    //Delete all accounts for a user (if needed)
    void deleteByUserId(String userId);
}
