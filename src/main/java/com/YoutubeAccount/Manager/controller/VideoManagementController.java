package com.YoutubeAccount.Manager.controller;

import com.YoutubeAccount.Manager.models.VideoManagement;
import com.YoutubeAccount.Manager.service.VideoManagementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/video")
public class VideoManagementController {
    @Autowired
    private final VideoManagementServices videoManagementServices;

    public VideoManagementController(VideoManagementServices services){
        this.videoManagementServices = services;
    }

    @GetMapping("get/{id}")
    public VideoManagement getVideoById(@PathVariable String id){
        return videoManagementServices.getVideoById(id);
    }

    @GetMapping("/get/channel/{channelId}")//channel id refers to account id
    public ResponseEntity<?> getVideoByChannel(@PathVariable String channelId){
        List<VideoManagement> video = videoManagementServices.getVideoByAccount(channelId);
        if(video.isEmpty()){
            return new ResponseEntity<>("No vidoes found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(video, HttpStatus.FOUND);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllVideos(){
        List<VideoManagement> video = videoManagementServices.getAll();
        if(!video.isEmpty()){
            return new ResponseEntity<>(video, HttpStatus.FOUND);
        }

        return new ResponseEntity<>("No videos found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveVideo(@RequestBody VideoManagement video){
        boolean flag = videoManagementServices.saveVideo(video);
        if(flag){
            return new ResponseEntity<>(video, HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Account not found", HttpStatus.BAD_REQUEST);
    }


}

