package com.YoutubeAccount.Manager.service;

import com.YoutubeAccount.Manager.models.Users;
import com.YoutubeAccount.Manager.models.VideoManagement;
import com.YoutubeAccount.Manager.repositories.UserRepository;
import com.YoutubeAccount.Manager.repositories.VideoManagementRepository;
import com.YoutubeAccount.Manager.repositories.YouTubeAccountRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VideoManagementServices {

    private final VideoManagementRepository videoManagementRepository;
    private final YouTubeAccountRespository youTubeAccountRespository;
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;
    private final VideoReactionService videoReactionService;
    public VideoManagementServices(VideoManagementRepository video,
                                   MongoTemplate template,
                                   YouTubeAccountRespository youtube,
                                   UserRepository userRepository,
                                   VideoReactionService videoReactionService){
        this.videoManagementRepository = video;
        this.mongoTemplate = template;
        this.youTubeAccountRespository = youtube;
        this.userRepository = userRepository;
        this.videoReactionService = videoReactionService;
    }

    public boolean saveVideo(VideoManagement video){
        if(!youTubeAccountRespository.existsById(video.getYoutubeAccountId())){
            return false;
        }
        if(videoManagementRepository.existsById(video.getId())){
            return false;
        }
        videoManagementRepository.save(video);
        return  true;
    }

    public VideoManagement getVideoById(String id){
        return videoManagementRepository.findById(id).orElse(null);
    }

    public List<VideoManagement> getVideoByAccount(String youtubeAccountId){
        Criteria criteria = new Criteria();
        Query query = new Query();
        query.addCriteria(criteria.where("youtubeAccountId").is(youtubeAccountId));
        return  mongoTemplate.find(query, VideoManagement.class);
    }

    public List<VideoManagement> getAll(){
        return videoManagementRepository.findAll();
    }
    public boolean deleteById(String id){
        videoManagementRepository.deleteById(id);
        return true;
    }

    //Delete all video for a channel
    public boolean deleteAll(String accountId){
        Criteria criteria = new Criteria();
        Query query = new Query();
        query.addCriteria(criteria.where("youtubeAccountId").is(accountId));
        mongoTemplate.remove(query, VideoManagement.class);
        return true;
    }

    public String likeDislikeVideo(String videoId, String type){
        VideoManagement video = videoManagementRepository.findById(videoId).orElseThrow();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = userRepository.getUserByusername(username);
        return videoReactionService.saveVideoReaction(videoId, user.getId(), type);
    }


    public boolean viewVideo(String id){
        VideoManagement video = videoManagementRepository.findById(id).orElse(null);
        if(video == null){
            return  false;
        }
        video.setViews(video.getViews()+1);
        videoManagementRepository.save(video);
        return true;
    }

    public boolean setDescription(String description, String id){
        VideoManagement video = videoManagementRepository.findById(id).orElse(null);
        if(video == null) {
            return false;
        }
        video.setDescription(description);
        videoManagementRepository.save(video);
        return true;
    }

    public boolean setTitle(String title, String id){
        VideoManagement video = videoManagementRepository.findById(id).orElse(null);
        if(video == null) {
            return false;
        }
        video.setTitle(title);
        videoManagementRepository.save(video);
        return true;
    }
}
