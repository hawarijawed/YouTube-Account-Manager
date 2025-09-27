package com.YoutubeAccount.Manager.service;

import com.YoutubeAccount.Manager.models.CommentReaction;
import com.YoutubeAccount.Manager.models.Comments;
import com.YoutubeAccount.Manager.repositories.CommentReactionRepository;
import com.YoutubeAccount.Manager.repositories.CommentRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class CommentReactionService {

    private final CommentReactionRepository commentReactionRepository;
    private final MongoTemplate mongoTemplate;
    private final CommentRepository commentRepository;
    public CommentReactionService(CommentReactionRepository commentReactionRepository,
                                  MongoTemplate template,
                                  CommentRepository commentRepository){
        this.commentReactionRepository = commentReactionRepository;
        this.mongoTemplate = template;
        this.commentRepository = commentRepository;
    }

    //Add comment reaction
    public boolean addCommentReaction(CommentReaction reaction){
        CommentReaction existing = commentReactionRepository.findByCommentIdAndUserId(reaction.getCommentId(), reaction.getUserId());
        Comments comments = commentRepository.findById(reaction.getCommentId()).orElse(null);
        if(comments == null) return false;
        if(existing != null){
            if(existing.getType().equals(reaction.getType())){
                commentReactionRepository.delete(existing);
                if(reaction.getType().equals("LIKE")){
                    //Unsetting the count of likedComments
                    comments.setLikedComments(comments.getLikedComments()>0?comments.getLikedComments()-1:0);
                }
                else{
                    //Unsetting the count of disliked comments
                    comments.setDislikedComments(comments.getDislikedComments()>0?comments.getDislikedComments()-1:0);
                }
            }
            else{
                existing.setType(reaction.getType());
                commentReactionRepository.save(existing);
                if(reaction.getType().equals("LIKE")){
                    comments.setLikedComments(comments.getLikedComments()+1);
                }
                else{
                    comments.setDislikedComments(comments.getDislikedComments()+1);
                }
            }
        }
        else {
            commentReactionRepository.save(reaction);
            if(reaction.getType().equals("LIKE")){
                comments.setLikedComments(comments.getLikedComments()+1);
            }
            else{
                comments.setDislikedComments(comments.getDislikedComments()+1);
            }
        }
        commentRepository.save(comments);
        commentReactionRepository.save(reaction);

        return true;
    }

    public void removeReaction(String commentId){
        Query query = new Query();
        query.addCriteria(Criteria.where("commentId").is(commentId));
        mongoTemplate.remove(query, CommentReaction.class);
    }
}
