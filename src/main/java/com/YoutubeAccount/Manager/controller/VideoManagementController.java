package com.YoutubeAccount.Manager.controller;

import com.YoutubeAccount.Manager.service.VideoManagementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/video")
public class VideoManagementController {
    @Autowired
    private final VideoManagementServices videoManagementServices;

    public VideoManagementController(VideoManagementServices services){
        this.videoManagementServices = services;
    }


}

