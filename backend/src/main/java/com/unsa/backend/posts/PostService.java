package com.unsa.backend.posts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public List<PostModel> obtenerPublicaciones(){
        return (ArrayList<PostModel>) postRepository.findAll();
    }
}
