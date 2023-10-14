package com.unsa.backend.posts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public List<PostModel> getPosts(){
        return (ArrayList<PostModel>) postRepository.findAll();
    }

    public PostModel getPostById(Long id) {
        Optional<PostModel> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    public PostModel createPost(PostModel newPost) {
        return postRepository.save(newPost);
    }

}
