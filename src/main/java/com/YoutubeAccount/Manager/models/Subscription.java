package com.YoutubeAccount.Manager.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Subscription")
@Data
public class Subscription {
    @Id
    private String id;
    private String channelId;
    private String userId;
    @LastModifiedDate
    private LocalDateTime subscribedAt;
}
