package com.YoutubeAccount.Manager.repositories;

import com.YoutubeAccount.Manager.models.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comments, String> {
}
