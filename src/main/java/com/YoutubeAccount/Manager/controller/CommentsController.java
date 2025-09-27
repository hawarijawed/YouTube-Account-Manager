package com.YoutubeAccount.Manager.controller;

import com.YoutubeAccount.Manager.models.Comments;
import com.YoutubeAccount.Manager.models.Users;
import com.YoutubeAccount.Manager.repositories.UserRepository;
import com.YoutubeAccount.Manager.service.CommentService;
import com.YoutubeAccount.Manager.service.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    private final CommentService commentService;
    private final UserRepository userRepository;
    public CommentsController(CommentService commentService, UserRepository userRepository){
        this.commentService = commentService;
        this.userRepository = userRepository;
    }

    //Add comment
    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody Comments comments){
        boolean flag = commentService.addComment(comments);
        if(flag){
            return new ResponseEntity<>("Your comment added successfully", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Unable to add comment", HttpStatus.BAD_REQUEST);
    }

    //Get comment by id
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable String id){
        Comments comments = commentService.getCommentById(id);
        if(comments == null){
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comments, HttpStatus.FOUND);
    }

    //Get comment by videoId
    @GetMapping("/get/video/{videoId}")
    public ResponseEntity<?> getCommentsByVideoId(@PathVariable String  videoId){
        List<Comments> comments = commentService.getVideoComments(videoId);
        if(comments.isEmpty()){
            return new ResponseEntity<>("No comments found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comments, HttpStatus.FOUND);
    }

    //Get comment by userId
    @GetMapping("/get/user/{id}")
    public ResponseEntity<?> getCommentByUserId(@PathVariable String userId){
        List<Comments> comments = commentService.getUserComments(userId);
        if(comments == null){
            return new ResponseEntity<>("No comments found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comments, HttpStatus.FOUND);
    }

    //Like the comment
    @PutMapping("/like/{id}")
    public ResponseEntity<String> likeComment(@PathVariable String id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username =  auth.getName();
        Users user = userRepository.getUserByusername(username);

        boolean flag = commentService.likeComment(id, user.getId());
        if(flag){
            return new ResponseEntity<>("You liked the comment", HttpStatus.OK);
        }
        return new ResponseEntity<>("Comment not found", HttpStatus.BAD_REQUEST);
    }

    //Dislike the comment
    @PutMapping("/dislike/{id}")
    public ResponseEntity<String> dislikeComment(@PathVariable String id){
        boolean flag = commentService.dislikeComment(id);
        if(flag){
            return new ResponseEntity<>("You disliked the comment", HttpStatus.OK);
        }
        return new ResponseEntity<>("Comment not found", HttpStatus.BAD_REQUEST);
    }

    //Delete comment by Id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id){
        boolean flag = commentService.deleteById(id);
        if(flag){
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
    }

    //Delete comment of a video
    @DeleteMapping("/delete/video/{id}")
    public ResponseEntity<String> deleteCommentByVideoId(@PathVariable String id){
        boolean flag = commentService.deleteAllComments(id);
        if(flag){
            return new ResponseEntity<>("Comments removed successfully", HttpStatus.GONE);
        }
        return new ResponseEntity<>("Comments not found", HttpStatus.NOT_FOUND);
    }

    //Delete comment of a user
    @DeleteMapping("/delete/user/{userId}")
    public ResponseEntity<String> deleteCommentByUserId(@PathVariable String userId){
        boolean flag = commentService.deleteUserComment(userId);
        if(flag){
            return new ResponseEntity<>("Comments deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
    }

    //Update the comment
    @PostMapping("/update")
    public ResponseEntity<?> updateComment(@RequestBody Comments comments){
        Comments comment = commentService.updateComments(comments);
        if(comment == null){
            return new ResponseEntity<>("Unable to update the comment", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

}
