package com.YoutubeAccount.Manager.service;

import com.YoutubeAccount.Manager.models.VideoManagement;
import com.YoutubeAccount.Manager.repositories.VideoManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VideoManagementServices {
    @Autowired
    private final VideoManagementRepository videoManagementRepository;

    @Autowired
    private final MongoTemplate mongoTemplate;
    public VideoManagementServices(VideoManagementRepository video, MongoTemplate template){
        this.videoManagementRepository = video;
        this.mongoTemplate = template;
    }

    public boolean saveVideo(VideoManagement video){
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

    public boolean deleteById(String id){
        videoManagementRepository.deleteById(id);
        return true;
    }

    public boolean likeVideo(String id){
        VideoManagement video = videoManagementRepository.findById(id).orElse(null);
        if(video == null){
            return  false;
        }
        video.setLikes(video.getLikes()+1);
        videoManagementRepository.save(video);
        return true;
    }

    public boolean disLikeVideo(String id){
        VideoManagement video = videoManagementRepository.findById(id).orElse(null);
        if(video == null){
            return  false;
        }
        video.setDislikes(video.getDislikes()+1);
        videoManagementRepository.save(video);
        return true;
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
