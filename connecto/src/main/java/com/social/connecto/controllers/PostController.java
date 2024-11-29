package com.social.connecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.connecto.exceptions.EntityNotFoundException;
import com.social.connecto.models.Post;
import com.social.connecto.models.User;
import com.social.connecto.servicelayers.PostService;
import com.social.connecto.servicelayers.UserService;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/add/{userid}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable int userid) {
        Post createdPost = postService.createPost(post, userid);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @DeleteMapping("/delete/post/{postid}/user/{userid}")
    public ResponseEntity<?> deletePost(@PathVariable int postid, @PathVariable int userid) {
        postService.deletePost(postid, userid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userid}")
    public ResponseEntity<List<Post>> findPostsByUserId(@PathVariable int userid) {
        List<Post> posts = postService.findPostsByUserId(userid);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/post/{postid}")
    public ResponseEntity<Post> findPostById(@PathVariable int postid) {
        Post post = postService.findPostById(postid);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> findAllPosts() {
        List<Post> posts = postService.findAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @PutMapping("/savepost/post/{postid}/user/{userid}")
    public ResponseEntity<?> savePosts(@PathVariable int postid, @PathVariable int userid) {
        postService.savePosts(postid, userid);
        User currentUser = userService.findUserById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userid));
        return ResponseEntity.status(HttpStatus.OK).body(currentUser);
    }

    @PutMapping("/likepost/post/{postid}/user/{userid}")
    public ResponseEntity<?> likePost(@PathVariable int postid, @PathVariable int userid) {
        postService.likePost(postid, userid);
        User currentUser = userService.findUserById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userid));
        return ResponseEntity.status(HttpStatus.OK).body(currentUser);
    }

}
