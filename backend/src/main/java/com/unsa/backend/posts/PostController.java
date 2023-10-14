package com.unsa.backend.posts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;

    // @GetMapping("/{id}")
    // public ResponseEntity<PostModel> getPost(@PathVariable Long id) {
    //     try {
    //         return postService.getPostById(id)
    //                 .map(post -> new ResponseEntity<>(post, HttpStatus.OK))
    //                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}
