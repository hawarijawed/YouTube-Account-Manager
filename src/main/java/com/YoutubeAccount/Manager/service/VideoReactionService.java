package com.YoutubeAccount.Manager.service;

import com.YoutubeAccount.Manager.models.VideoManagement;
import com.YoutubeAccount.Manager.models.VideoReaction;
import com.YoutubeAccount.Manager.repositories.VideoManagementRepository;
import com.YoutubeAccount.Manager.repositories.VideoReactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoReactionService {
    private final VideoReactionRepository videoReactionRepository;
    private final VideoManagementRepository videoManagementRepository;
    //private final VideoManagement videoManagement;
    public VideoReactionService(VideoReactionRepository videoReactionRepository,
                                VideoManagementRepository videoManagementRepository
                                ){
        this.videoReactionRepository = videoReactionRepository;
        this.videoManagementRepository = videoManagementRepository;
        //this.videoManagement = videoManagement;
    }

    //Save video reaction
    @Transactional
    public String saveVideoReaction(String videoId, String userId, String type){
        VideoReaction existing = videoReactionRepository.findByVideoIdAndUserId(videoId, userId);
        VideoManagement video = videoManagementRepository.findById(videoId).orElseThrow();

        if (existing != null) {
            if (existing.getReaction().equals(type)) {
                // Same reaction again → remove it
                videoReactionRepository.delete(existing);
                if (type.equals("LIKE")) video.setLikes(video.getLikes() - 1);
                else video.setDislikes(video.getDislikes() - 1);
                videoManagementRepository.save(video);
                return type + " removed";
            } else {
                // Switching reaction
                existing.setReaction(type);
                videoReactionRepository.save(existing);

                if (type.equals("LIKE")) {
                    video.setLikes(video.getLikes() + 1);
                    video.setDislikes(video.getDislikes() - 1);
                } else {
                    video.setDislikes(video.getDislikes() + 1);
                    video.setLikes(video.getLikes() - 1);
                }
                videoManagementRepository.save(video);
                return "Switched to " + type;
            }
        } else {
            // New reaction
            VideoReaction reaction = new VideoReaction();
            reaction.setVideoId(videoId);
            reaction.setUserId(userId);
            reaction.setReaction(type);
            videoReactionRepository.save(reaction);

            if (type.equals("LIKE")) video.setLikes(video.getLikes() + 1);
            else video.setDislikes(video.getDislikes() + 1);

            videoManagementRepository.save(video);
            return type + " added";
        }
    }
}
