package com.YoutubeAccount.Manager.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "VideoReaction")
@Data
public class VideoReaction  {
    @Id
    private String id;
    private String videoId;
    private String userId;
    private String reaction;
    @LastModifiedDate
    private LocalDateTime reactedAt;
}
