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
    public boolean addCommentReaction(CommentReaction reaction) {
        // Fetch existing reaction from the same user on the same comment
        CommentReaction existing = commentReactionRepository.findByCommentIdAndUserId(
                reaction.getCommentId(),
                reaction.getUserId()
        );

        // Fetch the actual comment
        Comments comments = commentRepository.findById(reaction.getCommentId()).orElse(null);
        if (comments == null) return false;

        if (existing != null) {
            if (existing.getType().equals(reaction.getType())) {
                // ✅ Toggle: user clicked same reaction again (e.g., LIKE → LIKE)
                commentReactionRepository.delete(existing);

                if ("LIKE".equals(reaction.getType())) {
                    comments.setLikedComments(Math.max(0, comments.getLikedComments() - 1));
                } else {
                    comments.setDislikedComments(Math.max(0, comments.getDislikedComments() - 1));
                }

            } else {
                // ✅ Switch: user changed reaction type (e.g., LIKE → DISLIKE)
                if ("LIKE".equals(reaction.getType())) {
                    comments.setLikedComments(comments.getLikedComments() + 1);
                    comments.setDislikedComments(Math.max(0, comments.getDislikedComments() - 1));
                } else {
                    comments.setDislikedComments(comments.getDislikedComments() + 1);
                    comments.setLikedComments(Math.max(0, comments.getLikedComments() - 1));
                }

                existing.setType(reaction.getType());
                commentReactionRepository.save(existing);
            }

        } else {
            // ✅ New reaction
            commentReactionRepository.save(reaction);

            if ("LIKE".equals(reaction.getType())) {
                comments.setLikedComments(comments.getLikedComments() + 1);
            } else {
                comments.setDislikedComments(comments.getDislikedComments() + 1);
            }
        }

        // Save updated comment counts
        commentRepository.save(comments);

        return true;
    }


    public void removeReaction(String commentId){
        Query query = new Query();
        query.addCriteria(Criteria.where("commentId").is(commentId));
        mongoTemplate.remove(query, CommentReaction.class);
    }
}
