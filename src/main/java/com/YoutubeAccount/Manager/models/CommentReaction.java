package com.YoutubeAccount.Manager.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "CommentReaction")
public class CommentReaction {
    @Id
    private String id;
    private String commentId;
    private String userId;
    private String type;
    @LastModifiedDate
    private LocalDateTime reactedAt;
}
