package com.YoutubeAccount.Manager.repositories;

import com.YoutubeAccount.Manager.models.CommentReaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReactionRepository extends MongoRepository<CommentReaction,String> {
    CommentReaction findByCommentIdAndUserId(String commentId, String userId);
}
