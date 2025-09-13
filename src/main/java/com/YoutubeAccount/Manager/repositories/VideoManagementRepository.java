package com.YoutubeAccount.Manager.repositories;

import com.YoutubeAccount.Manager.models.VideoManagement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoManagementRepository extends MongoRepository<VideoManagement, String> {

}
