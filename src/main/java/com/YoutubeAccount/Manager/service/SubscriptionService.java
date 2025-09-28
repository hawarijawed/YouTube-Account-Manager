package com.YoutubeAccount.Manager.service;

import com.YoutubeAccount.Manager.models.Subscription;
import com.YoutubeAccount.Manager.models.Users;
import com.YoutubeAccount.Manager.models.YouTubeAccount;
import com.YoutubeAccount.Manager.repositories.SubscriptionRepository;
import com.YoutubeAccount.Manager.repositories.UserRepository;
import com.YoutubeAccount.Manager.repositories.YouTubeAccountRespository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final YouTubeAccountRespository youTubeAccountRespository;
    public SubscriptionService(SubscriptionRepository repository,
                               UserRepository userRepository,
                               YouTubeAccountRespository youTubeAccountRespository) {
        this.subscriptionRepository = repository;
        this.userRepository = userRepository;
        this.youTubeAccountRespository = youTubeAccountRespository;
    }

    //Add subscription
    public void addSubscription(String  channelId, String username)
    {
        Users user = userRepository.getUserByusername(username);
        Subscription existing = subscriptionRepository.findByChannelIdAndUserId(channelId, user.getId());
        YouTubeAccount account = youTubeAccountRespository.findById(channelId).orElse(null);
        if(account == null) return;
        if(existing != null){//If the user has already subscribed channel
            subscriptionRepository.delete(existing);
            account.setSubscribers(Math.max(0, account.getSubscribers()-1));

        }
        else{
            Subscription subscription = new Subscription();
            subscription.setChannelId(channelId);
            subscription.setUserId(user.getId());
            subscriptionRepository.save(subscription);
            account.setSubscribers(account.getSubscribers()+1);
        }

        youTubeAccountRespository.save(account);
    }

}
