package com.YoutubeAccount.Manager.service;

import com.YoutubeAccount.Manager.models.Users;
import com.YoutubeAccount.Manager.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Custom UserDetailsService called for username: " + email);

        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(email));

        Users user = mongoTemplate.findOne(query, Users.class);

        if(user == null){
            throw new UsernameNotFoundException("User not found with given name: "+email);
        }
        UserDetails userDetails;
        userDetails = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
        log.info("User details info: "+userDetails);
        return userDetails;
    }
}
