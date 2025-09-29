package com.YoutubeAccount.Manager.repositories;

import com.YoutubeAccount.Manager.models.VideoReaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoReactionRepository extends MongoRepository<VideoReaction, String> {
    VideoReaction findByVideoIdAndUserId(String videoId, String userId);
}
