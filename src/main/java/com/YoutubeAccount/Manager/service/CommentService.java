package com.YoutubeAccount.Manager.service;

import com.YoutubeAccount.Manager.models.CommentReaction;
import com.YoutubeAccount.Manager.models.Comments;
import com.YoutubeAccount.Manager.repositories.CommentReactionRepository;
import com.YoutubeAccount.Manager.repositories.CommentRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MongoTemplate mongoTemplate;
    private final CommentReactionRepository commentReactionRepository;
    private final CommentReactionService commentReactionService;
    public CommentService(CommentRepository comment,
                          MongoTemplate template,
                          CommentReactionRepository commentReactionRepository,
                          CommentReactionService commentReactionService){
        this.commentRepository = comment;
        this.mongoTemplate = template;
        this.commentReactionRepository = commentReactionRepository;
        this.commentReactionService = commentReactionService;
    }

    //Add comment
    public boolean addComment(Comments comment){
        if(comment.getText() == null || comment.getUserId() == null || comment.getVideoId() == null){
            return false;
        }
        commentRepository.save(comment);
        return  true;
    }

    //Get all comments;
    public List<Comments> getAllComments(){
        return commentRepository.findAll();
    }

    //Get comments of particular user
    public List<Comments> getUserComments(String userId){
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("userId must not be null or empty");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Comments.class);
    }

    //Get all comments on a video
    public List<Comments> getVideoComments(String videoId){
        if(videoId == null || videoId.trim().isEmpty()){
            throw new IllegalArgumentException("Video id must not be null");
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("videoId").is(videoId));
        return mongoTemplate.find(query, Comments.class);
    }
    //Get comment with specific id
    public Comments getCommentById(String id){
        return commentRepository.findById(id).orElse(null);
    }
    //Like the comment
//    public boolean likeComment(String commentId, String userId){
//        Comments comments = commentRepository.findById(commentId).orElse(null);
//        if(comments == null)
//            return false;
//
//        CommentReaction reaction = new CommentReaction();
//        reaction.setCommentId(commentId);
//        reaction.setUserId(userId);
//        reaction.setType("LIKE");
//        commentReactionService.addCommentReaction(reaction);
//
//        return true;
//    }
//
//    //Dislike the comment
//    public boolean dislikeComment(String commentId, String userId){
//        Comments comments = commentRepository.findById(commentId).orElse(null);
//        if(comments == null)
//            return false;
//        CommentReaction reaction = new CommentReaction();
//        reaction.setUserId(userId);
//        reaction.setCommentId(commentId);
//        reaction.setType("DISLIKE");
//
//        commentReactionService.addCommentReaction(reaction);
//        return true;
//    }

    public String likeDisLikeComment(String commentId, String userId, String type){
        CommentReaction reaction = new CommentReaction();
        reaction.setUserId(userId);
        reaction.setCommentId(commentId);
        reaction.setType(type);

        return commentReactionService.addCommentReaction(reaction);
    }

    //Delete comment by Id
    public boolean deleteById(String id){
        if(!commentRepository.existsById(id)){
            return  false;
        }
        commentRepository.deleteById(id);
        return true;
    }

    //Delete all the comments over a video
    public boolean deleteAllComments(String videoId){
        if(!commentRepository.existsById(videoId)){
            return false;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("videoId").is(videoId));
        mongoTemplate.remove(query, Comments.class);
        return true;
    }

    //Delete/Report a particular user comment
    public boolean deleteUserComment(String userId){
        if(!commentRepository.existsById(userId)){
            return false;
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        mongoTemplate.remove(query, Comments.class);
        return true;
    }

    //Update Comment
    public Comments updateComments(Comments comment){
        if(comment.getText() == null) return null;
        Comments comments = commentRepository.findById(comment.getId()).orElse(null);
        if(comments == null) return null;
        comments.setText(comment.getText());
        commentRepository.save(comments);
        return comments;
    }

}
