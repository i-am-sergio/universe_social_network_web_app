package com.unsa.backend.posts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public List<PostModel> getPosts() {
        return postRepository.findAll();
    }

    public PostModel getPostById(Long id) {
        Optional<PostModel> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    public PostModel createPost(PostModel newPost) {
        return postRepository.save(newPost);
    }

    public void updatePost(PostModel existingPost, PostModel updatedPost) {
        // Actualizar el post existente con los datos del post actualizado
        existingPost.setDesc(updatedPost.getDesc());
        existingPost.setLikes(updatedPost.getLikes());
        existingPost.setImage(updatedPost.getImage());
        // Guardar el post actualizado en la base de datos
        postRepository.save(existingPost);
    }

    public boolean deletePost(Long postId) {
        Optional<PostModel> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            postRepository.delete(postOptional.get());
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void addLike(PostModel post, Long userId) {
        List<Long> likes = new ArrayList<>(post.getLikes());
        if (!likes.contains(userId)) {
            likes.add(userId);
            post.setLikes(likes);
            postRepository.save(post);
        }
    }

    @Transactional
    public void removeLike(PostModel post, Long userId) {
        List<Long> likes = new ArrayList<>(post.getLikes());
        if (likes.contains(userId)) {
            likes.remove(userId);
            post.setLikes(likes);
            postRepository.save(post);
        }
    }
}
