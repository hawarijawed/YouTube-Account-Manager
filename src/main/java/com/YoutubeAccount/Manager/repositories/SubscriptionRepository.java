package com.YoutubeAccount.Manager.repositories;

import com.YoutubeAccount.Manager.models.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

}
