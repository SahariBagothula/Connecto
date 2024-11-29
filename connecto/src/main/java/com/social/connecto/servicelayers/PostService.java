package com.social.connecto.servicelayers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.connecto.exceptions.EntityNotFoundException;
import com.social.connecto.models.Post;
import com.social.connecto.models.User;
import com.social.connecto.repositories.PostRepository;
import com.social.connecto.repositories.UserRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(Post post, int userid) {

        User user = userService.findUserById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userid));

        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);

    }

    public void deletePost(int postid, int userid) {

        Post postToBeDeleted = findPostById(postid);
        User user = userService.findUserById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userid));

        if (postToBeDeleted.getUser().getId() == user.getId()) {
            postRepository.delete(postToBeDeleted);
        } else {
            throw new IllegalArgumentException("You cannot delete another users post");
        }

    }

    public List<Post> findPostsByUserId(int userid) {
        userService.findUserById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userid));
        List<Post> posts = postRepository.findPostByUserid(userid);
        if (posts.isEmpty()) {
            throw new EntityNotFoundException("No posts found for user with id: " + userid);
        }
        return posts;
    }

    public Post findPostById(int postid) {
        return postRepository.findById(postid)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + postid));
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Post savePosts(int postid, int userid) {
        Post postToBeSaved = findPostById(postid);
        Optional<User> user = userService.findUserById(userid);

        if (user.get().getSavedPost().contains(postToBeSaved)) {
            user.get().getSavedPost().remove(postToBeSaved);
        } else {
            user.get().getSavedPost().add(postToBeSaved);
        }
        userRepository.save(user.get());
        return postToBeSaved;

    }

    public Post likePost(int postid, int userid) {
        Post postToBeLiked = findPostById(postid);
        Optional<User> user = userService.findUserById(userid);

        if (postToBeLiked.getLiked().contains(user.get())) {
            postToBeLiked.getLiked().remove(user.get());
        } else {
            postToBeLiked.getLiked().add(user.get());
        }
        userRepository.save(user.get());
        return postToBeLiked;

    }

}
