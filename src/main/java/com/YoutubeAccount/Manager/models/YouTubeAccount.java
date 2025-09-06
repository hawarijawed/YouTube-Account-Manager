package com.YoutubeAccount.Manager.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "youtube_accounts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YouTubeAccount {
    @Id
    private String id;

    private String userId;//reference for user._id
    private String channelId;
    private String channelName;
    //private String profilePic;
    private long subscribers;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
