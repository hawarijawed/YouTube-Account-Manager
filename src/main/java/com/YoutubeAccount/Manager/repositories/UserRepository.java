package com.YoutubeAccount.Manager.repositories;

import com.YoutubeAccount.Manager.models.Users;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface UserRepository extends MongoRepository<Users, String> {
    Users getUserByusername(String username);
}
