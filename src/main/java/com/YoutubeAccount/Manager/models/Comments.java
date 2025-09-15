package com.YoutubeAccount.Manager.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "comments")

public class Comments {
    @Id
    private String id;
    private String videoId;//Reference from VideoManagement.id
    private String userId;
    private String text;
    private long likedComments = 0;
    private long dislikedComments = 0;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
