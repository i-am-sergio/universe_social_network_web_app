package com.unsa.backend.posts;

import java.util.List;

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

import com.unsa.backend.services.TimelineService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final TimelineService timelineService;


    @GetMapping
    public ResponseEntity<List<PostModel>> getPosts() {
        try {
            List<PostModel> posts = postService.getPosts();

            if (!posts.isEmpty()) {
                return new ResponseEntity<>(posts, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostModel> getPost(@PathVariable Long id) {
        try {
            PostModel post = postService.getPostById(id);

            if (post != null) {
                return new ResponseEntity<>(post, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<PostModel> createPost(@RequestBody PostModel newPost) {
        try {
            PostModel createdPost = postService.createPost(newPost);
            return new ResponseEntity<>(createdPost, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody PostModel updatedPost) {
        try {
            PostModel existingPost = postService.getPostById(id);

            if (existingPost != null) {
                if (existingPost.getUserId().equals(updatedPost.getUserId())) {
                    postService.updatePost(existingPost, updatedPost);
                    return new ResponseEntity<>("Post updated!", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Authentication failed", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        try{
            if (postService.deletePost(postId)) {
                return new ResponseEntity<>("Post eliminado correctamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se pudo encontrar el post con ID: " + postId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable Long id, @RequestBody LikeRequest likeRequest) {
        try {
            PostModel post = postService.getPostById(id);
            if(post == null){
                return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
            }

            if (post.getLikes().contains(likeRequest.getUserId())) {
                postService.removeLike(post, likeRequest.getUserId());
                return new ResponseEntity<>("Post disliked", HttpStatus.OK);
            } else {
                postService.addLike(post, likeRequest.getUserId());
                return new ResponseEntity<>("Post liked", HttpStatus.OK);
            }
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/timeline/{userId}")
    public ResponseEntity<List<PostModel>> getTimelinePosts(@PathVariable Long userId) {
        List<PostModel> timelinePosts = timelineService.getTimelinePosts(userId);
        return new ResponseEntity<>(timelinePosts, HttpStatus.OK);
    }
}
