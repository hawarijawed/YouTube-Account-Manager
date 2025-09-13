package com.YoutubeAccount.Manager.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "video")
public class VideoManagement {
    @Id
    private String id;
    private String youtubeAccountId; //YoutubeAccount -> id
    private String title;
    private String description;
    private long views;
    private long likes;
    private long dislikes;
}
